package com.example.cis2208_workouttracker.backend.contracts;

import android.provider.BaseColumns;

public class HistoryItemContract {

    private HistoryItemContract(){}
    //Define the content for HistoryItems
    public static class HistoryEntry implements BaseColumns {
        public static final String TABLE_NAME_REPS = "rep_history";
        public static final String TABLE_NAME_TIMED = "timed_history";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_SETS = "sets";
        public static final String COLUMN_NAME_WEIGHT = "weight";
        public static final String COLUMN_NAME_REPS = "reps";
        public static final String COLUMN_NAME_TIME = "time";
        public static final String COLUMN_NAME_DATE = "date";
    }
}
