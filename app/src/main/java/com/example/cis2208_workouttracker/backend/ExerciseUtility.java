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

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class ExerciseUtility {

    private final SQLiteOpenHelper _dbHelper;
    //Aliases to shorten code
    String repsTable = ExercisesContract.ExerciseEntry.TABLE_NAME_REPS;
    String timedTable = ExercisesContract.ExerciseEntry.TABLE_NAME_TIMED;
    String nameCol = ExercisesContract.ExerciseEntry.COLUMN_NAME_NAME;
    String setsCol = ExercisesContract.ExerciseEntry.COLUMN_NAME_SETS;
    String repsCol =  ExercisesContract.ExerciseEntry.COLUMN_NAME_REPS;
    String timeCol = ExercisesContract.ExerciseEntry.COLUMN_NAME_TIME;
    String weightCol = ExercisesContract.ExerciseEntry.COLUMN_NAME_WEIGHT;
    String workoutFkCol =  ExercisesContract.ExerciseEntry.COLUMN_NAME_WORKOUT;

    public ExerciseUtility(DbHelper dbHelper){ this._dbHelper =dbHelper; }

    //Insertions-------------------------------------------------------------
    //Add an rep exercise to db
    public long insertRepExercise(RepExercise exercise){
        SQLiteDatabase db = _dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(nameCol, exercise.getName());
        values.put(setsCol, exercise.getNoOfSets());
        values.put(repsCol, exercise.getNoOfReps());
        values.put(weightCol, exercise.getWeight());
        values.put(workoutFkCol, exercise.getWorkoutId());
        return db.insert(
                repsTable,
                null,
                values
        );
    }

    //Add an rep exercise to db
    public long insertTimedExercise(TimedExercise exercise){
        SQLiteDatabase db = _dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(nameCol, exercise.getName());
        values.put(setsCol, exercise.getNoOfSets());
        values.put(timeCol, exercise.getTotalSeconds());
        values.put(weightCol, exercise.getWeight());
        values.put(workoutFkCol, exercise.getWorkoutId());
        return db.insert(
                timedTable,
                null,
                values
        );
    }

    //Select-----------------------------------------------------------------
    //Get Exercises belonging to a particular workout
    public ArrayList<Exercise> getAllExercisesByFK(long fk){
        ArrayList<Exercise> exercises = new ArrayList<>();

        List<Exercise> repExercises = getRepExerciseByFK(fk);
        List<Exercise> timedExercises = getTimedExerciseByFK(fk);

        exercises.addAll(repExercises);
        exercises.addAll(timedExercises);

        return exercises;
    }

    //Get Rep Exercises belonging to a particular workout
    public List<Exercise> getRepExerciseByFK(long fk){
        SQLiteDatabase db = _dbHelper.getReadableDatabase();
        ArrayList<Exercise> exercises = new ArrayList<>();

        String[] projection = {
                BaseColumns._ID,
                nameCol,
                setsCol,
                repsCol,
                weightCol,
                workoutFkCol
        };
        // WHERE "fk" = condition
        String selection = workoutFkCol + " = ?";
        String[] selectionArgs = { Long.toString(fk) };

        Cursor cursor = db.query(
                repsTable,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        RepExercise exercise;

        while (cursor.moveToNext()){
            long id = cursor.getLong(
                    cursor.getColumnIndexOrThrow(BaseColumns._ID)
            );
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
            exercise = new RepExercise(id, name, sets, weight, reps, workoutFK);
            exercises.add(exercise);
        }
        cursor.close();
        return exercises;
    }

    //Get Timed Exercises belonging to a particular workout
    public List<Exercise> getTimedExerciseByFK(long fk){
        SQLiteDatabase db = _dbHelper.getReadableDatabase();
        ArrayList<Exercise> exercises = new ArrayList<>();

        String[] projection = {
                BaseColumns._ID,
                nameCol,
                setsCol,
                timeCol,
                weightCol,
                workoutFkCol
        };
        // WHERE "fk" = condition
        String selection = workoutFkCol + " = ?";
        String[] selectionArgs = { Long.toString(fk) };

        Cursor cursor = db.query(
                timedTable,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        TimedExercise exercise;

        while (cursor.moveToNext()){
            long id = cursor.getLong(
                    cursor.getColumnIndexOrThrow(BaseColumns._ID)
            );
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
            long workoutFK = cursor.getLong(
                    cursor.getColumnIndexOrThrow(workoutFkCol)
            );

            //Build the time
            int minutes = TimedExercise.getMinutes(totalSeconds);
            int seconds = TimedExercise.getRemainderSeconds(totalSeconds);
            Time time = new Time(0, minutes, seconds);
            exercise = new TimedExercise(id, name, sets, weight, time, workoutFK);
            exercises.add(exercise);
        }
        cursor.close();
        return exercises;
    }

    //Deletions--------------------------------------------------------------
    //Delete a rep exercise by id
    public void removeRepExerciseById(long id){
        SQLiteDatabase db = _dbHelper.getWritableDatabase();
        String whereClause = BaseColumns._ID + "=?";
        String[] whereArgs = { Long.toString(id) };
        db.delete(repsTable, whereClause, whereArgs);
    }

    //Delete a rep exercise by id
    public void removeTimedExerciseById(long id){
        SQLiteDatabase db = _dbHelper.getWritableDatabase();
        String whereClause = BaseColumns._ID + "=?";
        String[] whereArgs = { Long.toString(id) };
        db.delete(timedTable, whereClause, whereArgs);
    }
}
