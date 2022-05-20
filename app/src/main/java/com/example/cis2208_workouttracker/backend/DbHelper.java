package com.example.cis2208_workouttracker.backend;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cis2208_workouttracker.backend.contracts.ExercisesContract;
import com.example.cis2208_workouttracker.backend.contracts.HistoryItemContract;
import com.example.cis2208_workouttracker.backend.contracts.WorkoutsContract;

public class DbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database
    // version.

    //Not that SQL statements separated by a ; cannot be executed with one
    //db.execSQL() call
    public static final int DATABASE_VERSION = 10;
    public static final String DATABASE_NAME = "workout_manager.db";
    //Workouts Table properties
    private final String _workoutsTableName = WorkoutsContract.WorkoutEntry.TABLE_NAME;
    private final String _wName = WorkoutsContract.WorkoutEntry.COLUMN_NAME_NAME;
    //Exercises Table properties
    private final String _repExercisesTableName = ExercisesContract.ExerciseEntry.TABLE_NAME_REPS;
    private final String _timedExercisesTableName = ExercisesContract.ExerciseEntry.TABLE_NAME_TIMED;
    private final String _eName = ExercisesContract.ExerciseEntry.COLUMN_NAME_NAME;
    private final String _eSets = ExercisesContract.ExerciseEntry.COLUMN_NAME_SETS;
    private final String _eWeight = ExercisesContract.ExerciseEntry.COLUMN_NAME_WEIGHT;
    private final String _eWorkoutId = ExercisesContract.ExerciseEntry.COLUMN_NAME_WORKOUT;
    private final String _eReps = ExercisesContract.ExerciseEntry.COLUMN_NAME_REPS;
    private final String _eTime = ExercisesContract.ExerciseEntry.COLUMN_NAME_TIME;
    //History Table properties
    private final  String _rHistTableName = HistoryItemContract.HistoryEntry.TABLE_NAME_REPS;
    private final String _tHistTableName = HistoryItemContract.HistoryEntry.TABLE_NAME_TIMED;
    private final String _hisName = HistoryItemContract.HistoryEntry.COLUMN_NAME_NAME;
    private final String _histReps = HistoryItemContract.HistoryEntry.COLUMN_NAME_REPS;
    private final String _histWeight = HistoryItemContract.HistoryEntry.COLUMN_NAME_WEIGHT;
    private final String _histSets = HistoryItemContract.HistoryEntry.COLUMN_NAME_SETS;
    private final String _histTime = HistoryItemContract.HistoryEntry.COLUMN_NAME_TIME;
    private final String _histDate = HistoryItemContract.HistoryEntry.COLUMN_NAME_DATE;
    //Pragma for onOpen()
    private final String _pragma = "PRAGMA foreign_keys = ON";

    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Important for DELETE CASCADE to work
    @Override
    public void onOpen(SQLiteDatabase db){
        super.onOpen(db);
        db.execSQL(_pragma);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createWorkoutsTable());
        db.execSQL(createRepExercisesTable());
        db.execSQL(createTimedExerciseTable());
        db.execSQL(createTimedHistoryTable());
        db.execSQL(createRepHistoryTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //Drop previous tables
        db.execSQL(dropWorkoutsTable());
        db.execSQL(dropRepExercisesTable());
        db.execSQL(dropTimedExercisesTable());
        db.execSQL(dropRepHistTable());
        db.execSQL(dropTimedHistTable());
        //Create new ones
        db.execSQL(createWorkoutsTable());
        db.execSQL(createRepExercisesTable());
        db.execSQL(createTimedExerciseTable());
        db.execSQL(createTimedHistoryTable());
        db.execSQL(createRepHistoryTable());
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
                + _eReps + " INTEGER, "
                + "CONSTRAINT fk_workouts_rep "
                + "FOREIGN KEY (" + _eWorkoutId + ") "
                + "REFERENCES " + _workoutsTableName + "("+ WorkoutsContract.WorkoutEntry._ID +") "
                + "ON DELETE CASCADE"
                + ");";
    }

    private String createTimedExerciseTable(){
        return "CREATE TABLE " + _timedExercisesTableName + " ("
                + ExercisesContract.ExerciseEntry._ID + " INTEGER PRIMARY KEY, "
                + _eName + " varchar, "
                + _eWorkoutId + " INTEGER, "
                + _eSets + " INTEGER, "
                + _eWeight + " NUMERIC, "
                + _eTime + " INTEGER, "
                + "CONSTRAINT fk_workouts_timed "
                + "FOREIGN KEY (" + _eWorkoutId + ") "
                + "REFERENCES " + _workoutsTableName + "("+ WorkoutsContract.WorkoutEntry._ID +") "
                + "ON DELETE CASCADE"
                + ");";
    }

    private String createRepHistoryTable(){
        return "CREATE TABLE " + _rHistTableName + " ("
                + HistoryItemContract.HistoryEntry._ID + " INTEGER PRIMARY KEY, "
                + _histDate + " INTEGER, "
                + _hisName + " varchar, "
                + _histSets + " INTEGER, "
                + _histReps + " INTEGER, "
                + _histWeight + " NUMERIC"
                + ");";
    }

    private String createTimedHistoryTable(){
        return "CREATE TABLE " + _tHistTableName + " ("
                + HistoryItemContract.HistoryEntry._ID + " INTEGER PRIMARY KEY, "
                + _histDate + " INTEGER, "
                + _hisName + " varchar, "
                + _histSets + " INTEGER, "
                + _histTime + " INTEGER, "
                + _histWeight + " NUMERIC"
                + ");";
    }

    private String dropRepHistTable(){
        return "DROP TABLE IF EXISTS "
                + _rHistTableName + ";";
    }

    private String dropTimedHistTable(){
        return "DROP TABLE IF EXISTS "
                + _tHistTableName + ";";
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
