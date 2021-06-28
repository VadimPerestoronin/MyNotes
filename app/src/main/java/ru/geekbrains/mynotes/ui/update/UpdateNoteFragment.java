package ru.geekbrains.mynotes.ui.update;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.geekbrains.mynotes.R;
import ru.geekbrains.mynotes.domain.Note;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class UpdateNoteFragment extends Fragment {

    public static final String TAG = "UpdateNoteFragment";

    private static final String ARG_NOTE = "ARG_NOTE";

    public static UpdateNoteFragment newInstance (Note note){
        UpdateNoteFragment fragment = new UpdateNoteFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_NOTE, note);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_update_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Note note = getArguments().getParcelable(ARG_NOTE);

        EditText title = view.findViewById(R.id.edit_title);
        title.setText(note.getTitle());

        EditText description = view.findViewById(R.id.edit_description);
        description.setText(note.getDescription());

        DatePicker datePicker = view.findViewById(R.id.picker);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(note.getDate());

        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            }
        });
    }
}
