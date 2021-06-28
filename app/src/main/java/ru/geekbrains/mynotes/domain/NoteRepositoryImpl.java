package ru.geekbrains.mynotes.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ru.geekbrains.mynotes.R;

public class NoteRepositoryImpl implements NoteRepository {

    public static final NoteRepository INSTANCE = new NoteRepositoryImpl();

    ArrayList<Note> result = new ArrayList<>();

    @Override
    public List<Note> getNotes(){


        result.add(new Note(R.string.id_1, R.string.date_1, R.string.title_1, R.string.description_1));
        result.add(new Note(R.string.id_2, R.string.date_2, R.string.title_2, R.string.description_2));
        result.add(new Note(R.string.id_3, R.string.date_3, R.string.title_3, R.string.description_3));
        result.add(new Note(R.string.id_4, R.string.date_4, R.string.title_4, R.string.description_4));
        result.add(new Note(R.string.id_5, R.string.date_5, R.string.title_5, R.string.description_5));

        return result;

    }


    @Override
    public Note add(int id, int date, int title, int description) {
        Note note = new Note(id, date, title, description);
        result.add(note);

        return note;
    }

    @Override
    public void remove(Note note) {
        result.remove(note);

    }

    @Override
    public Note update(@NonNull Note note, @Nullable int date, @Nullable int title, @Nullable int description) {

        for (int i = 0; i < result.size(); i++){
            Note item = result.get(i);
            if(item.getId().equals(note.getId())){

            }
        }

        note.setDate(date);
        note.setTitle(title);
        note.setDescription(description);


        return note;
    }


}
