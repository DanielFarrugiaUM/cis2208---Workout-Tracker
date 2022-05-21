package com.example.cis2208_workouttracker.ui.workouts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
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
    private TextInputEditText nameInput;
    private TextInputEditText setsInput;
    private TextInputEditText minutesInput;
    private TextInputEditText secondsInput;
    TextInputEditText weightInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_timed_exercise);
        getSupportActionBar().setTitle(R.string.add_exercise_title);
        Button confirmBtn = findViewById(R.id.confirm_btn);
        confirmBtn.setOnClickListener(this::onClickConfirm);
    }

    private void onClickConfirm(View view) {
        Intent intent = getIntent();
        long workoutId = intent.getLongExtra("workoutId", -1);
        //Get Name
        nameInput = findViewById(R.id.timed_name_input);
        String name = nameInput.getEditableText().toString();
        //Get sets
        setsInput = findViewById(R.id.timed_sets_input);
        String setsValue = setsInput.getEditableText().toString();

        //Get time in two parts
        //Minutes
        minutesInput = findViewById(R.id.minutes_input);
        String minutesValue = minutesInput.getEditableText().toString();

        //Seconds
        secondsInput = findViewById(R.id.seconds_input);
        String secondsValue = secondsInput.getEditableText().toString();

        //Get Weight
        weightInput = findViewById(R.id.timed_weight_input);
        String weightValue = weightInput.getEditableText().toString();

        if(verifyInput(setsValue, minutesValue, secondsValue)){
            //Since it is either seconds or minutes, any one of them can be an empty
            //string after verification, therefore we check for empty strings again
            int sec = 0;
            int minutes = 0;
            if(secondsValue.length() != 0){
                sec = Integer.parseInt(secondsValue);
            }
            if(minutesValue.length() != 0){
                minutes = Integer.parseInt(minutesValue);
            }
            int sets = Integer.parseInt(setsValue);
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

    private boolean verifyInput(String setsVal, String minutesVal, String secondsVal){
        //Get the values from strings.xml
        Resources res = getApplicationContext().getResources();
        String requiredErr = res.getString(R.string.required);
        String notZeroErr = res.getString(R.string.not_0);
        String weightErr = res.getString(R.string.required_weight);
        String timeErr = res.getString(R.string.time_err);
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
        //No input bill break our Parse line with null
        if(minutesInput.length() == 0 && secondsInput.length() == 0){
            secondsInput.setError(timeErr);
            return false;
        }
        //Check if any contains data, if not take it as a 0
        //We need to check the total
        int sec = 0;
        if(secondsInput.length() != 0){
            sec = Integer.parseInt(secondsVal);
        }
        int minutes = 0;
        if(minutesInput.length() != 0){
            minutes = Integer.parseInt(minutesVal);
        }
        //Check that time is not 0
        if(minutes + sec == 0){
            secondsInput.setError(timeErr);
            return false;
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