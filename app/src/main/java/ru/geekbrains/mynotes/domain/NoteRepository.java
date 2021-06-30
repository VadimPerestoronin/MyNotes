package ru.geekbrains.mynotes.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface NoteRepository {

    void getNotes(Callback<List<Note>> callback);

    void add(String title, String description, Callback<Note> callback);

    void remove(Note note, Callback<Object> callback);

    void update(@NonNull Note note, @Nullable Date date, @Nullable String title, @Nullable String description, Callback<Note> callback);
}
