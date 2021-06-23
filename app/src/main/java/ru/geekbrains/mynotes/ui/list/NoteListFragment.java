package ru.geekbrains.mynotes.ui.list;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import ru.geekbrains.mynotes.R;
import ru.geekbrains.mynotes.domain.Note;
import ru.geekbrains.mynotes.domain.NoteRepository;
import ru.geekbrains.mynotes.domain.NoteRepositoryImpl;
import ru.geekbrains.mynotes.ui.MainActivity;

public class NoteListFragment extends Fragment {

    public static final String TAG = "NoteListFragment";

    public static NoteListFragment newInstance(){
        return new NoteListFragment();
    }

    public interface OnNoteClicked {
        void onNoteClicked(Note note);

    }

    private NoteRepository noteRepository = NoteRepositoryImpl.INSTANCE;

    private OnNoteClicked onNoteClicked;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof OnNoteClicked){
            onNoteClicked = (OnNoteClicked) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onNoteClicked = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        noteRepository = new NoteRepositoryImpl();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        RecyclerView notesList = view.findViewById(R.id.note_list_container);

        notesList.setLayoutManager(new LinearLayoutManager(requireContext()));

        List<Note> notes = noteRepository.getNotes();

        NotesListAdapter notesListAdapter = new NotesListAdapter();
        notesListAdapter.setData(notes);

        notesListAdapter.setListener(new NotesListAdapter.OnNoteClickedListener() {
            @Override
            public void onNoteClickedListener(@NonNull Note note) {

                if (onNoteClicked != null){
                    onNoteClicked.onNoteClicked(note);
                }

            }
        });

        notesList.setAdapter(notesListAdapter);


    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
