package com.kenzie.appserver.controller;

import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.ExerciseEventDataResponse;
import com.kenzie.appserver.controller.model.MealEventDataResponse;
import com.kenzie.appserver.service.EventDataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@IntegrationTest
public class EventDataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventDataService eventDataService;

    @Test
    public void testGetMealEventData() throws Exception {
        String mealId = "meal123";
        MealEventDataResponse mockResponse = new MealEventDataResponse();
        mockResponse.setMealId(mealId);
        mockResponse.setName("Salad");
        mockResponse.setType("Vegetarian");
        mockResponse.setCalories(200);
        mockResponse.setProtein(5);
        mockResponse.setCarb(30);
        mockResponse.setFat(10);
        mockResponse.setGlutenFree(true);
        mockResponse.setVegan(false);

        given(eventDataService.getMealDataById(mealId)).willReturn(mockResponse);

        mockMvc.perform(get("/eventData/meal/id/" + mealId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mealId").value(mealId))
                .andExpect(jsonPath("$.name").value("Salad"))
                .andExpect(jsonPath("$.type").value("Vegetarian"))
                .andExpect(jsonPath("$.calories").value(200))
                .andExpect(jsonPath("$.protein").value(5))
                .andExpect(jsonPath("$.carb").value(30))
                .andExpect(jsonPath("$.fat").value(10))
                .andExpect(jsonPath("$.glutenFree").value(true))
                .andExpect(jsonPath("$.vegan").value(false));
    }

    @Test
    public void testGetMealEventDataByType() throws Exception {
        String type = "Vegetarian";
        MealEventDataResponse mockResponse1 = new MealEventDataResponse();
        mockResponse1.setMealId("meal456");
        mockResponse1.setName("Vegetarian Pizza");
        mockResponse1.setUrl("http://example.com/vegpizza");
        mockResponse1.setType(type);
        mockResponse1.setCalories(500);
        mockResponse1.setProtein(20);
        mockResponse1.setCarb(60);
        mockResponse1.setFat(20);
        mockResponse1.setGlutenFree(false);
        mockResponse1.setVegan(false);

        MealEventDataResponse mockResponse2 = new MealEventDataResponse();
        mockResponse2.setMealId("meal789");
        mockResponse2.setName("Vegetarian Burger");
        mockResponse2.setUrl("http://example.com/vegburger");
        mockResponse2.setType(type);
        mockResponse2.setCalories(450);
        mockResponse2.setProtein(18);
        mockResponse2.setCarb(40);
        mockResponse2.setFat(25);
        mockResponse2.setGlutenFree(false);
        mockResponse2.setVegan(false);

        List<MealEventDataResponse> mockResponses = Arrays.asList(mockResponse1, mockResponse2);

        given(eventDataService.getMealDataByType(type)).willReturn(mockResponses);

        mockMvc.perform(get("/eventData/meal/type/" + type))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].mealId").value("meal456"))
                .andExpect(jsonPath("$[0].name").value("Vegetarian Pizza"))
                .andExpect(jsonPath("$[1].mealId").value("meal789"))
                .andExpect(jsonPath("$[1].name").value("Vegetarian Burger"));
    }

    @Test
    public void testGetExerciseEventData() throws Exception {
        String exerciseId = "exercise123";
        ExerciseEventDataResponse mockResponse = new ExerciseEventDataResponse();
        mockResponse.setExerciseId(exerciseId);

        given(eventDataService.getExerciseDataById(exerciseId)).willReturn(mockResponse);

        mockMvc.perform(get("/eventData/exercise/id/" + exerciseId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exerciseId").value(exerciseId));
    }

    @Test
    public void testGetExerciseEventDataByType() throws Exception {
        String type = "Cardio";

        ExerciseEventDataResponse mockResponse1 = new ExerciseEventDataResponse();
        mockResponse1.setExerciseId("ex123");
        mockResponse1.setType(type);
        mockResponse1.setIntensity("High");
        mockResponse1.setName("Running");
        mockResponse1.setDuration(30);
        mockResponse1.setReps(0);
        mockResponse1.setSets(0);
        mockResponse1.setDistance(5.0);
        mockResponse1.setMETS(8.0);
        mockResponse1.setDescription("Long distance running");

        ExerciseEventDataResponse mockResponse2 = new ExerciseEventDataResponse();
        mockResponse2.setExerciseId("ex456");
        mockResponse2.setType(type);
        mockResponse2.setIntensity("Moderate");
        mockResponse2.setName("Cycling");
        mockResponse2.setDuration(45);
        mockResponse2.setReps(0);
        mockResponse2.setSets(0);
        mockResponse2.setDistance(20.0);
        mockResponse2.setMETS(6.0);
        mockResponse2.setDescription("Outdoor cycling");

        List<ExerciseEventDataResponse> mockResponses = Arrays.asList(mockResponse1, mockResponse2);

        given(eventDataService.getExerciseDataByType(type)).willReturn(mockResponses);

        mockMvc.perform(get("/eventData/exercise/type/" + type))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].exerciseId").value("ex123"))
                .andExpect(jsonPath("$[0].type").value("Cardio"))
                .andExpect(jsonPath("$[0].intensity").value("High"))
                .andExpect(jsonPath("$[0].name").value("Running"))
                .andExpect(jsonPath("$[0].duration").value(30))
                .andExpect(jsonPath("$[0].distance").value(5.0))
                .andExpect(jsonPath("$[0].mets").value(8.0))
                .andExpect(jsonPath("$[0].description").value("Long distance running"))
                .andExpect(jsonPath("$[1].exerciseId").value("ex456"))
                .andExpect(jsonPath("$[1].type").value("Cardio"))
                .andExpect(jsonPath("$[1].intensity").value("Moderate"))
                .andExpect(jsonPath("$[1].name").value("Cycling"))
                .andExpect(jsonPath("$[1].duration").value(45))
                .andExpect(jsonPath("$[1].distance").value(20.0))
                .andExpect(jsonPath("$[1].mets").value(6.0))
                .andExpect(jsonPath("$[1].description").value("Outdoor cycling"));
    }
}
