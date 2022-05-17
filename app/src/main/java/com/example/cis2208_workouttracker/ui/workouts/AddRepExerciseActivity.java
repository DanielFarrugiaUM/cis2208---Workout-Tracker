package com.example.cis2208_workouttracker.ui.workouts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cis2208_workouttracker.R;
import com.example.cis2208_workouttracker.backend.DbHelper;
import com.example.cis2208_workouttracker.backend.ExerciseUtility;
import com.example.cis2208_workouttracker.backend.WorkoutsUtility;
import com.example.cis2208_workouttracker.domainModels.RepExercise;
import com.google.android.material.textfield.TextInputEditText;

public class AddRepExerciseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rep_exercise);

        Button confirmBtn = findViewById(R.id.confirm_btn);
        confirmBtn.setOnClickListener(this::onClickConfirm);
    }

    private void onClickConfirm(View view) {
        Intent intent = getIntent();
        long workoutId = intent.getLongExtra("workoutId", -1);

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

        RepExercise exercise = new RepExercise(name, sets, weight, reps, workoutId);
        DbHelper dbHelper = new DbHelper(this);
        ExerciseUtility exerciseUtility = new ExerciseUtility(dbHelper);
        long eId = exerciseUtility.insertRepExercise(exercise);
        //Go back to edit workout
        intent = new Intent(this, EditWorkoutActivity.class);
        intent.putExtra("workoutId", workoutId);
        startActivity(intent);
    }
}