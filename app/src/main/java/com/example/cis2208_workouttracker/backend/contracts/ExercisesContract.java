package com.example.cis2208_workouttracker.backend.contracts;

import android.provider.BaseColumns;

public class ExercisesContract {

    private ExercisesContract(){}
    //Define the content for Exercises
    public static class ExerciseEntry implements BaseColumns {
        public static final String TABLE_NAME = "exercises";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_SETS = "sets";
        public static final String COLUMN_NAME_WEIGHT = "weight";
        public static final String COLUMN_NAME_WORKOUT = "wId"; //FK
        public static final String COLUMN_NAME_REPS = "reps";
        public static final String COLUMN_NAME_TIME = "time";
    }
}
