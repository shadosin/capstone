package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.ExampleRepository;
import com.kenzie.appserver.repositories.ExerciseRepository;
import com.kenzie.appserver.repositories.model.ExampleRecord;
import com.kenzie.appserver.repositories.model.ExerciseRecord;
import com.kenzie.appserver.service.model.Exercise;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {
    private ExerciseRepository repo;

    public ExerciseService(ExerciseRepository repo){
        this.repo = repo;
    }
    public Exercise findById(String exerciseId){
        return repo.findById(exerciseId)
                .map(exercise -> new Exercise(exercise.getExerciseId(), exercise.getType(), exercise.getIntensity()
                ,exercise.getExerciseName(), exercise.getDuration(), exercise.getReps(), exercise.getSets(), exercise.getDistance(), exercise.getMETS(),
                        exercise.getDescription()))
                .orElse(null);


    }
    public List<Exercise> findAllExercises(){
        List<Exercise> exercises = new ArrayList<>();
        Iterable<ExerciseRecord> exerciseRecordIterable = repo.findAll();
        for(ExerciseRecord record : exerciseRecordIterable){
            exercises.add(new Exercise(record.getExerciseId(), record.getType(), record.getIntensity()
                    ,record.getExerciseName(), record.getDuration(), record.getReps(), record.getSets(), record.getDistance(), record.getMETS(),
                    record.getDescription()));
        }
        return exercises;
    }
    public void addNewExercise(Exercise exercise){
        ExerciseRecord record = new ExerciseRecord();
        record.setExerciseId(exercise.getExerciseId());
        record.setType(exercise.getType());
        record.setIntensity(exercise.getIntensity());
        record.setExerciseName(exercise.getExerciseName());
        record.setDuration(exercise.getDuration());
        record.setReps(exercise.getReps());
        record.setSets(exercise.getSets());
        record.setDistance(exercise.getDistance());
        record.setMETS(exercise.getMETS());
        record.setDescription(exercise.getDescription());
        repo.save(record);
    }
    public void updateExercise(Exercise exercise){
        if(repo.existsById(exercise.getExerciseId())){
            ExerciseRecord record = new ExerciseRecord();
            record.setExerciseId(exercise.getExerciseId());
            record.setType(exercise.getType());
            record.setIntensity(exercise.getIntensity());
            record.setExerciseName(exercise.getExerciseName());
            record.setDuration(exercise.getDuration());
            record.setReps(exercise.getReps());
            record.setSets(exercise.getSets());
            record.setDistance(exercise.getDistance());
            record.setMETS(exercise.getMETS());
            record.setDescription(exercise.getDescription());
            repo.save(record);
        }
    }
    public void deleteExercise(String exerciseId){
        repo.deleteById(exerciseId);
    }

}
