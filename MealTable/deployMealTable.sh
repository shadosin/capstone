#!/usr/bin/env bash
STACK_NAME='capstone-meal-tables'
MEAL_FILE='mealData.json'
TEMP_FILE='temp.json'

get_data() {
    echo "Getting Meal Data from Google Sheet"
    node getMealJson.mjs > $MEAL_FILE
    echo "Meal data obtained"
}

create_stack() {
    echo "Creating Stack and Table"
    aws cloudformation create-stack --stack-name $STACK_NAME --template-body file://MealTable.yml
    echo "Waiting for stack to be created"
    aws cloudformation wait stack-create-complete --stack-name $STACK_NAME
    echo "Stack and Table created"
}

split_and_write() {
    local file=$1
    local dataLength=$(jq '.Meals | length' "$file")
    local -i count=0
    while [[ $count -lt $dataLength ]]; do
        jq "{Meals: .Meals[$count:$((count + 25))]}" $file > temp.json
        echo "Writing batch $count to DynamoDB"
        aws dynamodb batch-write-item --request-items file://$TEMP_FILE
        let count+=25
    done
}

cleanup() {
    echo "Cleaning up"
    [[ -f $MEAL_FILE ]] && rm $MEAL_FILE
    [[ -f $TEMP_FILE ]] && rm $TEMP_FILE
}

delete_stack() {
    echo "Deleting Stack"
    aws cloudformation delete-stack --stack-name $STACK_NAME
    echo "Waiting for stack to be deleted"
    aws cloudformation wait stack-delete-complete --stack-name $STACK_NAME
    echo "Stack deleted"
}

if [[ $1 == "-d" ]]; then
    delete_stack
    cleanup
fi

if [[ $1 != "-d" ]]; then
    get_data
fi

if [[ $1 == "-c" ]]; then
    create_stack
    split_and_write $MEAL_FILE
    cleanup
fi

if [[ $1 == "-r" ]]; then
  echo  "Cleaning files and exiting."
    cleanup
fi
echo "Good Bye"