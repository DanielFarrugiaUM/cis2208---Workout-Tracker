package com.example.cis2208_workouttracker.ui.history;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.cis2208_workouttracker.R;
import com.example.cis2208_workouttracker.adapters.HistoryItemAdapter;
import com.example.cis2208_workouttracker.adapters.WorkoutsAdapter;
import com.example.cis2208_workouttracker.databinding.FragmentHistoryBinding;
import com.example.cis2208_workouttracker.databinding.FragmentWorkoutsBinding;
import com.example.cis2208_workouttracker.domainModels.HistoryItem;
import com.example.cis2208_workouttracker.domainModels.Workout;
import com.example.cis2208_workouttracker.ui.workouts.HistoryItemsViewModel;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private @NonNull FragmentHistoryBinding binding;
    private HistoryItemsViewModel itemsViewModel;
    private HistoryItemAdapter adapter;
    private RecyclerView itemsView;
    private List<HistoryItem> items = new ArrayList<>();

    private Button pickDateBtn;
    private TextInputEditText dateText;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        itemsViewModel = new ViewModelProvider(this).get(HistoryItemsViewModel.class);
        View root = binding.getRoot();
        itemsView = root.findViewById(R.id.items_list);

        pickDateBtn = root.findViewById(R.id.pick_date_btn);
        dateText = root.findViewById(R.id.date);

        setUpRecyclerView();
        fetchItems();
        //Set up date picker
        MaterialDatePicker.Builder<Long> materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("SELECT A DATE");
        final MaterialDatePicker<Long> datePicker = materialDateBuilder.build();

        pickDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.show(getParentFragmentManager(), "DATE_PICKER");
            }
        });

        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                dateText.setText(datePicker.getHeaderText());
                Date date = new Date(selection);
                System.out.println(date.toString());
            }
        });
        return root;
    }

    private void fetchItems() {
        itemsViewModel.getItems(getActivity()).observe(getViewLifecycleOwner(),
                this::updateItemsList);
    }

    private void updateItemsList(List<HistoryItem> historyItems) {
        items.addAll(historyItems);
        adapter.notifyDataSetChanged();
    }


    private void setUpRecyclerView() {
        adapter = new HistoryItemAdapter(items);
        itemsView.setAdapter(adapter);
        itemsView.setLayoutManager(
                new LinearLayoutManager(itemsView.getContext())
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}