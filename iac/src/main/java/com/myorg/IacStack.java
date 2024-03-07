package com.myorg;

import software.amazon.awscdk.Duration;
import software.amazon.awscdk.RemovalPolicy;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.apprunner.CfnService;
import software.amazon.awscdk.services.docdb.DatabaseSecret;
import software.amazon.awscdk.services.ec2.InstanceType;
import software.amazon.awscdk.services.ec2.*;
import software.amazon.awscdk.services.ecr.Repository;
import software.amazon.awscdk.services.ecr.TagMutability;
import software.amazon.awscdk.services.iam.*;
import software.amazon.awscdk.services.rds.*;
import software.constructs.Construct;

import java.util.List;
import java.util.Map;

public class IacStack extends Stack {
    public IacStack(final Construct scope, final String id) {
        this(scope, id, null);
    }


    public IacStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        IInstanceEngine engine = DatabaseInstanceEngine.sqlServerEx(SqlServerExInstanceEngineProps.builder()
            .version(SqlServerEngineVersion.VER_16)
            .build());

        InstanceType instanceType = InstanceType.of(InstanceClass.T3, InstanceSize.MICRO);

        final int PORT = 1433;

        DatabaseSecret dbSuperUserSecret = DatabaseSecret.Builder.create(this, "dbSuperUserSecret")
            .username("DBSuperUser")
            .build();

        Vpc dbVpc = Vpc.Builder.create(this, "dbVpc")
            .ipAddresses(IpAddresses.cidr("10.0.0.0/16"))
            .natGateways(1)
            .maxAzs(3)
            .subnetConfiguration(List.of(SubnetConfiguration.builder()
                .name("public-subnet-1")
                .subnetType(SubnetType.PUBLIC)
                .cidrMask(24)
                .build()))
            .build();

        SecurityGroup databaseSecurityGroup = SecurityGroup.Builder.create(this, "database-SG")
            .securityGroupName("database-SG")
            .allowAllOutbound(false)
            .vpc(dbVpc)
            .build();

        databaseSecurityGroup.addEgressRule(Peer.anyIpv4(), Port.tcp(PORT));
        databaseSecurityGroup.addIngressRule(Peer.anyIpv4(), Port.tcp(PORT));

        DatabaseInstance databaseInstance = DatabaseInstance.Builder.create(this, "the-bean-index-instance")
            .vpc(dbVpc)
            .vpcSubnets(SubnetSelection.builder()
                .subnetType(SubnetType.PUBLIC)
                .build())
            .instanceType(instanceType)
            .engine(engine)
            .securityGroups(List.of(databaseSecurityGroup))
            .credentials(Credentials.fromSecret(dbSuperUserSecret))
            .backupRetention(Duration.days(0))
            .removalPolicy(RemovalPolicy.DESTROY)
            .build();

        // GitHub Deploy Role
        String gitHubUrl = "token.actions.githubusercontent.com";
        OpenIdConnectProvider githubProvider = OpenIdConnectProvider.Builder.create(this, "githubProvider")
            .url("https://" + gitHubUrl)
            .clientIds(List.of("sts.amazonaws.com"))
            .build();

        Map<String, Object> conditions = Map.of("StringLike", Map.of(gitHubUrl + ":sub", "repo:The-Bean-Index/TheBeanIndex:*"));

        PolicyStatement assumeRoleStatement = PolicyStatement.Builder.create()
            .actions(List.of("sts:AssumeRole"))
            .effect(Effect.ALLOW)
            .resources(List.of("arn:aws:iam::*:role/cdk-*"))
            .build();

        PolicyStatement getSecretsStatement = PolicyStatement.Builder.create()
            .actions(List.of("secretsmanager:GetSecretValue"))
            .effect(Effect.ALLOW)
            .resources(List.of("*"))
            .build();

        PolicyDocument policyDocument = PolicyDocument.Builder.create()
            .statements(List.of(assumeRoleStatement, getSecretsStatement))
            .build();

        Role githubDeployRole = Role.Builder.create(this, "githubDeployRole")
            .roleName("GitHubDeployRole")
            .description("This role is used via GitHub Actions to deploy with AWS CDK and access stored secrets")
            .assumedBy(new OpenIdConnectPrincipal(githubProvider, conditions))
            .inlinePolicies(Map.of("deploymentPolicies", policyDocument))
            .maxSessionDuration(Duration.hours(1))
            .build();
        //ECR

        Repository appRunnerRepository = Repository.Builder.create(this, "AppRunnerRepository")
            .imageScanOnPush(true)
            .imageTagMutability(TagMutability.MUTABLE)
            .removalPolicy(RemovalPolicy.DESTROY)
            .repositoryName("the-bean-index")
            .build();

        Role ecrAccessRole = Role.Builder.create(this, "AppRunnerECRRepositoryRole")
            .assumedBy(new ServicePrincipal("build.apprunner.amazonaws.com"))
            .managedPolicies(
                List.of(
                    ManagedPolicy.fromManagedPolicyArn(
                        this,
                        "AWSAppRunnerServicePolicyForECRAccessPolicy",
                        "arn:aws:iam::aws:policy/service-role/AWSAppRunnerServicePolicyForECRAccess"
                    )
                )
            )
            .build();
        CfnService.AuthenticationConfigurationProperty authenticationConfigurationProperty =
            CfnService.AuthenticationConfigurationProperty.builder()
                .accessRoleArn(ecrAccessRole.getRoleArn())
                .build();

        // App Runner
        CfnService.ImageRepositoryProperty imageRepositoryProperty = CfnService.ImageRepositoryProperty.builder()
            .imageIdentifier(appRunnerRepository.getRepositoryUri() + "/the-bean-index:latest")
            .imageConfiguration(CfnService.ImageConfigurationProperty.builder()
                .port("80")
                .build())
            .imageRepositoryType("ECR")
            .build();

        CfnService.SourceConfigurationProperty sourceConfigurationProperty = CfnService.SourceConfigurationProperty.builder()
            .imageRepository(imageRepositoryProperty)
            .authenticationConfiguration(authenticationConfigurationProperty)
            .autoDeploymentsEnabled(true)
            .build();

        CfnService.InstanceConfigurationProperty instanceConfigurationProperty = CfnService.InstanceConfigurationProperty.builder()
            .cpu("1 vCPU")
            .memory("2 GB")
            .build();

        CfnService.Builder.create(this, "the-bean-index-runner")
            .serviceName("The-Bean-Index-Runner")
            .sourceConfiguration(sourceConfigurationProperty)
            .instanceConfiguration(instanceConfigurationProperty)
            .build();
    }
}
