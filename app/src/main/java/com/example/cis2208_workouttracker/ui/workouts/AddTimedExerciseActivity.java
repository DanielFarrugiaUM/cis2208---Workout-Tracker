package com.example.cis2208_workouttracker.ui.workouts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cis2208_workouttracker.R;
import com.example.cis2208_workouttracker.backend.DbHelper;
import com.example.cis2208_workouttracker.backend.ExerciseUtility;
import com.example.cis2208_workouttracker.domainModels.TimedExercise;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.Time;

public class AddTimedExerciseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_timed_exercise);

        Button confirmBtn = findViewById(R.id.confirm_btn);
        confirmBtn.setOnClickListener(this::onClickConfirm);
    }

    private void onClickConfirm(View view) {
        Intent intent = getIntent();
        long workoutId = intent.getLongExtra("workoutId", -1);
        //Get Name
        TextInputEditText nameInput = findViewById(R.id.timed_name_input);
        String name = nameInput.getEditableText().toString();
        //Get sets
        TextInputEditText setsInput = findViewById(R.id.timed_sets_input);
        String setsValue = setsInput.getEditableText().toString();
        int sets = Integer.parseInt(setsValue);
        //Get time in two parts
        //Minutes
        TextInputEditText minutesInput = findViewById(R.id.minutes_input);
        String minutesValue = minutesInput.getEditableText().toString();
        int minutes = Integer.parseInt(minutesValue);
        //Seconds
        TextInputEditText secondsInput = findViewById(R.id.seconds_input);
        String secondsValue = secondsInput.getEditableText().toString();
        int sec = Integer.parseInt(secondsValue);
        //Get Weight
        TextInputEditText weightInput = findViewById(R.id.timed_weight_input);
        String weightValue = weightInput.getEditableText().toString();
        double weight = Double.parseDouble(weightValue);
        Time time = new Time(0, minutes, sec);
        //Create the exercise, save on db and go back to edit workouts
        TimedExercise exercise = new TimedExercise(name, sets, weight, time, workoutId);
        DbHelper dbHelper = new DbHelper(this);
        ExerciseUtility exerciseUtility = new ExerciseUtility(dbHelper);
        long eId = exerciseUtility.insertTimedExercise(exercise);
        //Go back to edit workout
        intent = new Intent(this, EditWorkoutActivity.class);
        intent.putExtra("workoutId", workoutId);
        startActivity(intent);
    }
}