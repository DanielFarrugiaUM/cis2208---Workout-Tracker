package com.example.cis2208_workouttracker.ui.workouts;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cis2208_workouttracker.domainModels.Workout;

import java.util.ArrayList;
import java.util.List;

public class WorkoutsViewModel extends ViewModel {

    private MutableLiveData<List<Workout>> workouts;

    public WorkoutsViewModel(){
        workouts = new MutableLiveData<>();
    }

    public MutableLiveData<List<Workout>> getWorkouts(){
        ArrayList<Workout> newWorkouts = new ArrayList<>();
        //Generate some dummy workouts for testing
        Workout workout1 = new Workout("Chest and Triceps");
        Workout workout2 = new Workout("Back and Biceps");
        Workout workout3 = new Workout("Legs and Abs");
        newWorkouts.add(workout1);
        newWorkouts.add(workout2);
        newWorkouts.add(workout3);

        workouts.setValue(newWorkouts);
        return workouts;
    }
}
