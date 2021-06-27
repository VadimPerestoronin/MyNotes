package ru.geekbrains.mynotes.ui.details;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import ru.geekbrains.mynotes.R;
import ru.geekbrains.mynotes.domain.Note;


public class NoteDetailsFragment extends Fragment {

    public static final String TAG = "NoteDetailsFragment";

    private static final String ARG_NOTE = "ARG_NOTE";

    public static NoteDetailsFragment newInstance (Note note){
        NoteDetailsFragment fragment = new NoteDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_NOTE, note);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_note_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView noteDate = view.findViewById(R.id.note_date);
        TextView noteTitle = view.findViewById(R.id.note_title);
        TextView noteDescription = view.findViewById(R.id.note_description);

        if(getArguments() != null){
            Note note = getArguments().getParcelable(ARG_NOTE);

            noteDate.setText(note.getDate());
            noteTitle.setText(note.getTitle());
            noteDescription.setText(note.getDescription());
        }


    }
}