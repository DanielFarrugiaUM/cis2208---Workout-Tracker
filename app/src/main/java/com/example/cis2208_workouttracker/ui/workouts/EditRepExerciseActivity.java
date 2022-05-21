package com.example.cis2208_workouttracker.ui.workouts;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.cis2208_workouttracker.R;
import com.example.cis2208_workouttracker.backend.DbHelper;
import com.example.cis2208_workouttracker.backend.ExerciseUtility;
import com.example.cis2208_workouttracker.domainModels.RepExercise;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

public class EditRepExerciseActivity extends AppCompatActivity {

    long exerciseId;
    long workoutId;
    DbHelper _dbHelper;
    ExerciseUtility _exerciseUtil;
    private TextInputEditText nameInput;
    private TextInputEditText setsInput;
    private TextInputEditText repsInput;
    private TextInputEditText weightInput;

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
        nameInput = findViewById(R.id.rep_name_input);
        String name = nameInput.getEditableText().toString();

        setsInput = findViewById(R.id.rep_sets_input);
        String setsValue = setsInput.getEditableText().toString();


        repsInput = findViewById(R.id.rep_reps_input);
        String repsValue = repsInput.getEditableText().toString();


        weightInput = findViewById(R.id.rep_weight_input);
        String weightValue = weightInput.getEditableText().toString();


        if(verifyInput(setsValue, repsValue)){
            int sets = Integer.parseInt(setsValue);
            double weight = Double.parseDouble(weightValue);
            int reps = Integer.parseInt(repsValue);

            RepExercise exercise = new RepExercise(exerciseId, name, sets, weight, reps, workoutId);
            _exerciseUtil.updateRepExercise(exercise);

            Intent intent = new Intent(this, EditWorkoutActivity.class);
            intent.putExtra("workoutId", workoutId);
            startActivity(intent);
        }

    }

/*    @Override
    public void finish(){
        Intent intent = new Intent(this, EditWorkoutActivity.class);
        intent.putExtra("workoutId", workoutId);
        super.finish();
    }*/

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

    @Override
    public void onBackPressed() {
        finish();
    }
}