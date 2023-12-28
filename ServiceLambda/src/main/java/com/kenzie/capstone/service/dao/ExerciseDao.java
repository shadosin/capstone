package com.kenzie.capstone.service.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.google.common.collect.ImmutableMap;
import com.kenzie.capstone.service.model.ExerciseData;
import com.kenzie.capstone.service.model.ExerciseRecord;

import java.util.List;

public class ExerciseDao {
    private DynamoDBMapper mapper;

    public ExerciseDao(DynamoDBMapper mapper){this.mapper = mapper;}

public ExerciseData addExerciseData(ExerciseData data){
        try{
            mapper.save(data, new DynamoDBSaveExpression()
                    .withExpected(ImmutableMap.of("exerciseId", new ExpectedAttributeValue().withExists(false)
                    )));
        }catch(ConditionalCheckFailedException e){
            throw new IllegalArgumentException("id has already been used");
        }
        return data;
}
public List<ExerciseRecord> findExerciseData(String exerciseId){
        ExerciseRecord record = new ExerciseRecord();
        record.setExerciseId(exerciseId);

    DynamoDBQueryExpression<ExerciseRecord> query = new DynamoDBQueryExpression<ExerciseRecord>()
            .withHashKeyValues(record)
            .withConsistentRead(false);

    return mapper.query(ExerciseRecord.class, query);
    }
    public ExerciseRecord setExerciseRecord(String exerciseId, String type,
                                            String intensity, String exerciseName, int duration,
                                            int reps, int sets, double distance, double METS,
                                            String description){
        ExerciseRecord record = new ExerciseRecord();
        record.setExerciseId(exerciseId);
        record.setType(type);
        record.setIntensity(intensity);
        record.setExerciseName(exerciseName);
        record.setDuration(duration);
        record.setReps(reps);
        record.setSets(sets);
        record.setDistance(distance);
        record.setMETS(METS);
        record.setDescription(description);

        try {
            mapper.save(record, new DynamoDBSaveExpression()
                    .withExpected(ImmutableMap.of(
                            "id",
                            new ExpectedAttributeValue().withExists(false)
                    )));
        } catch (ConditionalCheckFailedException e) {
            throw new IllegalArgumentException("id already exists");
        }

        return record;

    }

}
