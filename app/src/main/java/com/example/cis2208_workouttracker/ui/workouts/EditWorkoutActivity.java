package com.example.cis2208_workouttracker.ui.workouts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.cis2208_workouttracker.R;
import com.example.cis2208_workouttracker.backend.DbHelper;
import com.example.cis2208_workouttracker.backend.WorkoutsUtility;
import com.example.cis2208_workouttracker.domainModels.Workout;

public class EditWorkoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout);
        setWorkoutName();
    }

    private void setWorkoutName(){
        //Get the id from previous activity
        Intent intent = getIntent();
        long id = intent.getLongExtra("workoutId", -1);

        //Get Workout from db
        DbHelper dbHelper = new DbHelper(this);
        WorkoutsUtility workoutsUtility = new WorkoutsUtility(dbHelper);
        Workout workout = workoutsUtility.getWorkoutById(id);

        //Set The name to appear in text view
        TextView workoutNameView = findViewById(R.id.workout_name_edit);
        String workoutName = workout.getName();
        workoutNameView.setText(workoutName);
    }
}