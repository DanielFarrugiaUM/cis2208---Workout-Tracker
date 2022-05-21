package com.example.cis2208_workouttracker.ui.workouts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cis2208_workouttracker.R;
import com.example.cis2208_workouttracker.backend.DbHelper;
import com.example.cis2208_workouttracker.backend.ExerciseUtility;
import com.example.cis2208_workouttracker.domainModels.RepExercise;
import com.google.android.material.textfield.TextInputEditText;

public class AddRepExerciseActivity extends AppCompatActivity {
    private TextInputEditText nameInput;
    private TextInputEditText setsInput;
    private TextInputEditText repsInput;
    private TextInputEditText weightInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_rep_exercise);

        Button confirmBtn = findViewById(R.id.confirm_btn);
        confirmBtn.setOnClickListener(this::onClickConfirm);
    }

    private void onClickConfirm(View view) {
        Intent intent = getIntent();
        long workoutId = intent.getLongExtra("workoutId", -1);

        nameInput = findViewById(R.id.rep_name_input);
        String name = nameInput.getEditableText().toString();

        setsInput = findViewById(R.id.rep_sets_input);
        String setsValue = setsInput.getEditableText().toString();


        repsInput = findViewById(R.id.rep_reps_input);
        String repsValue = repsInput.getEditableText().toString();


        weightInput = findViewById(R.id.rep_weight_input);
        String weightValue = weightInput.getEditableText().toString();


        //Check the user inputs and if correct
        //save to db and go back to previous screen
        if(verifyInput(setsValue, repsValue)){
            //Empty string error not possible here
            int sets = Integer.parseInt(setsValue);
            double weight = Double.parseDouble(weightValue);
            int reps = Integer.parseInt(repsValue);
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

    private boolean verifyInput(String setsVal, String repsVal){
        //Get the values from strings.xml
        Resources res = getApplicationContext().getResources();
        String requiredErr = res.getString(R.string.required);
        String notZeroErr = res.getString(R.string.not_0);
        String weightErr = res.getString(R.string.required_weight);
        //Check name
        if(nameInput.length() == 0){
            nameInput.setError(requiredErr);
            return false;
        }
        //Impossible to have 0 sets or empty field
        if(setsInput.length() == 0){
            setsInput.setError(requiredErr);
            return false;
        }else{
            int sets = Integer.parseInt(setsVal);
            if(sets == 0){
                setsInput.setError(notZeroErr);
                return false;
            }
        }
        //Impossible to have 0 reps or empty field
        if(repsInput.length() == 0){
            repsInput.setError(requiredErr);
            return false;
        }else{
            int reps = Integer.parseInt(repsVal);
            if(reps == 0){
                repsInput.setError(notZeroErr);
                return false;
            }
        }
        //Weight can be 0 in case of body weight training
        //but not empty
        if(weightInput.length() == 0){
            weightInput.setError(weightErr);
            return false;
        }
        return true;
    }
}