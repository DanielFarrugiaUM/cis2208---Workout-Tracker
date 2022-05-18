package com.example.cis2208_workouttracker.ui.workouts;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cis2208_workouttracker.R;
import com.example.cis2208_workouttracker.adapters.WorkoutsAdapter;
import com.example.cis2208_workouttracker.databinding.FragmentWorkoutsBinding;
import com.example.cis2208_workouttracker.domainModels.Workout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkoutsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private WorkoutsViewModel workoutsViewModel;
    private WorkoutsAdapter workoutsAdapter;
    private @NonNull FragmentWorkoutsBinding binding;
    private RecyclerView workoutsView;
    private List<Workout> workouts = new ArrayList<>();

    public WorkoutsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WorkoutsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkoutsFragment newInstance() {
        //Auto generated code not required
/*      Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return new WorkoutsFragment();
    }

    //This was auto generated
/*    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        workoutsViewModel = new ViewModelProvider(this).get(WorkoutsViewModel.class);
        binding = FragmentWorkoutsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        workoutsView = root.findViewById(R.id.workouts_list);
        //Get the button from the XML and assign the action
        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(this::onClickAdd);
        setUpRecyclerView();
        fetchWorkouts();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void fetchWorkouts() {
        workoutsViewModel.getWorkouts(getActivity()).observe(getViewLifecycleOwner(),
                this::updateWorkoutsList);
    }

    private void updateWorkoutsList(List<Workout> newWorkouts){
        workouts.addAll(newWorkouts);
        workoutsAdapter.notifyDataSetChanged();
    }

    private void setUpRecyclerView() {
        workoutsAdapter = new WorkoutsAdapter(workouts, this.getActivity());
        workoutsView.setAdapter(workoutsAdapter);
        workoutsView.setLayoutManager(
                new LinearLayoutManager(workoutsView.getContext())
        );
    }

    public void onClickAdd(View v){
        Intent intent = new Intent(getActivity(), AddWorkoutActivity.class);
        startActivity(intent);
    }
}