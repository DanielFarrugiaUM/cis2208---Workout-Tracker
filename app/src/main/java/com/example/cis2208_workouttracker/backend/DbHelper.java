package com.example.cis2208_workouttracker.backend;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cis2208_workouttracker.backend.contracts.ExercisesContract;
import com.example.cis2208_workouttracker.backend.contracts.WorkoutsContract;

public class DbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database
    // version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "workout_manager.db";
    //Workouts Table
    private final String _workoutsTableName = WorkoutsContract.WorkoutEntry.TABLE_NAME;
    private final String _wName = WorkoutsContract.WorkoutEntry.COLUMN_NAME_NAME;
    //Exercises Table
    private final String _exercisesTableName = ExercisesContract.ExerciseEntry.TABLE_NAME;
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
        db.execSQL(createTables());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(dropTables());
        db.execSQL(createTables());
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }

    private String createTables() {
        return createWorkoutsTable() + createExercisesTable();
    }

    private String createWorkoutsTable(){
        return "CREATE TABLE " + _workoutsTableName + " ("
                + WorkoutsContract.WorkoutEntry._ID + " INTEGER PRIMARY KEY, "
                + _wName + " varchar);";
    }

    private String createExercisesTable(){
        return "CREATE TABLE " + _exercisesTableName + " ("
                + ExercisesContract.ExerciseEntry._ID + " INTEGER PRIMARY KEY, "
                + _eName + " varchar, "
                + _eWorkoutId + " INT, "
                + _eSets + " INT, "
                + _eWeight + " REAL, "
                + _eReps + " INT, "
                + _eTime + " DATETIME);";
    }
    private String dropTables() {
        return "";
    }

    private String DropWorkoutsTable(){
        return "DROP TABLE IF EXISTS "
                + _workoutsTableName + ";";
    }

    private String DropExercisesTable(){
        return "DROP TABLE IF EXISTS "
                + _exercisesTableName + ";";
    }
}
