#!/usr/bin/env bash

# Script to deploy the Exercise Data from a Google Sheet to a DynamoDB table, using aws cli and CloudFormation.

# Google Sheet URL
# https://sheets.googleapis.com/v4/spreadsheets/{spreadsheetId}/values/{sheetName}?alt=json&key={theKey}

# You will need to install the program jq  to run this script.
# if your on windows install scoop then run scoop install jq
# if your on mac install homebrew then run brew install jq
# if your on linux install apt-get then run apt-get install jq

# you will also need to have an api key from google.


echo "Getting Exercise Data from Google Sheet"
node getExerciseJson.mjs > exerciseData.json
echo "Exercise Data obtained"
echo "Creating Stack and Table"
aws cloudformation create-stack --stack-name capstone-exercise-tables --template-body file://ExerciseTable.yml
echo "Waiting for stack to be created"
aws cloudformation wait stack-create-complete --stack-name capstone-exercise-tables
echo "Stack and Table created"

split_and_write() {
    local file=$1
    local length=$(jq '.Exercise | length' "$file")
    local -i count=0

    while [[ $count -lt $length ]]; do
        jq "{Exercise: .Exercise[$count:$((count + 25))]}" $file > temp.json
        echo "Writing batch $count to DynamoDB"
        aws dynamodb batch-write-item --request-items file://temp.json
        let count+=25
    done
}
split_and_write exerciseData.json
echo "Exercise Data written to DynamoDB"
echo "Cleaning up"

[[ -f exerciseData.json ]] && rm exerciseData.json

[[ -f temp.json ]] && rm temp.json

echo "Good Bye"