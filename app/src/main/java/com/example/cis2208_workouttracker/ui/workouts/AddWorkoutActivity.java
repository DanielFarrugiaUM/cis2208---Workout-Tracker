package com.example.cis2208_workouttracker.ui.workouts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cis2208_workouttracker.R;
import com.example.cis2208_workouttracker.backend.DbHelper;
import com.example.cis2208_workouttracker.backend.WorkoutsUtility;
import com.example.cis2208_workouttracker.databinding.ActivityAddWorkoutBinding;
import com.example.cis2208_workouttracker.domainModels.Workout;

public class AddWorkoutActivity extends AppCompatActivity {
    private ActivityAddWorkoutBinding binding;

    private EditText workoutNameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout);
        getSupportActionBar().setTitle(R.string.add_workout);

        //Get the button from layout and set listener
        Button confirmBtn = findViewById(R.id.confirm_btn);
        confirmBtn.setOnClickListener(this::onClickConfirm);

        //Get the name view and disable confirm button
        workoutNameView = findViewById(R.id.workout_name);
        workoutNameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Set to true if the length of the input is not 0
                confirmBtn.setEnabled(!charSequence.toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public void onClickConfirm(View view){
        //Find and get the view with the user's input and convert it to string

        String workoutName = workoutNameView.getText().toString();
        Workout workout = new Workout(workoutName);
        //Get the tools to write to DB and save the new workout
        DbHelper dbHelper = new DbHelper(this);
        WorkoutsUtility workoutsUtility = new WorkoutsUtility(dbHelper);
        long wId = workoutsUtility.insertWorkout(workout);

        Toast toast = Toast.makeText(
                this,
                "Workout added, now add exercises",
                Toast.LENGTH_LONG);
        toast.show();
        //Go to edit workout activity with the id of added workout
        Intent intent = new Intent(this, EditWorkoutActivity.class);
        intent.putExtra("workoutId", wId);
        startActivity(intent);

    }
}