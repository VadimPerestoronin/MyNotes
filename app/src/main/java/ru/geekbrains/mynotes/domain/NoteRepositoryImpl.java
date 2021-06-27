package ru.geekbrains.mynotes.domain;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.mynotes.R;

public class NoteRepositoryImpl implements NoteRepository {

    public static final NoteRepository INSTANCE = new NoteRepositoryImpl();

    @Override
    public List<Note> getNotes(){
        ArrayList<Note> result = new ArrayList<>();

        result.add(new Note(R.string.date_1, R.string.title_1, R.string.description_1));
        result.add(new Note(R.string.date_2, R.string.title_2, R.string.description_2));
        result.add(new Note(R.string.date_3, R.string.title_3, R.string.description_3));
        result.add(new Note(R.string.date_4, R.string.title_4, R.string.description_4));
        result.add(new Note(R.string.date_5, R.string.title_5, R.string.description_5));

        return result;



    }
}
