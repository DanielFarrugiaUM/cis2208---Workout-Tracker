package com.example.cis2208_workouttracker.ui.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.cis2208_workouttracker.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class AddExerciseDialog extends DialogFragment {

    private int mSelectedItem;

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState){
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());

        builder.setTitle(R.string.dialog_ex_title);
                //.setMessage(R.string.dialog_ex_msg);

        builder.setSingleChoiceItems(R.array.exercise_types, -1,
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mSelectedItem = i;
                    }
                });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.i("Choice made:", String.valueOf(mSelectedItem));
            }
        })
        .setPositiveButton(R.string.btn_confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        return builder.create();
    }

}
