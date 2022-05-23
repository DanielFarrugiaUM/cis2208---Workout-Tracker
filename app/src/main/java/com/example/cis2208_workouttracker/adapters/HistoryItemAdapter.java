package com.example.cis2208_workouttracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cis2208_workouttracker.R;
import com.example.cis2208_workouttracker.domainModels.Exercise;
import com.example.cis2208_workouttracker.domainModels.HistoryItem;
import com.example.cis2208_workouttracker.domainModels.RepExercise;
import com.example.cis2208_workouttracker.domainModels.TimedExercise;
import com.example.cis2208_workouttracker.ui.workouts.HistoryItemsViewModel;
import com.example.cis2208_workouttracker.ui.workouts.WorkoutsViewModel;

import java.util.List;

public class HistoryItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<HistoryItem> items;

    //In this case I am using two Views for rep and timed
    public HistoryItemAdapter(List<HistoryItem> items){
        this.items = items;
    }
    @Override
    public int getItemViewType(int pos){
        HistoryItem item = items.get(pos);
        if(item.exercise instanceof RepExercise){
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

        View itemsView;
        RecyclerView.ViewHolder viewHolder = null;
        //Need to decide what view inflate depending on exercise type
        switch (viewType){
            case 0:
                itemsView = inflater.inflate(
                        R.layout.rep_history_item, parent, false
                );
                viewHolder = new RepViewHolder(itemsView);
                break;
            case 1:
                itemsView = inflater.inflate(
                        R.layout.timed_history_item, parent, false
                );
                viewHolder = new TimedViewHolder(itemsView);
                break;

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HistoryItem item = items.get(position);

        if (item.exercise instanceof RepExercise) {
            HistoryItemAdapter.RepViewHolder viewHolder = (HistoryItemAdapter.RepViewHolder) holder;

            viewHolder.nameTextView.setText(item.getName());

            String setsString = String.valueOf(item.getNoOfSets());
            viewHolder.setsValueTextView.setText(setsString);

            String repsString = String.valueOf(((RepExercise) item.exercise).getNoOfReps());
            viewHolder.repsValueTextView.setText(repsString);

            String weightString = Double.toString(item.getWeight());
            viewHolder.weightValueTextView.setText(weightString);
        } else {
            HistoryItemAdapter.TimedViewHolder viewHolder = (HistoryItemAdapter.TimedViewHolder) holder;

            viewHolder.nameTextView.setText(item.getName());

            String setsString = String.valueOf(item.getNoOfSets());
            viewHolder.setsValueTextView.setText(setsString);

            String repsString = String.valueOf(((TimedExercise) item.exercise).getTime());
            viewHolder.timeValueTextView.setText(repsString);

            String weightString = Double.toString(item.getWeight());
            viewHolder.weightValueTextView.setText(weightString);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class RepViewHolder extends RecyclerView.ViewHolder{

        public TextView nameTextView;
        public TextView setsValueTextView;
        public TextView repsValueTextView;
        public TextView weightValueTextView;

        public RepViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.perf_name);
            setsValueTextView = itemView.findViewById(R.id.sets);
            repsValueTextView = itemView.findViewById(R.id.time);
            weightValueTextView = itemView.findViewById(R.id.weight);
        }
    }

    public class  TimedViewHolder extends RecyclerView.ViewHolder{

        public TextView nameTextView;
        public TextView setsValueTextView;
        public TextView timeValueTextView;
        public TextView weightValueTextView;

        public TimedViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.perf_name);
            setsValueTextView = itemView.findViewById(R.id.sets);
            timeValueTextView = itemView.findViewById(R.id.time);
            weightValueTextView = itemView.findViewById(R.id.weight);
        }
    }

}
