package ru.geekbrains.mynotes.ui.list;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import ru.geekbrains.mynotes.R;
import ru.geekbrains.mynotes.domain.Callback;
import ru.geekbrains.mynotes.domain.Note;
import ru.geekbrains.mynotes.domain.NoteFirestoreRepository;
import ru.geekbrains.mynotes.domain.NoteRepository;
import ru.geekbrains.mynotes.domain.NoteRepositoryImpl;
import ru.geekbrains.mynotes.ui.MainRouter;
import ru.geekbrains.mynotes.ui.update.AlertDialogFragment;
import ru.geekbrains.mynotes.ui.update.UpdateNoteFragment;

import android.view.ContextMenu;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

public class NoteListFragment extends Fragment {

    public static final String TAG = "NoteListFragment";

    public static NoteListFragment newInstance(){
        return new NoteListFragment();
    }

    public interface OnNoteClicked {
        void onNoteClicked(Note note);

    }

    private NotesListAdapter notesListAdapter;
    private NoteRepository noteRepository = NoteFirestoreRepository.INSTANCE;

    private OnNoteClicked onNoteClicked;

    private int longClickedIndex;
    private Note longClickedNote;

    private boolean isLoading = false;

    private ProgressBar progressBar;

    private MainRouter router;

    private String resultDelete;

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

        router = new MainRouter(getParentFragmentManager());
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_list, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        getChildFragmentManager().setFragmentResultListener(AlertDialogFragment.RESULT, getViewLifecycleOwner(), new FragmentResultListener() {

            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Snackbar.make(getView(), requestKey, Snackbar.LENGTH_LONG).show();


                resultDelete = result.getString(AlertDialogFragment.RESULT);


            }
        });

        Toolbar toolbar = view.findViewById(R.id.toolbar);

        RecyclerView notesList = view.findViewById(R.id.note_list_container);

        progressBar = view.findViewById(R.id.progress);

        if(isLoading){
            progressBar.setVisibility(View.VISIBLE);
        }

        notesList.setLayoutManager(new LinearLayoutManager(requireContext()));

        notesList.setAdapter(notesListAdapter);

        isLoading = true;

        noteRepository.getNotes(new Callback<List<Note>>() {
            @Override
            public void onSuccess(List<Note> notes) {
                notesListAdapter.setData(notes);
                notesListAdapter.notifyDataSetChanged();

                isLoading = false;

                if(progressBar != null){
                    progressBar.setVisibility(View.GONE);
                }
            }
        });


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

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.add_note){

                    noteRepository.add("Новая заметка", "Текст новой заметки", new Callback<Note>() {
                        @Override
                        public void onSuccess(Note result) {
                            int index = notesListAdapter.add(result);

                            notesListAdapter.notifyItemInserted(index);

                            notesList.scrollToPosition(index);

                        }
                    });

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
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.notes_list_fragment, UpdateNoteFragment.newInstance(longClickedNote), UpdateNoteFragment.TAG)
                    .addToBackStack(UpdateNoteFragment.TAG)
                    .commit();

            return true;
        }

        if(item.getItemId() == R.id.delete_note){
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                    .setTitle(R.string.delete_dialog_title)
                    .setMessage(R.string.delete_dialog_message)
                    .setIcon(R.drawable.ic_delete_black)
                    .setCancelable(false)
                    .setPositiveButton(R.string.btn_positive, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            noteRepository.remove(longClickedNote, new Callback<Object>() {

                                @Override
                                public void onSuccess(Object result) {
                                    notesListAdapter.remove(longClickedNote);
                                    notesListAdapter.notifyItemRemoved(longClickedIndex);
                                }
                            });


                        }
                    })
                    .setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.cancel();
                        }
                    });

            builder.show();



//            showAlertDialogFragment();
//            resultDelete = getArguments().getString(AlertDialogFragment.RESULT);
//
//            if (resultDelete == "delete"){
//                noteRepository.remove(longClickedNote, new Callback<Object>() {
//
//                    @Override
//                    public void onSuccess(Object result) {
//                        notesListAdapter.remove(longClickedNote);
//                        notesListAdapter.notifyItemRemoved(longClickedIndex);
//                    }
//                });
//            }


        }

        return super.onContextItemSelected(item);
    }

//    private void showAlertDialogFragment() {
//        AlertDialogFragment.newInstance()
//                .show(getChildFragmentManager(), AlertDialogFragment.TAG);
//
//    }

}
