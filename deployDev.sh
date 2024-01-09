#!/bin/bash
set -eo pipefail
#TEMPLATE=LambdaService-template.yml
VITATRACSERVICETEMPLATE=LambdaVitatracService.yml

source ./setupEnvironment.sh

if [ -z "$GITHUB_GROUP_NAME" ] || [ "$GITHUB_GROUP_NAME" == "replacewithyourgroupname" ] ; then
  echo "Your environment variable GITHUB_GROUP_NAME is not properly configured.  Make sure that you have set it properly in setupEnvironment.sh"
  exit 1
fi

./gradlew :ServiceLambda:build -i

echo "Deleting Application $CAPSTONE_SERVICE_STACK_DEV"
echo "This may take 2-3 minutes...  But if takes more than 5 minutes then it may have failed. Check your CloudFormation Stack on the AWS UI for errors."
aws cloudformation delete-stack --stack-name $CAPSTONE_SERVICE_STACK_DEV
aws cloudformation wait stack-delete-complete --stack-name $CAPSTONE_SERVICE_STACK_DEV

aws cloudformation package --template-file $VITATRACSERVICETEMPLATE --s3-bucket $CAPSTONE_ARTIFACT_BUCKET --output-template-file $VITATRACSERVICETEMPLATE-development.yml
aws cloudformation deploy --template-file $VITATRACSERVICETEMPLATE --stack-name $CAPSTONE_SERVICE_STACK_DEV --capabilities CAPABILITY_NAMED_IAM

