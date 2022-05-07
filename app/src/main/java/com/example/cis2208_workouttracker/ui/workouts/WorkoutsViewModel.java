package com.example.cis2208_workouttracker.ui.workouts;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cis2208_workouttracker.backend.DbHelper;
import com.example.cis2208_workouttracker.backend.WorkoutsUtility;
import com.example.cis2208_workouttracker.domainModels.Workout;

import java.util.ArrayList;
import java.util.List;

public class WorkoutsViewModel extends ViewModel {

    private final MutableLiveData<List<Workout>> workouts;

    public WorkoutsViewModel(){
        workouts = new MutableLiveData<>();
    }

    public MutableLiveData<List<Workout>> getWorkouts(Context context){
        //ArrayList<Workout> newWorkouts = new ArrayList<>();
        DbHelper dbHelper = new DbHelper(context);
        WorkoutsUtility workoutsUtility = new WorkoutsUtility(dbHelper);
        ArrayList<Workout> newWorkouts = workoutsUtility.getWorkouts();
        //Generate some dummy workouts for testing
        /*Workout workout1 = new Workout("Chest and Triceps");
        Workout workout2 = new Workout("Back and Biceps");
        Workout workout3 = new Workout("Legs and Abs");
        Workout workout4 = new Workout("Chest and Triceps");
        Workout workout5 = new Workout("Back and Biceps");
        Workout workout6 = new Workout("Legs and Abs");
        newWorkouts.add(workout1);
        newWorkouts.add(workout2);
        newWorkouts.add(workout3);
        newWorkouts.add(workout4);
        newWorkouts.add(workout5);
        newWorkouts.add(workout6);*/

        workouts.setValue(newWorkouts);
        return workouts;
    }
}
