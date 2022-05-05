package com.example.cis2208_workouttracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cis2208_workouttracker.R;
import com.example.cis2208_workouttracker.domainModels.Workout;

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

        public ViewHolder(final View workoutsView) {
            super(workoutsView);
            nameTextView =  workoutsView.findViewById(R.id.workout_name);

        }
    }
}
