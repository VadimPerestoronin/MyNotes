package ru.geekbrains.mynotes.ui.list;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.mynotes.R;
import ru.geekbrains.mynotes.domain.Note;
import ru.geekbrains.mynotes.domain.NoteRepository;
import ru.geekbrains.mynotes.domain.NoteRepositoryImpl;
import ru.geekbrains.mynotes.ui.MainActivity;

import android.view.ContextMenu;

public class NoteListFragment extends Fragment {

    public static final String TAG = "NoteListFragment";

    public static NoteListFragment newInstance(){
        return new NoteListFragment();
    }

    public interface OnNoteClicked {
        void onNoteClicked(Note note);

    }

    private NotesListAdapter notesListAdapter;
    private NoteRepository noteRepository = NoteRepositoryImpl.INSTANCE;

    private OnNoteClicked onNoteClicked;

    private int longClickedIndex;
    private Note longClickedNote;

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

        notesListAdapter = new NotesListAdapter(this);

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


        Toolbar toolbar = view.findViewById(R.id.toolbar);

        RecyclerView notesList = view.findViewById(R.id.note_list_container);

        notesList.setLayoutManager(new LinearLayoutManager(requireContext()));

        List<Note> notes = noteRepository.getNotes();

        NotesListAdapter notesListAdapter = new NotesListAdapter(this);
        notesListAdapter.setData(notes);

        notesListAdapter.setListener(new NotesListAdapter.OnNoteClickedListener() {
            @Override
            public void onNoteClickedListener(@NonNull Note note) {

                if (onNoteClicked != null){
                    onNoteClicked.onNoteClicked(note);
                }

            }
        });

        notesListAdapter.setLongClickedListener(new NotesListAdapter.OnLongNoteClickedListener() {
            @Override
            public void onLongNoteClickedListener(@NonNull Note note, int index) {
                longClickedIndex = index;
                longClickedNote = note;

            }
        });

        notesList.setAdapter(notesListAdapter);



        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.add_note){

                    Note addedNote = noteRepository.add(R.string.date_default, R.string.title_default, R.string.description_default);

                    int index = notesListAdapter.add(addedNote);

                    notesListAdapter.notifyItemInserted(index);

                    notesList.scrollToPosition(index);


                    return true;
                }
                return false;
            }
        });


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

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_notes_context, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.edit_note){
            return true;
        }

        if(item.getItemId() == R.id.delete_note){

            noteRepository.remove(longClickedNote);
            notesListAdapter.remove(longClickedNote);
            notesListAdapter.notifyItemRemoved(longClickedIndex);

        }

        return super.onContextItemSelected(item);
    }

}
