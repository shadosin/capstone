package com.kenzie.capstone.service.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kenzie.capstone.service.ExerciseService;
import com.kenzie.capstone.service.dependency.DaggerServiceComponent;
import com.kenzie.capstone.service.dependency.ServiceComponent;
import com.kenzie.capstone.service.model.ExerciseData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetExerciseDataUsingGSI implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        ServiceComponent serviceComponent = DaggerServiceComponent.create();
        ExerciseService service = serviceComponent.provideExerciseService();

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);

        String attributeValue = input.getPathParameters().get("type");

        if(attributeValue == null || attributeValue.isEmpty()){
            return response
                    .withStatusCode(400)
                    .withBody("No such exercise exists");
        }

        try{
            List<ExerciseData> exerciseDataList = service.getExerciseDataFromAttributeValue(attributeValue);
            String output = gson.toJson(exerciseDataList);

            return response.withStatusCode(200).withBody(output);
        }catch (Exception e){
            return response.withStatusCode(400).withBody(gson.toJson(e.getMessage()));
        }
    }

}
