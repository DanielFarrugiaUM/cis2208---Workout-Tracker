package com.example.cis2208_workouttracker.ui.workouts;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cis2208_workouttracker.backend.DbHelper;
import com.example.cis2208_workouttracker.backend.ExerciseUtility;
import com.example.cis2208_workouttracker.domainModels.Exercise;
import com.example.cis2208_workouttracker.domainModels.HistoryItem;
import com.example.cis2208_workouttracker.domainModels.RepExercise;
import com.example.cis2208_workouttracker.domainModels.TimedExercise;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class HistoryItemsViewModel extends ViewModel {
    private final MutableLiveData<List<HistoryItem>> items;

    public HistoryItemsViewModel(){
        items = new MutableLiveData<>();
    }

    public MutableLiveData<List<HistoryItem>> getItems(Context context){

        DbHelper dbHelper = new DbHelper(context);
        ExerciseUtility exerciseUtility = new ExerciseUtility(dbHelper);
        ArrayList<HistoryItem> newItems = new ArrayList<>();

        //Hard coded data, needs to come from DB
        RepExercise ex1 = new RepExercise("Exercise1", 3,15, 12, 1);
        RepExercise ex2 = new RepExercise("Exercise1", 5,12.5, 8, 1);
        TimedExercise ex3 = new TimedExercise("timed", 4, 0, new Time(0,1,30), 1);
        HistoryItem i1 = new HistoryItem(ex1);
        HistoryItem i2 = new HistoryItem(ex2);
        HistoryItem i3 = new HistoryItem(ex3);
        newItems.add(i1);
        newItems.add(i2);
        newItems.add(i3);
        items.setValue(newItems);
        return items;
    }
}
