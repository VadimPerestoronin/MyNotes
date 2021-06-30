package ru.geekbrains.mynotes.domain;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.logging.LogRecord;

import ru.geekbrains.mynotes.R;

public class NoteRepositoryImpl implements NoteRepository {

    public static final NoteRepository INSTANCE = new NoteRepositoryImpl();

    private final ArrayList<Note> notes = new ArrayList<>();

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private Handler handler = new Handler(Looper.getMainLooper());

    public NoteRepositoryImpl(){


        notes.add(new Note("id_1", new Date(), "Заметка 1", "Текст первой заметки"));
        notes.add(new Note("id_2", new Date(), "Заметка 2", "Текст второй заметки"));



    }


    @Override
    public void getNotes(Callback<List<Note>> callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(notes);
                    }
                });

            }
        });

    }

    @Override
    public void add(String title, String description, Callback<Note> callback) {
        Note note = new Note(UUID.randomUUID().toString(), new Date(), title, description);
        notes.add(note);

        callback.onSuccess(note);
    }



    @Override
    public void remove(Note note, Callback<Object> callback) {
        notes.remove(note);

        callback.onSuccess(note);

    }


    @Override
    public void update(@NonNull Note note, @Nullable Date date, @Nullable String title, @Nullable String description, Callback<Note> callback) {



        for (int i = 0; i < notes.size(); i++){
            Note item = notes.get(i);
            if(item.getId().equals(note.getId())){
                Date dateToSet = item.getDate();
                String titleToSet = item.getTitle();
                String descriptionToSet = item.getDescription();

                if(title != null){
                    titleToSet = title;
                }

                if(description != null){
                    descriptionToSet = description;
                }

                if(date != null){
                    dateToSet = date;
                }

                Note newNote = new Note(note.getId(), dateToSet, titleToSet, descriptionToSet);

                notes.remove(i);
                notes.add(i, newNote);

                callback.onSuccess(newNote);

            }
        }

        note.setDate(date);
        note.setTitle(title);
        note.setDescription(description);


        callback.onSuccess(note);
    }


}
