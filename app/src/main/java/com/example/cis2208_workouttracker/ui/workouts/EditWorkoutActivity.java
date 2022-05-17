package com.example.cis2208_workouttracker.ui.workouts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.cis2208_workouttracker.R;
import com.example.cis2208_workouttracker.adapters.ExerciseAdapter;
import com.example.cis2208_workouttracker.backend.DbHelper;
import com.example.cis2208_workouttracker.backend.WorkoutsUtility;
import com.example.cis2208_workouttracker.domainModels.Exercise;
import com.example.cis2208_workouttracker.domainModels.Workout;
import com.example.cis2208_workouttracker.ui.dialogs.AddExerciseDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class EditWorkoutActivity extends AppCompatActivity {

    private ExerciseViewModel exercisesViewModel;
    private ExerciseAdapter adapter;
    private RecyclerView exercisesView;
    private List<Exercise> exercises = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout);
        exercisesViewModel = new ViewModelProvider(this).get(ExerciseViewModel.class);
        exercisesView = this.findViewById(R.id.exercise_list);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this::onClickAdd);
        setUpRecyclerView();
        populateScreen();
        setWorkoutName();
    }

    private void populateScreen() {
        exercisesViewModel.getExercises().observe(
                this,
                this::updateExercisesList
        );
    }

    private void updateExercisesList(List<Exercise> newExercises){
        exercises.addAll(newExercises);
        adapter.notifyDataSetChanged();
    }

    private void setUpRecyclerView() {
        adapter = new ExerciseAdapter(exercises);
        exercisesView.setAdapter(adapter);
        exercisesView.setLayoutManager(
                new LinearLayoutManager(exercisesView.getContext())
        );
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

    private void onClickAdd(View view){
        Intent intent = getIntent();
        long workoutIid = intent.getLongExtra("workoutId", -1);
        DialogFragment dialog = new AddExerciseDialog(workoutIid);
        dialog.show(getSupportFragmentManager(), "AddExerciseDialog");
    }
}