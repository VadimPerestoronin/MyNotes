package ru.geekbrains.mynotes.domain;

import java.util.List;

public interface NoteRepository {

    List<Note> getNotes();
}
