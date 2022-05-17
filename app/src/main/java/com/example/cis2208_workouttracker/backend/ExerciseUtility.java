package com.example.cis2208_workouttracker.backend;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.example.cis2208_workouttracker.backend.contracts.ExercisesContract;
import com.example.cis2208_workouttracker.backend.contracts.WorkoutsContract;
import com.example.cis2208_workouttracker.domainModels.Exercise;
import com.example.cis2208_workouttracker.domainModels.RepExercise;
import com.example.cis2208_workouttracker.domainModels.TimedExercise;
import com.example.cis2208_workouttracker.domainModels.Workout;

import java.util.ArrayList;
import java.util.List;

public class ExerciseUtility {

    private final SQLiteOpenHelper _dbHelper;

    public ExerciseUtility(DbHelper dbHelper){ this._dbHelper =dbHelper; }

    //Add an rep exercise to db
    public long insertRepExercise(RepExercise exercise){
        SQLiteDatabase db = _dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ExercisesContract.ExerciseEntry.COLUMN_NAME_NAME, exercise.getName());
        values.put(ExercisesContract.ExerciseEntry.COLUMN_NAME_SETS, exercise.getNoOfSets());
        values.put(ExercisesContract.ExerciseEntry.COLUMN_NAME_REPS, exercise.getNoOfReps());
        values.put(ExercisesContract.ExerciseEntry.COLUMN_NAME_WEIGHT, exercise.getWeight());
        values.put(ExercisesContract.ExerciseEntry.COLUMN_NAME_WORKOUT, exercise.getWorkoutId());
        return db.insert(
                ExercisesContract.ExerciseEntry.TABLE_NAME_REPS,
                null,
                values
        );
    }

    //Add an rep exercise to db
    public long insertTimedExercise(TimedExercise exercise){
        SQLiteDatabase db = _dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ExercisesContract.ExerciseEntry.COLUMN_NAME_NAME, exercise.getName());
        values.put(ExercisesContract.ExerciseEntry.COLUMN_NAME_SETS, exercise.getNoOfSets());
        values.put(ExercisesContract.ExerciseEntry.COLUMN_NAME_TIME, exercise.getTotalSeconds());
        values.put(ExercisesContract.ExerciseEntry.COLUMN_NAME_WORKOUT, exercise.getWorkoutId());
        return db.insert(
                ExercisesContract.ExerciseEntry.TABLE_NAME_TIMED,
                null,
                values
        );
    }

    //Get Exercises belonging to a particular workout
    public List<Exercise> getRepExerciseByFK(long fk){
        SQLiteDatabase db = _dbHelper.getReadableDatabase();
        ArrayList<Exercise> exercises = new ArrayList<>();
        //Aliases to shorten code
        String nameCol = ExercisesContract.ExerciseEntry.COLUMN_NAME_NAME;
        String setsCol = ExercisesContract.ExerciseEntry.COLUMN_NAME_SETS;
        String repsCol =  ExercisesContract.ExerciseEntry.COLUMN_NAME_REPS;
        String weightCol = ExercisesContract.ExerciseEntry.COLUMN_NAME_WEIGHT;
        String workoutFkCol =  ExercisesContract.ExerciseEntry.COLUMN_NAME_WORKOUT;

        String[] projection = {
                BaseColumns._ID,
                nameCol,
                setsCol,
                repsCol,
                weightCol,
                workoutFkCol
        };
        // WHERE "fk" = condition
        String selection = ExercisesContract.ExerciseEntry.COLUMN_NAME_WORKOUT + " = ?";
        String[] selectionArgs = { Long.toString(fk) };

        Cursor cursor = db.query(
                WorkoutsContract.WorkoutEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        RepExercise exercise = null;

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
            long workoutFK = cursor.getLong(
                    cursor.getColumnIndexOrThrow(workoutFkCol)
            );
            exercise = new RepExercise(name, sets, weight, reps, workoutFK);
            exercises.add(exercise);
        }
        cursor.close();
        return exercises;
    }

}
