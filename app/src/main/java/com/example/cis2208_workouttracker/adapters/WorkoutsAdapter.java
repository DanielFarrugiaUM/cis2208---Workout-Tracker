package com.example.cis2208_workouttracker.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cis2208_workouttracker.R;
import com.example.cis2208_workouttracker.backend.DbHelper;
import com.example.cis2208_workouttracker.backend.ExerciseUtility;
import com.example.cis2208_workouttracker.backend.WorkoutsUtility;
import com.example.cis2208_workouttracker.domainModels.Workout;
import com.example.cis2208_workouttracker.ui.workouts.EditWorkoutActivity;
import com.example.cis2208_workouttracker.ui.workouts.PerformWorkoutActivity;

import java.util.List;

public class WorkoutsAdapter extends
        RecyclerView.Adapter<WorkoutsAdapter.ViewHolder> {

    private List<Workout> workouts;
    private final Context _context;
    private final DbHelper _dbHelper;
    private final WorkoutsUtility _workoutsUtility;

    public WorkoutsAdapter(List<Workout> workouts, Context context){
        this.workouts = workouts;
        _context = context;
        _dbHelper = new DbHelper(_context);
        _workoutsUtility = new WorkoutsUtility(_dbHelper);
    }

    @NonNull
    @Override
    public WorkoutsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View workoutsView = inflater.inflate(R.layout.card_workout, parent, false);
        return new ViewHolder(workoutsView);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutsAdapter.ViewHolder holder, int position) {
        Workout workout = workouts.get(position);

        TextView nameView = holder.nameTextView;
        nameView.setText(workout.name);
    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public Button deleteBtn;
        public Button editBtn;
        public Button startBtn;

        public ViewHolder(final View workoutsView) {
            super(workoutsView);

            nameTextView =  workoutsView.findViewById(R.id.workout_name);
            deleteBtn = workoutsView.findViewById(R.id.workout_delete_btn);
            editBtn = workoutsView.findViewById(R.id.workout_edit_btn);
            startBtn = workoutsView.findViewById(R.id.workout_start_btn);

            startBtn.setOnClickListener(view -> onStartClick(workoutsView));
            deleteBtn.setOnClickListener(view -> onDeleteClick(workoutsView));
            editBtn.setOnClickListener(view -> onEditClick(workoutsView));
        }

        public void onStartClick(View workoutsView){
            int pos = getAdapterPosition();
            Workout workout = workouts.get(pos);
            long workoutId = workout.getId();
            Intent intent = new Intent(workoutsView.getContext(), PerformWorkoutActivity.class);
            intent.putExtra("workoutId", workoutId);
            workoutsView.getContext().startActivity(intent);

        }
        public void onDeleteClick(View workoutsView){
            int pos = getAdapterPosition();
            Workout workout = workouts.get(pos);
            long workoutId = workout.getId();
            _workoutsUtility.removeWorkoutById(workoutId);
            //Remove from live data and signal recycler view
            workouts.remove(workout);
            notifyDataSetChanged();
        }

        public void onEditClick(View workoutsView){
            int pos = getAdapterPosition();
            Workout workout = workouts.get(pos);
            long workoutId = workout.getId();
            Intent intent = new Intent(workoutsView.getContext(), EditWorkoutActivity.class);
            intent.putExtra("workoutId", workoutId);
            workoutsView.getContext().startActivity(intent);
        }
    }
}
