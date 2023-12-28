package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.ExerciseDao;
import com.kenzie.capstone.service.model.ExerciseData;
import com.kenzie.capstone.service.model.ExerciseRecord;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ExerciseServiceTest {
    private ExerciseDao exerciseDao;
    private ExerciseService exerciseService;
    @BeforeAll
    void setup(){
        this.exerciseDao = mock(ExerciseDao.class);
        this.exerciseService = new ExerciseService(exerciseDao);
    }

    @Test
    void setExerciseDataWorks(){
        ArgumentCaptor<String> exerciseIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> typeCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> intensityCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> durationCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> repsCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> setsCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Double> distanceCaptor = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Double> metsCaptor = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<String> descriptionCaptor = ArgumentCaptor.forClass(String.class);

        ExerciseData response = this.exerciseService.setData("type",
                "intensity", "name", 1, 1, 1, 1.2,
                1.0, "description");

        verify(exerciseDao, times(1)).setExerciseRecord(exerciseIdCaptor.capture(), typeCaptor.capture(),
                intensityCaptor.capture(), nameCaptor.capture(), durationCaptor.capture(), repsCaptor.capture(), setsCaptor.capture(), distanceCaptor.capture()
                , metsCaptor.capture(), descriptionCaptor.capture());

        assertNotNull(exerciseIdCaptor.getValue(), "An ID is generated");
        assertNotNull(typeCaptor.getValue(), "A value is generated");
        assertNotNull(intensityCaptor.getValue(), "intensity is generated");
        assertNotNull(nameCaptor.getValue(), "a name is generated");
        assertNotNull(durationCaptor.getValue(), "Duration is generated");
        assertNotNull(repsCaptor.getValue(), "Reps are generated");
        assertNotNull(setsCaptor.getValue(), "Sets are generated");
        assertNotNull(distanceCaptor.getValue(), "Distance is generated");
        assertNotNull(metsCaptor.getValue(), "METS are generated");
        assertNotNull(descriptionCaptor.getValue(), "description is generated");

        assertEquals(response.getExerciseId(), exerciseIdCaptor.getValue());
        assertEquals(response.getType(), typeCaptor.getValue());
        assertEquals(response.getIntensity(), intensityCaptor.getValue());
        assertEquals(response.getExerciseName(), nameCaptor.getValue());
        assertEquals(response.getDuration(), durationCaptor.getValue());
        assertEquals(response.getReps(), repsCaptor.getValue());
        assertEquals(response.getSets(), setsCaptor.getValue());
        assertEquals(response.getDistance(), distanceCaptor.getValue());
        assertEquals(response.getMETS(), metsCaptor.getValue());
        assertEquals(response.getDescription(), descriptionCaptor.getValue());

        assertNotNull(response, "A response is returned");

    }



    @Test
    void getExerciseData(){
        ArgumentCaptor<String> exerciseIdCaptor = ArgumentCaptor.forClass(String.class);

        String exerciseId = "id";
        String type = "type";
        String intensity = "intensity";
        String exerciseName = "name";
        int duration = 10;
        int reps = 10;
        int sets = 4;
        double distance = 1.2;
        double mets = 2.3;
        String description = "to describe";

        ExerciseRecord record = new ExerciseRecord();
        record.setExerciseId(exerciseId);
        record.setType(type);
        record.setIntensity(intensity);
        record.setExerciseName(exerciseName);
        record.setDuration(duration);
        record.setReps(reps);
        record.setSets(sets);
        record.setDistance(distance);
        record.setMETS(mets);
        record.setDescription(description);

        when(exerciseDao.findExerciseData(exerciseId)).thenReturn(Arrays.asList(record));

        ExerciseData data = this.exerciseService.getExerciseData(exerciseId);

        verify(exerciseDao, times(1)).findExerciseData(exerciseIdCaptor.capture());

        assertEquals(exerciseId, exerciseIdCaptor.getValue());
    }
}
