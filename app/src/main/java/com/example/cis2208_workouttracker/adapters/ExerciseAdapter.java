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
import com.example.cis2208_workouttracker.domainModels.Exercise;
import com.example.cis2208_workouttracker.domainModels.RepExercise;
import com.example.cis2208_workouttracker.domainModels.TimedExercise;
import com.example.cis2208_workouttracker.domainModels.Workout;
import com.example.cis2208_workouttracker.ui.workouts.EditRepExerciseActivity;
import com.example.cis2208_workouttracker.ui.workouts.EditTimedExerciseActivity;

import java.util.List;

public class ExerciseAdapter extends
        RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {

    private List<Exercise> exercises;
    private final Context _context;
    private final DbHelper _dbHelper;
    private final ExerciseUtility _exerciseUtility;

    public ExerciseAdapter(List<Exercise> exercises, Context context){
        this.exercises = exercises;
        this._context = context;
        _dbHelper = new DbHelper(_context);
        _exerciseUtility = new ExerciseUtility(_dbHelper);
    }

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

        TextView setsValue = holder.setsValueTextView;
        setsValue.setText(String.valueOf(exercise.getNoOfSets()));

        TextView weightsValue = holder.weightValueTextView;
        String weight = Double.toString(exercise.getWeight());
        weightsValue.setText(weight);

        if(exercise instanceof TimedExercise){
            TextView label = holder.repsOrTimeLabelTextView;
            label.setText(R.string.time_label);

            TextView timeValue = holder.repsOrTimeValueTextView;
            String time = String.valueOf(((TimedExercise) exercise).getTime());
            timeValue.setText(time);
        }else{
            TextView repsValue = holder.repsOrTimeValueTextView;
            String reps = String.valueOf(((RepExercise)exercise).getNoOfReps());
            repsValue.setText(reps);
        }
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView setsValueTextView;
        public TextView repsOrTimeLabelTextView;
        public TextView repsOrTimeValueTextView;
        public TextView weightValueTextView;
        public Button deleteBtn;
        public Button editBtn;

        public ViewHolder(final View exercisesView) {
            super(exercisesView);
            nameTextView =  exercisesView.findViewById(R.id.rep_ex_name);
            setsValueTextView = exercisesView.findViewById(R.id.sets);
            repsOrTimeLabelTextView = exercisesView.findViewById(R.id.reps_or_time_label);
            repsOrTimeValueTextView = exercisesView.findViewById(R.id.reps_or_time);
            weightValueTextView = exercisesView.findViewById(R.id.weight);
            //Buttons
            deleteBtn = exercisesView.findViewById(R.id.rep_ex_delete_btn);
            editBtn = exercisesView.findViewById(R.id.rep_ex_edit_btn);
            deleteBtn.setOnClickListener(view -> onDeleteClick(exercisesView));
            editBtn.setOnClickListener(view -> onEditClick(exercisesView));
        }

        public void onDeleteClick(View exerciseView){
            int pos = getAdapterPosition();
            Exercise exercise = exercises.get(pos);
            long id = exercise.getId();
            //Note that exercise can be of two types
            //This will change which table it will be removed from
            if(exercise instanceof RepExercise){
                _exerciseUtility.removeRepExerciseById(id);
            }else if(exercise instanceof TimedExercise){
                _exerciseUtility.removeTimedExerciseById(id);
            }
            //Remove the workout from the adapter
            exercises.remove(exercise);
            notifyDataSetChanged();
        }

        public void onEditClick(View exerciseView){
            int pos = getAdapterPosition();
            Exercise exercise = exercises.get(pos);
            long id = exercise.getId();
            //Decide which activity to start
            if(exercise instanceof RepExercise){
                Intent intent = new Intent(_context, EditRepExerciseActivity.class);
                intent.putExtra("exerciseId", exercise.getId());
                _context.startActivity(intent);
            }else if(exercise instanceof TimedExercise){
                Intent intent = new Intent(_context, EditTimedExerciseActivity.class);
                intent.putExtra("exerciseId", exercise.getId());
                _context.startActivity(intent);
            }
        }
    }
}
