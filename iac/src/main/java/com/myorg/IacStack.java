package com.myorg;

import java.util.List;
import java.util.Map;

import software.amazon.awscdk.Duration;
import software.amazon.awscdk.RemovalPolicy;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.docdb.DatabaseSecret;
import software.amazon.awscdk.services.ec2.IIpAddresses;
import software.amazon.awscdk.services.ec2.IPeer;
import software.amazon.awscdk.services.ec2.Instance;
import software.amazon.awscdk.services.ec2.InstanceClass;
import software.amazon.awscdk.services.ec2.InstanceSize;
import software.amazon.awscdk.services.ec2.InstanceType;
import software.amazon.awscdk.services.ec2.IpAddresses;
import software.amazon.awscdk.services.ec2.Peer;
import software.amazon.awscdk.services.ec2.Port;
import software.amazon.awscdk.services.ec2.SecurityGroup;
import software.amazon.awscdk.services.ec2.SubnetConfiguration;
import software.amazon.awscdk.services.ec2.SubnetSelection;
import software.amazon.awscdk.services.ec2.SubnetType;
import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.rds.Credentials;
import software.amazon.awscdk.services.rds.DatabaseInstance;
import software.amazon.awscdk.services.rds.DatabaseInstanceEngine;
import software.amazon.awscdk.services.rds.IInstanceEngine;
import software.amazon.awscdk.services.rds.SqlServerEngineVersion;
import software.amazon.awscdk.services.rds.SqlServerExInstanceEngineProps;
import software.amazon.awscdk.services.secretsmanager.*;
import software.constructs.Construct;

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


        DatabaseSecret dbSuperUserSecret = DatabaseSecret.Builder.create(this,  "dbSuperUserSecret")
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

    }
}
