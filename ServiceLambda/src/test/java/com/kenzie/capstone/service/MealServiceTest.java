package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.MealDao;
import com.kenzie.capstone.service.model.MealData;
import com.kenzie.capstone.service.model.MealRecord;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;

import static com.amazonaws.util.ValidationUtils.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MealServiceTest {

    private MealDao mealDao;

    private MealService mealService;

    @BeforeAll
    void setup() {
        this.mealDao = mock(MealDao.class);
        this.mealService = new MealService(mealDao);
    }

    @Test
    void setMealData() {
        ArgumentCaptor<String> mealIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> mealNameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> descriptionCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> recipeCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> typeCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Double> proteinCaptor = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Double> caloriesCaptor = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Double> carbsCaptor = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Double> fatCaptor = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Boolean> glutenFreeCaptor = ArgumentCaptor.forClass(Boolean.class);
        ArgumentCaptor<Boolean> veganCaptor = ArgumentCaptor.forClass(Boolean.class);

        MealData response = this.mealService.setData( "type", "recipe",
                "name", "description", 1.4, 1.0, 1.0, 1.0, true, true);

        verify(mealDao, times(1)).setMealRecord(mealIdCaptor.capture(), mealNameCaptor.capture(),
                descriptionCaptor.capture(), recipeCaptor.capture(), typeCaptor.capture(), caloriesCaptor.capture(),
                proteinCaptor.capture(), carbsCaptor.capture(), fatCaptor.capture(), glutenFreeCaptor.capture(), veganCaptor.capture());

        assertNotNull(mealIdCaptor.getValue(), "An ID is generated");
        assertNotNull(mealNameCaptor.getValue(), "A name is generated");
        assertNotNull(descriptionCaptor.getValue(), "A description is generated");
        assertNotNull(recipeCaptor.getValue(), "A recipe is generated");
        assertNotNull(typeCaptor.getValue(), "A value is generated");
        assertNotNull(caloriesCaptor.getValue(), "Calories are generated");
        assertNotNull(proteinCaptor.getValue(), "Proteins are generated");
        assertNotNull(carbsCaptor.getValue(), "Carbs are generated");
        assertNotNull(fatCaptor.getValue(), "Fats are generated");
        assertNotNull(glutenFreeCaptor.getValue(), "GlutenFree is generated");
        assertNotNull(veganCaptor.getValue(), "Vegan is generated");

        assertEquals(response.getMealId(), mealIdCaptor.getValue());
        assertEquals(response.getName(), mealNameCaptor.getValue());
        assertEquals(response.getDescription(), descriptionCaptor.getValue());
        assertEquals(response.getRecipe(), recipeCaptor.getValue());
        assertEquals(response.getType(), typeCaptor.getValue());
        assertEquals(response.getCalories(), caloriesCaptor.getValue());
        assertEquals(response.getProtein(), proteinCaptor.getValue());
        assertEquals(response.getCarb(), carbsCaptor.getValue());
        assertEquals(response.getFat(), fatCaptor.getValue());
        assertEquals(response.isGlutenFree(), glutenFreeCaptor.getValue());
        assertEquals(response.isVegan(), veganCaptor.getValue());

        assertNotNull(response, "A response is returned");
    }

    @Test
    void getMealData() {
        ArgumentCaptor<String> mealIdCaptor = ArgumentCaptor.forClass(String.class);

        String mealId = "id";
        String name = "name";
        String description = "description";
        String recipe = "recipe";
        String type = "type";
        double calories = 1.4;
        double protein = 1.0;
        double carb = 1.1;
        double fat = 1.2;
        boolean glutenFree = true;
        boolean vegan = true;

        MealRecord mealRecord = new MealRecord();
        mealRecord.setMealId(mealId);
        mealRecord.setName(name);
        mealRecord.setDescription(description);
        mealRecord.setRecipe(recipe);
        mealRecord.setType(type);
        mealRecord.setCalories(calories);
        mealRecord.setProtein(protein);
        mealRecord.setCarb(carb);
        mealRecord.setFat(fat);
        mealRecord.setGlutenFree(glutenFree);
        mealRecord.setVegan(vegan);

        when(mealDao.findMealData(mealId)).thenReturn(Arrays.asList(mealRecord));

        MealData data = this.mealService.getMealData(mealId);

        verify(mealDao, times(1)).findMealData(mealIdCaptor.capture());

        assertEquals(mealId, mealIdCaptor.getValue());
    }

}
