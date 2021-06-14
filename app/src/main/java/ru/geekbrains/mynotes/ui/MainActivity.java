package ru.geekbrains.mynotes.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import ru.geekbrains.mynotes.R;
import ru.geekbrains.mynotes.domain.Note;
import ru.geekbrains.mynotes.ui.details.NoteDetailsActivity;
import ru.geekbrains.mynotes.ui.details.NoteDetailsFragment;
import ru.geekbrains.mynotes.ui.list.NoteListFragment;

public class MainActivity extends AppCompatActivity implements NoteListFragment.OnNoteClicked {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onNoteClicked(Note note) {
        Toast.makeText(this, note.getTitle(), Toast.LENGTH_SHORT).show();


        boolean isLandscape = getResources().getBoolean(R.bool.isLandscape);

        if (isLandscape){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.notes_details_fragment, NoteDetailsFragment.newInstance(note))
                    .commit();

        } else {

            Intent intent = new Intent(this, NoteDetailsActivity.class);
            intent.putExtra(NoteDetailsActivity.ARG_NOTE, note);
            startActivity(intent);
        }

    }



}