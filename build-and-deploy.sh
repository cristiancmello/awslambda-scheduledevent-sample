#!/usr/bin/env bash

source env.sh

cat <<EOF > policydocument.json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": "logs:CreateLogGroup",
      "Resource": "arn:aws:logs:$AWS_REGION:$AWS_ACCOUNTID:*"
    },
    {
      "Effect": "Allow",
      "Action": [
        "logs:CreateLogStream",
        "logs:PutLogEvents"
      ],
      "Resource": [
        "arn:aws:logs:$AWS_REGION:$AWS_ACCOUNTID:log-group:/aws/lambda/$LAMBDA_NAME:*"
      ]
    }
  ]
}
EOF

cat <<EOF > lambda-trustrolepolicy.json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Sid": "",
      "Effect": "Allow",
      "Principal": {
        "Service": "events.amazonaws.com"
      },
      "Action": "sts:AssumeRole"
    },
    {
      "Effect": "Allow",
      "Principal": {
        "Service": "lambda.amazonaws.com"
      },
      "Action": "sts:AssumeRole"
    }
  ]
}
EOF

mvn -U clean package -DskipTests

aws lambda delete-function \
  --function-name "$LAMBDA_NAME"

aws iam create-role \
  --role-name LambdaTrustRolePolicy \
  --assume-role-policy-document file://lambda-trustrolepolicy.json

aws lambda create-function \
  --function-name "$LAMBDA_NAME" \
  --role "arn:aws:iam::$AWS_ACCOUNTID:role/LambdaTrustRolePolicy" \
  --zip-file "fileb://target/$JARFILE" \
  --handler "$LAMBDA_HANDLER" \
  --description "AWS Lambda with EventBridge Scheduled Event Sample for Spring Cloud Function" \
  --runtime java17 \
  --timeout 30 \
  --memory-size 1024 \
  --publish

aws iam put-role-policy \
  --role-name LambdaTrustRolePolicy \
  --policy-name CloudWatchPolicy \
  --policy-document file://policydocument.json

aws lambda add-permission \
  --function-name "$LAMBDA_NAME" \
  --statement-id my-scheduled-event \
  --action 'lambda:InvokeFunction' \
  --principal events.amazonaws.com \
  --source-arn "arn:aws:events:$AWS_REGION:$AWS_ACCOUNTID:rule/TriggerEachOneMinuteEventRule"

aws events put-rule \
  --name TriggerEachOneMinuteEventRule \
  --schedule-expression "rate(1 minute)"

aws events put-targets \
  --rule TriggerEachOneMinuteEventRule \
  --targets "{\"Id\" : \"1\", \"Arn\": \"arn:aws:lambda:$AWS_REGION:$AWS_ACCOUNTID:function:$LAMBDA_NAME\"}"

rm lambda-trustrolepolicy.json policydocument.json