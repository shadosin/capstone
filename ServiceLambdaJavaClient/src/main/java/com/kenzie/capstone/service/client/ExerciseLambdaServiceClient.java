package com.kenzie.capstone.service.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.capstone.service.model.ExerciseData;

import java.util.ArrayList;
import java.util.List;

public class ExerciseLambdaServiceClient {
    private static final String GET_EXERCISE_ENDPOINT = "exercise/{exerciseId}";
    private static final String GET_EXERCISE_TYPE_ENDPOINT = "exercise/type/{value}";
    private static final String SET_EXERCISE_ENDPOINT = "exercise";
    private ObjectMapper mapper;
    public ExerciseLambdaServiceClient(){this.mapper = new ObjectMapper();}

    public ExerciseData findExerciseData(String exerciseId){
        EndpointUtility endpointUtility = new EndpointUtility();
        String response = endpointUtility.getEndpoint(GET_EXERCISE_ENDPOINT.replace("{exerciseId}", exerciseId));
        ExerciseData exerciseData;
        try{
            exerciseData = mapper.readValue(response, ExerciseData.class);
        }catch(Exception e){
            throw new ApiGatewayException(("Unable to deserialize JSON: " + e));
        }
        return exerciseData;
    }
    public ExerciseData setExerciseData(String exerciseId, String type,
                                        String intensity, String exerciseName, int duration,
                                        int reps, int sets, double distance, double METS,
                                        String description){
        String data = exerciseId + type + intensity + exerciseName + String.valueOf(duration) + String.valueOf(reps)
                + String.valueOf(sets) + String.valueOf(distance) + String.valueOf(METS) + description;

        EndpointUtility endpointUtility = new EndpointUtility();
        String response = endpointUtility.postEndpoint(SET_EXERCISE_ENDPOINT, data);
        ExerciseData exerciseData;
        try{
            exerciseData = mapper.readValue(response, ExerciseData.class);
        }catch(Exception e){
            throw new ApiGatewayException("Unable to deserializeJSON: " + e);
        }
        return exerciseData;
    }

    public List<ExerciseData> getExerciseDataFromGSI(String attributeValue) {
        EndpointUtility endpointUtility = new EndpointUtility();
        String response = endpointUtility.getEndpoint(GET_EXERCISE_TYPE_ENDPOINT.replace("{value}", attributeValue));
        List<ExerciseData> exerciseDataList;
        try{
            exerciseDataList = mapper.readValue(response, new TypeReference<>() {});
        } catch (Exception e) {
            throw new ApiGatewayException(("Unable to deserialize JSON: " + e));
        }
        return exerciseDataList;
    }
}
