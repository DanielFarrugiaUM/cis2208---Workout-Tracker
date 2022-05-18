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
import com.google.android.material.textfield.TextInputEditText;

public class EditRepExerciseActivity extends AppCompatActivity {

    long exerciseId;
    long workoutId;
    DbHelper _dbHelper;
    ExerciseUtility _exerciseUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_rep_exercise);
        //Get the id from the incoming intent
        Intent intent = getIntent();
        exerciseId = intent.getLongExtra("exerciseId", -1);
        //Create db tools and and initialise instance in memory
        _dbHelper = new DbHelper(this);
        _exerciseUtil = new ExerciseUtility(_dbHelper);
        RepExercise exercise = (RepExercise) _exerciseUtil.getRepExerciseById(exerciseId);
        workoutId = exercise.getWorkoutId();
        //Show the current exercise details
        populateScreen(exercise);

        Button confirmBtn = findViewById(R.id.confirm_btn);
        confirmBtn.setOnClickListener(this::onClickConfirm);
    }

    private void populateScreen(RepExercise exercise){
        //Set title to Edit Exercise
        TextView headingView = findViewById(R.id.create_rep_heading);
        headingView.setText(R.string.edit_exercise_heading);
        //Populate input views

        TextInputEditText nameInput = findViewById(R.id.rep_name_input);
        nameInput.setText(exercise.getName());

        TextInputEditText setsInput = findViewById(R.id.rep_sets_input);
        int sets = exercise.getNoOfSets();
        String setsString = String.valueOf(sets);
        setsInput.setText(setsString);

        TextInputEditText repsInput = findViewById(R.id.rep_reps_input);
        int reps = exercise.getNoOfReps();
        String repsString = String.valueOf(reps);
        repsInput.setText(repsString);

        TextInputEditText weightInput = findViewById(R.id.rep_weight_input);
        double weight = exercise.getWeight();
        String weightString = Double.toString(weight);
        weightInput.setText(weightString);
    }

    private void onClickConfirm(View view) {
        //Get the new values
        TextInputEditText nameInput = findViewById(R.id.rep_name_input);
        String name = nameInput.getEditableText().toString();

        TextInputEditText setsInput = findViewById(R.id.rep_sets_input);
        String setsValue = setsInput.getEditableText().toString();
        int sets = Integer.parseInt(setsValue);

        TextInputEditText repsInput = findViewById(R.id.rep_reps_input);
        String repsValue = repsInput.getEditableText().toString();
        int reps = Integer.parseInt(repsValue);

        TextInputEditText weightInput = findViewById(R.id.rep_weight_input);
        String weightValue = weightInput.getEditableText().toString();
        double weight = Double.parseDouble(weightValue);

        RepExercise exercise = new RepExercise(exerciseId, name, sets, weight, reps, workoutId);
        _exerciseUtil.updateRepExercise(exercise);

        Intent intent = new Intent(this, EditWorkoutActivity.class);
        startActivity(intent);
    }
}