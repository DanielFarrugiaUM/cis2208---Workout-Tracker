package com.example.cis2208_workouttracker.ui.workouts;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cis2208_workouttracker.backend.DbHelper;
import com.example.cis2208_workouttracker.backend.ExerciseUtility;
import com.example.cis2208_workouttracker.backend.HistoryUtility;
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
    //Get data from db to display in history fragment
    public MutableLiveData<List<HistoryItem>> getItems(Context context, long date){
        DbHelper dbHelper = new DbHelper(context);
        HistoryUtility histUtil = new HistoryUtility(dbHelper);
        ArrayList<HistoryItem> newItems = histUtil.getHistItemsByDate(date);
        items.setValue(newItems);
        return items;
    }
}
