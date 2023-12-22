package com.kenzie.capstone.service;


import com.kenzie.capstone.service.dao.ExerciseDao;
import com.kenzie.capstone.service.model.ExerciseData;
import com.kenzie.capstone.service.model.ExerciseRecord;


import javax.inject.Inject;
import java.util.List;


public class ExerciseService {
    private final ExerciseDao dao;
    @Inject
    public ExerciseService(ExerciseDao dao){
        this.dao = dao;
    }
    public ExerciseData getExerciseData(String exerciseId){
        List<ExerciseRecord> records = dao.findExerciseData(exerciseId);
        if(records.size() > 0){
            return new ExerciseData(records.get(0).getExerciseId(),
                    records.get(0).getType(),
                    records.get(0).getIntensity(),
                    records.get(0).getExerciseName(),
                    records.get(0).getDuration(),
                    records.get(0).getReps(),
                    records.get(0).getSets(),
                    records.get(0).getDistance(),
                    records.get(0).getMETS(),
                    records.get(0).getDescription());
        }
        return null;
    }

    public ExerciseData setData(String exerciseId, String type,
                                String intensity, String exerciseName, int duration,
                                int reps, int sets, double distance, double METS,
                                String description){
        ExerciseRecord record = dao.setExerciseRecord(exerciseId, type,intensity,
                exerciseName, duration, reps, sets, distance, METS, description);

        return new ExerciseData(exerciseId, type, intensity, exerciseName, duration
        , reps, sets, distance, METS, description);
    }

}
