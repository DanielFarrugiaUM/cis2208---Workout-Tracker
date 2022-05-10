package com.example.cis2208_workouttracker.backend;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.example.cis2208_workouttracker.backend.contracts.WorkoutsContract;
import com.example.cis2208_workouttracker.domainModels.Workout;

import java.util.ArrayList;

public class WorkoutsUtility {

    private final SQLiteOpenHelper _dbHelper;

    public WorkoutsUtility(DbHelper dbHelper){
        this._dbHelper = dbHelper;
    }

    //Add a workout to db
    public long insertWorkout(Workout workout){
        SQLiteDatabase db = _dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(WorkoutsContract.WorkoutEntry.COLUMN_NAME_NAME, workout.name);
        return db.insert(
                WorkoutsContract.WorkoutEntry.TABLE_NAME,
                null,
                values
        );
    }

    //Get an array of workouts
    public ArrayList<Workout> getWorkouts(){
        ArrayList<Workout> workouts = new ArrayList<>();
        SQLiteDatabase db = _dbHelper.getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                WorkoutsContract.WorkoutEntry.COLUMN_NAME_NAME
        };

        Cursor cursor = db.query(
                WorkoutsContract.WorkoutEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()){
            long id = cursor.getLong(
                    cursor.getColumnIndexOrThrow(BaseColumns._ID)
            );
            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(WorkoutsContract.WorkoutEntry.COLUMN_NAME_NAME)
            );
            Workout workout = new Workout(name);
            workouts.add(workout);
        }

        cursor.close();
        return workouts;
    }

    //Get a workout by id
    public Workout getWorkoutById(long id){
        SQLiteDatabase db = _dbHelper.getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                WorkoutsContract.WorkoutEntry.COLUMN_NAME_NAME
        };
        // WHERE "id" = condition
        String selection = BaseColumns._ID + " = ?";
        String[] selectionArgs = { Long.toString(id) };

        Cursor cursor = db.query(
                WorkoutsContract.WorkoutEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        Workout workout = null;

        while (cursor.moveToNext()){
            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(WorkoutsContract.WorkoutEntry.COLUMN_NAME_NAME)
            );
            workout = new Workout(name);
        }
        cursor.close();
        return workout;
    }

    //Delete all workouts from db
    public void removeAll(){
        SQLiteDatabase db = _dbHelper.getWritableDatabase();
        db.delete(WorkoutsContract.WorkoutEntry.TABLE_NAME, null, null);
    }
}
