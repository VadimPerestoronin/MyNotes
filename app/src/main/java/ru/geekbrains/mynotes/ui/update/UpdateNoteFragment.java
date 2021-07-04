package ru.geekbrains.mynotes.ui.update;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.google.android.material.snackbar.Snackbar;

import ru.geekbrains.mynotes.R;
import ru.geekbrains.mynotes.domain.Callback;
import ru.geekbrains.mynotes.domain.Note;
import ru.geekbrains.mynotes.domain.NoteFirestoreRepository;
import ru.geekbrains.mynotes.domain.NoteRepository;
import ru.geekbrains.mynotes.domain.NoteRepositoryImpl;
import ru.geekbrains.mynotes.ui.list.NotesListAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class UpdateNoteFragment extends Fragment {

    public static final String TAG = "UpdateNoteFragment";

    private static final String ARG_NOTE = "ARG_NOTE";



    public static UpdateNoteFragment newInstance (Note note){
        UpdateNoteFragment fragment = new UpdateNoteFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_NOTE, note);
        fragment.setArguments(bundle);
        return fragment;
    }

    private final NoteRepository noteRepository = NoteFirestoreRepository.INSTANCE;

    private int selectedYear = -1;
    private int selectedMonth = -1;
    private int selectedDayOfMonth = -1;


    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_update_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        Note note = getArguments().getParcelable(ARG_NOTE);

        NotesListAdapter notesListAdapter = new NotesListAdapter(this);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        EditText title = view.findViewById(R.id.edit_title);
        EditText description = view.findViewById(R.id.edit_description);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {



                if(item.getItemId() == R.id.confirm_edit_note){




                    Date selectedDate = null;

                    if (selectedYear != -1 && selectedMonth != -1 && selectedDayOfMonth != -1){
                        Calendar calendar = Calendar.getInstance();

                        calendar.setTime(note.getDate());

                        calendar.set(Calendar.YEAR, selectedYear);
                        calendar.set(Calendar.MONTH, selectedMonth);
                        calendar.set(Calendar.DAY_OF_MONTH, selectedDayOfMonth);

                        selectedDate = calendar.getTime();

                    } else{
                        selectedDate = new Date();
                    }

                    noteRepository.update(note, selectedDate, title.getText().toString(), description.getText().toString(), new Callback<Note>() {
                        @Override
                        public void onSuccess(Note result) {
                            notesListAdapter.add(result);
                            notesListAdapter.notifyDataSetChanged();

                        }
                    });

                    getParentFragmentManager().popBackStack();

                    return true;
                }
                return false;
            }

        });



        title.setText(note.getTitle());


        description.setText(note.getDescription());

        DatePicker datePicker = view.findViewById(R.id.picker);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(note.getDate());

        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                selectedYear = year;
                selectedMonth = monthOfYear;
                selectedDayOfMonth = dayOfMonth;

            }
        });
    }


}
