package com.myorg;

import software.amazon.awscdk.Duration;
import software.amazon.awscdk.RemovalPolicy;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
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
            .actions(List.of("secretsmanager:GetSecretValue", "secretsmanager:ListSecrets"))
            .effect(Effect.ALLOW)
            .resources(List.of("*"))
            .build();

        PolicyStatement ecrGetAuthToken = PolicyStatement.Builder.create()
            .actions(List.of("ecr:GetAuthorizationToken"))
            .effect(Effect.ALLOW)
            .resources(List.of("*"))
            .build();

        PolicyStatement ec2instancesAndPairs = PolicyStatement.Builder.create()
            .actions(List.of("ec2:DescribeInstances", "ec2:DescribeKeyPairs"))
            .effect(Effect.ALLOW)
            .resources(List.of("*"))
            .build();

        PolicyStatement getSSMParam = PolicyStatement.Builder.create()
            .actions(List.of("ssm:GetParameter", "ssm:GetParameters"))
            .effect(Effect.ALLOW)
            .resources(List.of("*"))
            .build();

        PolicyStatement decryptKMS = PolicyStatement.Builder.create()
            .actions(List.of("kms:Decrypt"))
            .effect(Effect.ALLOW)
            .resources(List.of("arn:aws:kms:*:*:key/alias/aws/ssm"))
            .build();

        PolicyStatement ecrPush = PolicyStatement.Builder.create()
            .actions(List.of(
                "ecr:CompleteLayerUpload",
                "ecr:GetAuthorizationToken",
                "ecr:UploadLayerPart",
                "ecr:InitiateLayerUpload",
                "ecr:BatchCheckLayerAvailability",
                "ecr:PutImage"))
            .effect(Effect.ALLOW)
            .resources(List.of("*"))
            .build();

        PolicyDocument policyDocument = PolicyDocument.Builder.create()
            .statements(List.of(assumeRoleStatement, getSecretsStatement, ecrPush, ec2instancesAndPairs, getSSMParam, decryptKMS))
            .build();

        Role githubDeployRole = Role.Builder.create(this, "githubDeployRole")
            .roleName("GitHubDeployRole")
            .description("This role is used via GitHub Actions to deploy with AWS CDK and access stored secrets")
            .assumedBy(new OpenIdConnectPrincipal(githubProvider, conditions))
            .inlinePolicies(Map.of("deploymentPolicies", policyDocument))
            .maxSessionDuration(Duration.hours(1))
            .build();

        //ECR
        Repository theBeanIndexRepository = Repository.Builder.create(this, "TheBeanIndexRepository")
            .imageScanOnPush(true)
            .imageTagMutability(TagMutability.MUTABLE)
            .removalPolicy(RemovalPolicy.DESTROY)
            .repositoryName("the-bean-index")
            .build();

        // EC2

        Role ec2InstanceRole = Role.Builder.create(this, "EC2InstanceRole")
            .assumedBy(new ServicePrincipal("ec2.amazonaws.com"))
            .managedPolicies(
                List.of(
                    ManagedPolicy.fromManagedPolicyArn(
                        this,
                        "AmazonEC2ContainerRegistryReadOnlyPolicy",
                        "arn:aws:iam::aws:policy/AmazonEC2ContainerRegistryReadOnly"
                    )
                )
            )
            .inlinePolicies(Map.of("ecrPolicies", PolicyDocument.Builder.create()
                .statements(List.of(ecrGetAuthToken))
                .build()))
            .build();

        Vpc ec2Vpc = Vpc.Builder.create(this, "ec2Vpc")
            .ipAddresses(IpAddresses.cidr("192.0.0.0/16"))
            .natGateways(1)
            .maxAzs(2)
            .subnetConfiguration(List.of(SubnetConfiguration.builder()
                .name("ec2-public-subnet")
                .subnetType(SubnetType.PUBLIC)
                .cidrMask(24)
                .build()))
            .build();

        SecurityGroup ec2Sg = SecurityGroup.Builder.create(this, "ec2InstanceSg")
            .vpc(ec2Vpc)
            .allowAllOutbound(true)
            .securityGroupName("EC2-Instance-Sg")
            .build();

        ec2Sg.addIngressRule(Peer.anyIpv4(), Port.tcp(22), "Allow SSH from the internet");
        ec2Sg.addIngressRule(Peer.anyIpv4(), Port.tcp(80), "Allow HTTP access from the internet");

        KeyPair keyPair = KeyPair.Builder.create(this, "instanceKeyPair")
            .keyPairName("EC2-Instance-Key")
            .physicalName("EC2-Instance-Key")
            .type(KeyPairType.RSA)
            .build();

        UserData userData = UserData.forLinux();
        userData.addCommands(
            "yum update -y",
            "yum install docker -y",
            "service docker start",
            "usermod -a -G docker ec2-user"
        );

        Instance ec2Instance = Instance.Builder.create(this, "javaServerInstance")
            .vpc(ec2Vpc)
            .role(ec2InstanceRole)
            .securityGroup(ec2Sg)
            .instanceName("Java-Server-Instance")
            .instanceType(instanceType)
            .machineImage(MachineImage.latestAmazonLinux2023())
            .keyPair(keyPair)
            .userData(userData)
            .build();
    }
}
