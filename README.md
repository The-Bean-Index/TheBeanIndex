# Soccer DB

## Table of Contents

- [The Team](#the-team)

- [Documentation]()

- [Initial Deployment And Setup](#initial-deployment-and-setup)

- [Additional Deployments](#additional-deployments)

- [Useful Commands](#useful-commands)

## The Team

Avi

Lesedi

Shaariq

Tawanda

Sam

## Documentation

Confluence - https://avishkar-bbd.atlassian.net/wiki/spaces/TBI/pages

Jira - https://avishkar-bbd.atlassian.net/jira/software/projects/TBI/boards/2

## Initial Deployment And Setup

First install NodeJS from https://nodejs.org/en/download/

Then to install the AWS CLI run

```

npm i -g aws-cli

```

Then to configure your AWS CLI credentials run

```

aws configure

```

Then to install the AWS CDK run

```

npm i -g aws-cdk

```

Then to deploy the resources required by the AWS CDK to your AWS account, navigate to the `iac` directory in your
terminal and run

```

cdk bootstrap

```

Then to deploy your intial resources run

```

cdk deploy

```

Configure the following repository variables for the actions in GitHub. The deploy role arn can be found in AWS IAM
roles under the "GitHubDeployRole".

```

key: AWS_DEPLOY_ROLE

value: <The arn of the deploy role>

key: AWS_REGION

value: The AWS region to deploy to eg. eu-west-1

```

Then connect to your RDS database instance by using the values for the host, username and password that you find in

the "dbSuperUserSecret*" in AWS Secrets Manager using SQL Server Management Studio (Or a similar tool).

Execute the following SQL statement to create the database

```

USE master;

GO

CREATE DATABASE thebeanindex;

GO

```

Then go to the GitHub repository and click on Actions. Select the "*Deploy Database Changes*" workflow, click "*Run
workflow*".

Your database will have all tables created with their constraints, relations etc.

## Additional Deployments

Any new changes to the infrastructure as code can be deployed using the "*Deploy Infrastructure*" workflow on GitHub
actions

## To use the CLI

- Download the bean-index-cli-*.exe from Releases
- Run the installer
- Start the installed application
- Type in help in the CLI for more info

## Useful Commands

* `cdk deploy` deploy this stack to your default AWS account/region (manual deployment)

* `cdk diff` compare deployed stack with current state
