package ru.geekbrains.mynotes.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class NoteFirestoreRepository implements NoteRepository {

    public static final NoteRepository INSTANCE = new NoteFirestoreRepository();

    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    private final static String NOTES = "notes";
    private final static String DATE = "date";
    private final static String TITLE = "title";
    private final static String DESCRIPTION = "description";


    @Override
    public void getNotes(Callback<List<Note>> callback) {
        firebaseFirestore.collection(NOTES)
                .orderBy(DATE, Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {

                            ArrayList<Note> notes = new ArrayList<>();

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Date date = ((Timestamp) document.get(DATE)).toDate();
                                String title = (String) document.get(TITLE);
                                String description = (String) document.get(DESCRIPTION);
                                notes.add(new Note(document.getId(), date, title, description));

                            }

                            callback.onSuccess(notes);
                        } else {
                            task.getException();
                        }

                    }
                });
    }

    @Override
    public void add(String title, String description, Callback<Note> callback) {
        HashMap<String, Object> data = new HashMap<>();

        Date date = new Date();

        data.put(DATE, date);
        data.put(TITLE, title);
        data.put(DESCRIPTION, description);


        firebaseFirestore.collection(NOTES)
                .add(data)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Note note = new Note(task.getResult().getId(), date, title, description);
                            callback.onSuccess(note);
                        }

                    }

                });

    }

    @Override
    public void remove(Note note, Callback<Object> callback) {
        firebaseFirestore.collection(NOTES)
                .document(note.getId())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            callback.onSuccess(note);
                        }
                    }
                });

    }


    @Override
    public void update(@NonNull Note note, @Nullable Date date, @Nullable String title, @Nullable String description, Callback<Note> callback) {


        HashMap<String, Object> data = new HashMap<>();


        data.put(DATE, date);
        data.put(TITLE, title);
        data.put(DESCRIPTION, description);

        firebaseFirestore.collection(NOTES)
                .document(note.getId())
                .update(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            callback.onSuccess(note);
                        }

                    }
                });


    }
}
