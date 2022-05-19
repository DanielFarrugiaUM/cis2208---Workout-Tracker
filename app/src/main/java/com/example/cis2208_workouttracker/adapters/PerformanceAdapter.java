package com.example.cis2208_workouttracker.adapters;

import com.example.cis2208_workouttracker.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cis2208_workouttracker.domainModels.Exercise;
import com.example.cis2208_workouttracker.domainModels.RepExercise;
import com.example.cis2208_workouttracker.domainModels.TimedExercise;

import java.util.List;

public class PerformanceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Exercise> exercises;
    private List<Exercise> selected;

    public PerformanceAdapter(List<Exercise> exercises, List<Exercise> selected){
        this.exercises = exercises;
        this.selected = selected;
    }
    @Override
    public int getItemViewType(int pos){
        Exercise exercise = exercises.get(pos);
        if(exercise instanceof RepExercise){
            return 0;
        }else {
            return 1;
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View exerciseView;
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType){
            case 0:
                exerciseView = inflater.inflate(
                    R.layout.card_perform_rep, parent, false
                );
                viewHolder = new RepViewHolder(exerciseView);
                break;
            case 1:
                exerciseView = inflater.inflate(
                        R.layout.car_perform_timed, parent, false
                );
                viewHolder = new TimedViewHolder(exerciseView);
                break;

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);

        if(exercise instanceof RepExercise){
            RepViewHolder viewHolder = (RepViewHolder) holder;

            viewHolder.nameTextView.setText(exercise.getName());

            String setsString = String.valueOf(exercise.getNoOfSets());
            viewHolder.setsValueTextView.setText(setsString);

            String repsString = String.valueOf(((RepExercise)exercise).getNoOfReps());
            viewHolder.repsValueTextView.setText(repsString);

            String weightString = Double.toString(exercise.getWeight());
            viewHolder.weightValueTextView.setText(weightString);
        }else{
            TimedViewHolder viewHolder = (TimedViewHolder) holder;

            viewHolder.nameTextView.setText(exercise.getName());

            String setsString = String.valueOf(exercise.getNoOfSets());
            viewHolder.setsValueTextView.setText(setsString);

            String repsString = String.valueOf(((TimedExercise)exercise).getTime());
            viewHolder.timeValueTextView.setText(repsString);

            String weightString = Double.toString(exercise.getWeight());
            viewHolder.weightValueTextView.setText(weightString);
        }
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public class RepViewHolder extends RecyclerView.ViewHolder{

        public TextView nameTextView;
        public TextView setsValueTextView;
        public TextView repsValueTextView;
        public TextView weightValueTextView;
        public CheckBox checkBox;

        public RepViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.rep_ex_name);
            setsValueTextView = itemView.findViewById(R.id.sets);
            repsValueTextView = itemView.findViewById(R.id.reps);
            weightValueTextView = itemView.findViewById(R.id.weight);

            checkBox = itemView.findViewById(R.id.checkbox);
            checkBox.setOnClickListener(view -> onCheckBoxTicked(itemView));

        }

        public void onCheckBoxTicked(View view){
            int pos = getAdapterPosition();
            Exercise exercise = exercises.get(pos);
            if(selected.contains(exercise)){
                selected.remove(exercise);
            }else {
                selected.add(exercise);
            }
            notifyDataSetChanged();
        }
    }

    public class  TimedViewHolder extends RecyclerView.ViewHolder{

        public TextView nameTextView;
        public TextView setsValueTextView;
        public TextView timeValueTextView;
        public TextView weightValueTextView;
        public CheckBox checkBox;

        public TimedViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.rep_ex_name);
            setsValueTextView = itemView.findViewById(R.id.sets);
            timeValueTextView = itemView.findViewById(R.id.time);
            weightValueTextView = itemView.findViewById(R.id.weight);

            checkBox = itemView.findViewById(R.id.checkbox);
            checkBox.setOnClickListener(view -> onCheckBoxTicked(itemView));
        }

        public void onCheckBoxTicked(View view){
            int pos = getAdapterPosition();
            Exercise exercise = exercises.get(pos);
            if(selected.contains(exercise)){
                selected.remove(exercise);
            }else {
                selected.add(exercise);
            }
            notifyDataSetChanged();
        }
    }
}
