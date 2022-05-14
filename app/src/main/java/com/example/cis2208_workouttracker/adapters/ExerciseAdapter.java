package com.example.cis2208_workouttracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cis2208_workouttracker.R;
import com.example.cis2208_workouttracker.domainModels.Exercise;
import com.example.cis2208_workouttracker.domainModels.Workout;

import java.util.List;

public class ExerciseAdapter extends
        RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {

    private List<Exercise> exercises;

    public ExerciseAdapter(List<Exercise> exercises){ this.exercises = exercises; }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View exercisesView = inflater.inflate(R.layout.card_rep_exercise, parent, false);
        return new ExerciseAdapter.ViewHolder(exercisesView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);

        TextView nameView = holder.nameTextView;
        nameView.setText(exercise.name);
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public Button deleteBtn;
        public Button editBtn;

        public ViewHolder(final View workoutsView) {
            super(workoutsView);
            nameTextView =  workoutsView.findViewById(R.id.rep_ex_name);
            deleteBtn = workoutsView.findViewById(R.id.rep_ex_delete_btn);
            editBtn = workoutsView.findViewById(R.id.rep_ex_edit_btn);
        }

        public void onDeleteClick(long id){

        }
    }
}
