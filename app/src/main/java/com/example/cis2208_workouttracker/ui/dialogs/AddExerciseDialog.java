package com.example.cis2208_workouttracker.ui.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.cis2208_workouttracker.R;
import com.example.cis2208_workouttracker.ui.workouts.AddRepExerciseActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class AddExerciseDialog extends DialogFragment {

    private int mSelectedItem;
    private Button positiveBtn;

    //The following was needed to enable/disable confirm btn
    //From:
    // https://stackoverflow.com/questions/15912124/android-disable-dialogfragment-ok-cancel-buttons
    public void onStart(){
        super.onStart();
        AlertDialog dialog = (AlertDialog) getDialog();
        if(dialog != null){
            positiveBtn = dialog.getButton(Dialog.BUTTON_POSITIVE);
            positiveBtn.setEnabled(false); //Button should be enabled on choice made
        }
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState){
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());

        builder.setTitle(R.string.dialog_ex_title);
                //.setMessage(R.string.dialog_ex_msg); //Cannot use message with singleChoice

        builder.setSingleChoiceItems(R.array.exercise_types, -1,
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        positiveBtn.setEnabled(true); //Now the user can confirm
                        mSelectedItem = i;
                    }
                });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        })
        .setPositiveButton(R.string.btn_confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent;
                switch (mSelectedItem){
                    case 0:
                        intent = new Intent(getActivity(), AddRepExerciseActivity.class);
                        break;
                    case 1:
                        intent = new Intent(getActivity(), AddRepExerciseActivity.class);
                        break;
                    default://Auto generated, case no selection is made
                        throw new IllegalStateException("Unexpected value: " + mSelectedItem);
                }
                startActivity(intent);
            }
        });

        return builder.create();
    }

}
