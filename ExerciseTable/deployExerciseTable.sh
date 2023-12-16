#!/usr/bin/env bash
STACK_NAME='capstone-exercise-tables'
EXERCISE_FILE='exerciseData.json'
TEMP_FILE='temp.json'

get_data() {
    echo "Getting Exercise Data from Google Sheet"
    node getExerciseJson.mjs > $EXERCISE_FILE
    echo "Exercise data obtained"
}

create_stack() {
    echo "Creating Stack and Table"
    aws cloudformation create-stack --stack-name $STACK_NAME --template-body file://ExerciseTable.yml
    echo "Waiting for stack to be created"
    aws cloudformation wait stack-create-complete --stack-name $STACK_NAME
    echo "Stack and Table created"
}

split_and_write() {
    local file=$1
    local dataLength=$(jq '.Exercise | length' "$file")
    local -i count=0
    while [[ $count -lt $dataLength ]]; do
        jq "{Exercise: .Exercise[$count:$((count + 25))]}" $file > temp.json
        echo "Writing batch $count to DynamoDB"
        aws dynamodb batch-write-item --request-items file://$TEMP_FILE
        let count+=25
    done
}

cleanup() {
    echo "Cleaning up"
    [[ -f $EXERCISE_FILE ]] && rm $EXERCISE_FILE
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
    split_and_write $EXERCISE_FILE
    cleanup
fi

if [[ $1 == "-r" ]]; then
  echo  "Cleaning files and exiting."
    cleanup
fi
echo "Good Bye"