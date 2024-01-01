package com.kenzie.capstone.service.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.capstone.service.model.MealData;

import java.util.List;

public class MealLambdaServiceClient {
    private static final String GET_MEAL_ENDPOINT = "meal/{mealId}";

    private static final String GET_MEAL_TYPE_ENDPOINT = "meal/type/{value}";

    private static final String SET_MEAL_ENDPOINT = "meal";

    private ObjectMapper mapper;

    public MealLambdaServiceClient() {
        this.mapper = new ObjectMapper();
    }

    public MealData getMealData(String mealId) {
        EndpointUtility endpointUtility = new EndpointUtility();
        String response = endpointUtility.getEndpoint(GET_MEAL_ENDPOINT.replace("{id}", mealId));
        MealData mealData;
        try{
            mealData = mapper.readValue(response, MealData.class);
        }catch(Exception e){
            throw new ApiGatewayException("Unable to map deserialized JSON: " + e);
        }
        return mealData;
    }
    public MealData setMealData(String data){
        EndpointUtility endpointUtility = new EndpointUtility();
        String response = endpointUtility.postEndpoint(SET_MEAL_ENDPOINT,data);
        MealData mealData;
        try{
            mealData = mapper.readValue(response, MealData.class);
        }catch(Exception e){
            throw new ApiGatewayException("Unable to map deserialized JSON: " + e);
        }
        return mealData;
    }

    public List<MealData> getMealDataFromGSI(String attributeValue) {
        EndpointUtility endpointUtility = new EndpointUtility();
        String response = endpointUtility.getEndpoint(GET_MEAL_TYPE_ENDPOINT.replace("{value}", attributeValue));
        List<MealData> mealDataList;
        try{
            mealDataList = mapper.readValue(response, new TypeReference<>() {});
        } catch (Exception e) {
            throw new ApiGatewayException(("Unable to deserialize JSON: " + e));
        }
        return mealDataList;
    }
}
