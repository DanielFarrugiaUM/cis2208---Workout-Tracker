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
import com.example.cis2208_workouttracker.domainModels.Workout;
import com.example.cis2208_workouttracker.ui.workouts.EditWorkoutActivity;

import java.util.List;

public class WorkoutsAdapter extends
        RecyclerView.Adapter<WorkoutsAdapter.ViewHolder> {

    private List<Workout> workouts;

    public WorkoutsAdapter(List<Workout> workouts){
        this.workouts = workouts;
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

            editBtn.setOnClickListener(view -> onEditClick(workoutsView));
        }

        public void onDeleteClick(long id){

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
