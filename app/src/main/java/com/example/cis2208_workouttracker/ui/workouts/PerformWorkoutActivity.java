package com.example.cis2208_workouttracker.ui.workouts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

import com.example.cis2208_workouttracker.MainActivity;
import com.example.cis2208_workouttracker.R;
import com.example.cis2208_workouttracker.adapters.PerformanceAdapter;
import com.example.cis2208_workouttracker.backend.DbHelper;
import com.example.cis2208_workouttracker.backend.HistoryUtility;
import com.example.cis2208_workouttracker.domainModels.Exercise;
import com.example.cis2208_workouttracker.domainModels.HistoryItem;
import com.example.cis2208_workouttracker.domainModels.RepExercise;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class PerformWorkoutActivity extends AppCompatActivity {

    private ExerciseViewModel exerciseViewModel;
    private PerformanceAdapter adapter;
    private RecyclerView exercisesView;
    private List<Exercise> exercises = new ArrayList<>();
    private List<Exercise> selected = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String wName = intent.getStringExtra("workoutName");
        getSupportActionBar().setTitle(wName);
        setContentView(R.layout.activity_perform_workout);
        exerciseViewModel = new ViewModelProvider(this).get(ExerciseViewModel.class);
        exercisesView = this.findViewById(R.id.perform_exercise_list);

        ExtendedFloatingActionButton finishedBtn = findViewById(R.id.finished_btn);
        finishedBtn.setOnClickListener(this::onClickFinished);
        setUpRecyclerView();
        populateScreen();
    }

    private void populateScreen() {
        //Get the id from previous activity
        Intent intent = getIntent();
        long id = intent.getLongExtra("workoutId", -1);
        exerciseViewModel.getExercises(this, id).observe(
                this,
                this::updateExercisesList
        );
    }

    private void updateExercisesList(List<Exercise> newExercises) {
        exercises.addAll(newExercises);
        adapter.notifyDataSetChanged();
    }

    private void setUpRecyclerView() {
        adapter = new PerformanceAdapter(exercises, selected);
        exercisesView.setAdapter(adapter);
        exercisesView.setLayoutManager(
                new LinearLayoutManager(exercisesView.getContext())
        );
    }

    private void onClickFinished(View view) {
        DbHelper dbHelper = new DbHelper(this);
        HistoryUtility histUtil = new HistoryUtility(dbHelper);

        //Write all the ticked exercise to DB
        for(Exercise exercise: selected){
            HistoryItem tempItem = new HistoryItem(exercise);
            //Call Db methods
            if(tempItem.exercise instanceof RepExercise){
                histUtil.insertRepHistoryItem(tempItem);
            }else{
                histUtil.insertTimedHistoryItem(tempItem);
            }
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}