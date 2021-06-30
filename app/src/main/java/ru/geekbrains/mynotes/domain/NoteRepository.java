package ru.geekbrains.mynotes.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public interface NoteRepository {

    List<Note> getNotes();

    Note add(int id, int date, int title, int description);

    void remove(Note note);

    Note update(@NonNull Note note, @Nullable int date, @Nullable int title, @Nullable int description);
}
