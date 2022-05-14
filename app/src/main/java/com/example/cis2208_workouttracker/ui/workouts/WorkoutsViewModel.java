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

        DbHelper dbHelper = new DbHelper(context);
        WorkoutsUtility workoutsUtility = new WorkoutsUtility(dbHelper);
        ArrayList<Workout> newWorkouts = workoutsUtility.getWorkouts();

        workouts.setValue(newWorkouts);
        return workouts;
    }
}
