package com.example.cis2208_workouttracker.backend.contracts;

import android.provider.BaseColumns;

public class WorkoutsContract {

    private WorkoutsContract(){}
        //Define the table contents for workouts
        public static class WorkoutEntry implements BaseColumns{
            public static final String TABLE_NAME = "workouts";
            public static final String COLUMN_NAME_NAME = "name";
        }
}
