package com.example.cis2208_workouttracker.ui.workouts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cis2208_workouttracker.R;
import com.example.cis2208_workouttracker.backend.DbHelper;
import com.example.cis2208_workouttracker.backend.ExerciseUtility;
import com.example.cis2208_workouttracker.domainModels.RepExercise;
import com.example.cis2208_workouttracker.domainModels.TimedExercise;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.Time;

public class EditTimedExerciseActivity extends AppCompatActivity {

    long exerciseId;
    long workoutId;
    DbHelper _dbHelper;
    ExerciseUtility _exerciseUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_edit_timed_exercise);
        //Get the id from the incoming intent
        Intent intent = getIntent();
        exerciseId = intent.getLongExtra("exerciseId", -1);
        //Create db tools and and initialise instance in memory
        _dbHelper = new DbHelper(this);
        _exerciseUtil = new ExerciseUtility(_dbHelper);
        TimedExercise exercise = (TimedExercise) _exerciseUtil.getTimedExerciseById(exerciseId);
        workoutId = exercise.getWorkoutId();
        //Show the current exercise details
        populateScreen(exercise);

        Button confirmBtn = findViewById(R.id.confirm_btn);
        confirmBtn.setOnClickListener(this::onClickConfirm);
    }

    private void populateScreen(TimedExercise exercise){
        //Set title to Edit Exercise
        TextView headingView = findViewById(R.id.create_edit_timed_heading);
        headingView.setText(R.string.edit_exercise_heading);
        //Populate input views
        TextInputEditText nameInput = findViewById(R.id.timed_name_input);
        nameInput.setText(exercise.getName());

        TextInputEditText setsInput = findViewById(R.id.timed_sets_input);
        int sets = exercise.getNoOfSets();
        String setsString = String.valueOf(sets);
        setsInput.setText(setsString);

        //Process minutes and seconds separately
        Time time = exercise.getTime();
        int minutes = time.getMinutes();
        int seconds = time.getSeconds();
        String minString = String.valueOf(minutes);
        String secString = String.valueOf(seconds);

        TextInputEditText minutesInput = findViewById(R.id.minutes_input);
        minutesInput.setText(minString);
        TextInputEditText secondsInput = findViewById(R.id.seconds_input);
        secondsInput.setText(secString);

        TextInputEditText weightInput = findViewById(R.id.timed_weight_input);
        double weight = exercise.getWeight();
        String weightString = Double.toString(weight);
        weightInput.setText(weightString);
    }

    private void onClickConfirm(View view) {
        //Get the new values
        TextInputEditText nameInput = findViewById(R.id.timed_name_input);
        String name = nameInput.getEditableText().toString();

        TextInputEditText setsInput = findViewById(R.id.timed_sets_input);
        String setsValue = setsInput.getEditableText().toString();
        int sets = Integer.parseInt(setsValue);
        //Minutes and seconds taken separately to create Time() instance
        TextInputEditText minutesInput = findViewById(R.id.minutes_input);
        String minValue = minutesInput.getEditableText().toString();
        int min = Integer.parseInt(minValue);

        TextInputEditText secondsInput = findViewById(R.id.seconds_input);
        String secValue = secondsInput.getEditableText().toString();
        int sec = Integer.parseInt(secValue);

        Time time = new Time(0, min, sec);

        TextInputEditText weightInput = findViewById(R.id.timed_weight_input);
        String weightValue = weightInput.getEditableText().toString();
        double weight = Double.parseDouble(weightValue);

        TimedExercise exercise = new TimedExercise(exerciseId, name, sets, weight, time, workoutId);
        _exerciseUtil.updateTimedExercise(exercise);

        Intent intent = new Intent(this, EditWorkoutActivity.class);
        intent.putExtra("workoutId", workoutId);
        startActivity(intent);
    }
}