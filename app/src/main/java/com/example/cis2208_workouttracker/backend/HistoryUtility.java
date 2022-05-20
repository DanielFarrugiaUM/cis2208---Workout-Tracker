package com.example.cis2208_workouttracker.backend;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cis2208_workouttracker.backend.contracts.ExercisesContract;
import com.example.cis2208_workouttracker.backend.contracts.HistoryItemContract;
import com.example.cis2208_workouttracker.domainModels.HistoryItem;

public class HistoryUtility {

    private final SQLiteOpenHelper _dbHelper;
    //Aliases to shorten code
    private final  String _rHistTableName = HistoryItemContract.HistoryEntry.TABLE_NAME_REPS;
    private final String _tHistTableName = HistoryItemContract.HistoryEntry.TABLE_NAME_TIMED;
    private final String nameCol = HistoryItemContract.HistoryEntry.COLUMN_NAME_NAME;
    private final String repsCol = HistoryItemContract.HistoryEntry.COLUMN_NAME_REPS;
    private final String weightCol = HistoryItemContract.HistoryEntry.COLUMN_NAME_WEIGHT;
    private final String setsCol = HistoryItemContract.HistoryEntry.COLUMN_NAME_SETS;
    private final String timeCol = HistoryItemContract.HistoryEntry.COLUMN_NAME_TIME;
    private final String dateCol = HistoryItemContract.HistoryEntry.COLUMN_NAME_DATE;

    public HistoryUtility(DbHelper dbHelper){
        this._dbHelper = dbHelper;
    }

    public long insertRepHistoryItem(HistoryItem historyItem){
        SQLiteDatabase db = _dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(nameCol, historyItem.getName());
        values.put(setsCol, historyItem.getNoOfSets());
        values.put(repsCol, historyItem.getReps());
        values.put(weightCol, historyItem.getWeight());
        values.put(dateCol, historyItem.getDate().getDate());
        return db.insert(
                _rHistTableName,
                null,
                values
        );
    }

    public long insertTimedHistoryItem(HistoryItem historyItem){
        SQLiteDatabase db = _dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(nameCol, historyItem.getName());
        values.put(setsCol, historyItem.getNoOfSets());
        values.put(timeCol, historyItem.getTotalSeconds());
        values.put(weightCol, historyItem.getWeight());
        values.put(dateCol, historyItem.getDate().getDate());
        return db.insert(
                _tHistTableName,
                null,
                values
        );
    }
}
