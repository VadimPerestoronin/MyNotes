package ru.geekbrains.mynotes.ui;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import ru.geekbrains.mynotes.R;
import ru.geekbrains.mynotes.domain.Note;
import ru.geekbrains.mynotes.ui.details.NoteDetailsFragment;
import ru.geekbrains.mynotes.ui.info.AboutFragment;
import ru.geekbrains.mynotes.ui.list.NoteListFragment;
import ru.geekbrains.mynotes.ui.update.UpdateNoteFragment;

public class MainRouter {



    private final FragmentManager fragmentManager;

    public MainRouter(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void showNotes(){

        fragmentManager
                .beginTransaction()
                .replace(R.id.notes_list_fragment, NoteListFragment.newInstance(), NoteListFragment.TAG)
                .commit();
    }

    public void showAbout(){
        fragmentManager
                .beginTransaction()
                .replace(R.id.notes_list_fragment, AboutFragment.newInstance(), AboutFragment.TAG)
                .commit();

    }

    public void showNoteDetail(Note note){
        fragmentManager
                .beginTransaction()
                .replace(R.id.notes_list_fragment, NoteDetailsFragment.newInstance(note), NoteDetailsFragment.TAG)
                .addToBackStack(NoteDetailsFragment.TAG)
                .commit();

    }

    public void showEditNote(Note note){
        fragmentManager
                .beginTransaction()
                .replace(R.id.notes_list_fragment, UpdateNoteFragment.newInstance(note), UpdateNoteFragment.TAG)
                .addToBackStack(UpdateNoteFragment.TAG)
                .commit();

    }
}
