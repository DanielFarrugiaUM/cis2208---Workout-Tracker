package com.example.cis2208_workouttracker.ui.workouts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cis2208_workouttracker.R;
import com.example.cis2208_workouttracker.adapters.ExerciseAdapter;
import com.example.cis2208_workouttracker.backend.DbHelper;
import com.example.cis2208_workouttracker.backend.WorkoutsUtility;
import com.example.cis2208_workouttracker.domainModels.Exercise;
import com.example.cis2208_workouttracker.domainModels.Workout;
import com.example.cis2208_workouttracker.ui.dialogs.AddExerciseDialog;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EditWorkoutActivity extends AppCompatActivity {

    private ExerciseViewModel exercisesViewModel;
    private ExerciseAdapter adapter;
    private RecyclerView exercisesView;
    private List<Exercise> exercises = new ArrayList<>();
    private Button updateNameBtn;
    private TextInputEditText workoutNameView;
    private long workoutId;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout);
        getSupportActionBar().setTitle(R.string.edit_workout_title);

        exercisesViewModel = new ViewModelProvider(this).get(ExerciseViewModel.class);
        exercisesView = this.findViewById(R.id.exercise_list);
        workoutNameView = findViewById(R.id.name_input);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this::onClickAdd);

         updateNameBtn = findViewById(R.id.updateNameBtn);
         updateNameBtn.setOnClickListener(this::onClickUpdateName);

         if(savedInstanceState != null){
             workoutId = savedInstanceState.getLong("workoutId");
         }else{
             intent = getIntent();
             workoutId = intent.getLongExtra("workoutId", -1);
         }
    }

    public void onStart(){
        super.onStart();

        setUpRecyclerView();
        populateScreen();
        setWorkoutName();
    }
    //I tried to keep state alive so that i can use back button
    //but was unable to do so
    //The debugger is enter this function, but still getting a null
    //saved instance on create!!!
    @Override
    public void onSaveInstanceState(Bundle savedInstance){
        super.onSaveInstanceState(savedInstance);
        savedInstance.putLong("workoutId", workoutId);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        workoutId = savedInstanceState.getLong("workoutId");
    }


    private void populateScreen() {
        //Get the id from previous activity
        exercisesViewModel.getExercises(this, workoutId).observe(
                this,
                this::updateExercisesList
        );
    }

    private void updateExercisesList(List<Exercise> newExercises){
        exercises.addAll(newExercises);
        adapter.notifyDataSetChanged();
    }

    private void setUpRecyclerView() {
        adapter = new ExerciseAdapter(exercises, this);
        exercisesView.setAdapter(adapter);
        exercisesView.setLayoutManager(
                new LinearLayoutManager(exercisesView.getContext())
        );
    }

    private void setWorkoutName(){
        //Get Workout from db
        DbHelper dbHelper = new DbHelper(this);
        WorkoutsUtility workoutsUtility = new WorkoutsUtility(dbHelper);
        Workout workout = workoutsUtility.getWorkoutById(workoutId);

        //Set The name to appear in text view
        //TextInputEditText workoutNameView = findViewById(R.id.name_input);
        String workoutName = workout.getName();
        workoutNameView.setText(workoutName);

        workoutNameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Set to true if the length of the input is not 0
                updateNameBtn.setEnabled(!charSequence.toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void onClickAdd(View view){
        DialogFragment dialog = new AddExerciseDialog(workoutId);
        dialog.show(getSupportFragmentManager(), "AddExerciseDialog");
    }

    private void onClickUpdateName(View view){
        String newName;
        newName = Objects.requireNonNull(workoutNameView.getText()).toString().trim();
        DbHelper dbHelper = new DbHelper(this);
        WorkoutsUtility _workoutsUtil = new WorkoutsUtility(dbHelper);
        _workoutsUtil.updateWorkoutById(workoutId, newName);
        updateNameBtn.setEnabled(false);
    }
}