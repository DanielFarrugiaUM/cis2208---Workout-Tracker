package com.example.cis2208_workouttracker.backend;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cis2208_workouttracker.backend.contracts.ExercisesContract;
import com.example.cis2208_workouttracker.backend.contracts.WorkoutsContract;

public class DbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database
    // version.

    //Not that SQL statements separated by a ; cannot be executed with one
    //db.execSQL() call
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "workout_manager.db";
    //Workouts Table
    private final String _workoutsTableName = WorkoutsContract.WorkoutEntry.TABLE_NAME;
    private final String _wName = WorkoutsContract.WorkoutEntry.COLUMN_NAME_NAME;
    //Exercises Table
    private final String _repExercisesTableName = ExercisesContract.ExerciseEntry.TABLE_NAME_REPS;
    private final String _timedExercisesTableName = ExercisesContract.ExerciseEntry.TABLE_NAME_TIMED;
    private final String _eName = ExercisesContract.ExerciseEntry.COLUMN_NAME_NAME;
    private final String _eSets = ExercisesContract.ExerciseEntry.COLUMN_NAME_SETS;
    private final String _eWeight = ExercisesContract.ExerciseEntry.COLUMN_NAME_WEIGHT;
    private final String _eWorkoutId = ExercisesContract.ExerciseEntry.COLUMN_NAME_WORKOUT;
    private final String _eReps = ExercisesContract.ExerciseEntry.COLUMN_NAME_REPS;
    private final String _eTime = ExercisesContract.ExerciseEntry.COLUMN_NAME_TIME;

    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createWorkoutsTable());
        db.execSQL(createRepExercisesTable());
        db.execSQL(createTimedExerciseTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //Drop previous tables
        db.execSQL(dropWorkoutsTable());
        db.execSQL(dropRepExercisesTable());
        db.execSQL(dropTimedExercisesTable());
        //Create new ones
        db.execSQL(createWorkoutsTable());
        db.execSQL(createRepExercisesTable());
        db.execSQL(createTimedExerciseTable());
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private String createWorkoutsTable(){
        return "CREATE TABLE " + _workoutsTableName + " ("
                + WorkoutsContract.WorkoutEntry._ID + " INTEGER PRIMARY KEY, "
                + _wName + " varchar);";
    }

    private String createRepExercisesTable(){
        return "CREATE TABLE " + _repExercisesTableName + " ("
                + ExercisesContract.ExerciseEntry._ID + " INTEGER PRIMARY KEY, "
                + _eName + " varchar, "
                + _eWorkoutId + " INTEGER, "
                + _eSets + " INTEGER, "
                + _eWeight + " NUMERIC, "
                + _eReps + " INTEGER);";
    }

    private String createTimedExerciseTable(){
        return "CREATE TABLE " + _timedExercisesTableName + " ("
                + ExercisesContract.ExerciseEntry._ID + " INTEGER PRIMARY KEY, "
                + _eName + " varchar, "
                + _eWorkoutId + " INTEGER, "
                + _eSets + " INTEGER, "
                + _eWeight + " INTEGER, "
                + _eTime + " INTEGER);"; //WILL NEED TO CONVERT TIME
    }

    private String dropWorkoutsTable(){
        return "DROP TABLE IF EXISTS "
                + _workoutsTableName + ";";
    }

    private String dropRepExercisesTable(){
        return "DROP TABLE IF EXISTS "
                + _repExercisesTableName + ";";
    }

    private String dropTimedExercisesTable(){
        return "DROP TABLE IF EXISTS "
                + _timedExercisesTableName + ";";
    }
}
