package com.example.cis2208_workouttracker.backend;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.example.cis2208_workouttracker.backend.contracts.ExercisesContract;
import com.example.cis2208_workouttracker.backend.contracts.HistoryItemContract;
import com.example.cis2208_workouttracker.domainModels.Exercise;
import com.example.cis2208_workouttracker.domainModels.HistoryItem;
import com.example.cis2208_workouttracker.domainModels.RepExercise;
import com.example.cis2208_workouttracker.domainModels.TimedExercise;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

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
        values.put(dateCol, historyItem.getDate());
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
        values.put(dateCol, historyItem.getDate());
        return db.insert(
                _tHistTableName,
                null,
                values
        );
    }

    public List<HistoryItem> getRepHistItemsByDate(long date){
        SQLiteDatabase db = _dbHelper.getReadableDatabase();
        ArrayList<HistoryItem> items = new ArrayList<>();

        String[] projection = {
                nameCol,
                setsCol,
                repsCol,
                weightCol
        };
        // WHERE "date" = condition
        String selection = dateCol + " = ?";
        String[] selectionArgs = { Long.toString(date) };

        Cursor cursor = db.query(
                _rHistTableName,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        HistoryItem item;

        while (cursor.moveToNext()){
            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(nameCol)
            );
            int sets = cursor.getInt(
                    cursor.getColumnIndexOrThrow(setsCol)
            );
            int reps = cursor.getInt(
                    cursor.getColumnIndexOrThrow(repsCol)
            );
            double weight = cursor.getDouble(
                    cursor.getColumnIndexOrThrow(weightCol)
            );
            //Workout fk not needed, used -1
            RepExercise exercise = new RepExercise(name, sets, weight, reps, -1);
            item = new HistoryItem(exercise);
            items.add(item);
        }
        cursor.close();
        return items;
    }

    public List<HistoryItem> getTimedHistItemsByDate(long date){
        SQLiteDatabase db = _dbHelper.getReadableDatabase();
        ArrayList<HistoryItem> items = new ArrayList<>();

        String[] projection = {
                nameCol,
                setsCol,
                timeCol,
                weightCol
        };
        // WHERE "date" = condition
        String selection = dateCol + " = ?";
        String[] selectionArgs = { Long.toString(date) };

        Cursor cursor = db.query(
                _tHistTableName,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        HistoryItem item;

        while (cursor.moveToNext()){
            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(nameCol)
            );
            int sets = cursor.getInt(
                    cursor.getColumnIndexOrThrow(setsCol)
            );
            int totalSeconds = cursor.getInt(
                    cursor.getColumnIndexOrThrow(timeCol)
            );
            double weight = cursor.getDouble(
                    cursor.getColumnIndexOrThrow(weightCol)
            );

            int minutes = TimedExercise.getMinutes(totalSeconds);
            int seconds = TimedExercise.getRemainderSeconds(totalSeconds);
            Time time = new Time(0, minutes, seconds);
            //Workout fk not needed, used -1
            TimedExercise exercise = new TimedExercise(name, sets, weight, time, -1);
            item = new HistoryItem(exercise);
            items.add(item);
        }
        cursor.close();
        return items;
    }

    public ArrayList<HistoryItem> getHistItemsByDate(long date){
        ArrayList<HistoryItem> allItems = new ArrayList<>();

        List<HistoryItem> timedItems = getTimedHistItemsByDate(date);
        List<HistoryItem> repItems = getRepHistItemsByDate(date);

        allItems.addAll(timedItems);
        allItems.addAll(repItems);
        return  allItems;
    }
}
