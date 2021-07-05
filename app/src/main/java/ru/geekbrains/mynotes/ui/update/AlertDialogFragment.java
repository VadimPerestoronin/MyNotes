package ru.geekbrains.mynotes.ui.update;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.jetbrains.annotations.NotNull;

import ru.geekbrains.mynotes.R;
import ru.geekbrains.mynotes.ui.MainActivity;

public class AlertDialogFragment extends DialogFragment {

    public static final String TAG = "AlertDialogFragment";
    public static final String RESULT = "AlertDialogFragment";


    public static AlertDialogFragment newInstance(){
        return new AlertDialogFragment();
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                .setTitle(R.string.delete_dialog_title)
                .setMessage(R.string.delete_dialog_message)
                .setIcon(R.drawable.ic_delete_black)
                .setCancelable(false)
                .setPositiveButton(R.string.btn_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Bundle resultDelete = new Bundle();
                        resultDelete.putString("bundle_key", "delete");

                        getChildFragmentManager().setFragmentResult(RESULT, resultDelete);


                    }
                })
                .setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });

        return builder.create();
    }
}
