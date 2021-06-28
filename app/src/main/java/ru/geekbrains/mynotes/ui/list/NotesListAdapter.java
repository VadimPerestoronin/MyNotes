package ru.geekbrains.mynotes.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.mynotes.R;
import ru.geekbrains.mynotes.domain.Note;


public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.NoteViewHolder> {




    public interface OnNoteClickedListener{
        void onNoteClickedListener(@NonNull Note note);
    }

    public interface OnLongNoteClickedListener{
        void onLongNoteClickedListener(@NonNull Note note, int index);
    }

    private final Fragment fragment;

    private final ArrayList<Note> notes = new ArrayList<>();

    public NotesListAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    public void setData(List<Note> toSet){
        notes.clear();
        notes.addAll(toSet);
    }

    public int add(Note addedNote) {

        notes.add(addedNote);
        return notes.size() - 1;

    }

    public void remove(Note longClickedNote) {
        notes.remove(longClickedNote);

    }

    private OnNoteClickedListener listener;

    private OnLongNoteClickedListener longClickedListener;

    public OnLongNoteClickedListener getLongClickedListener() {
        return longClickedListener;
    }

    public void setLongClickedListener(OnLongNoteClickedListener longClickedListener) {
        this.longClickedListener = longClickedListener;
    }



    public OnNoteClickedListener getListener() {
        return listener;
    }

    public void setListener(OnNoteClickedListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesListAdapter.NoteViewHolder holder, int position) {

        Note note = notes.get(position);
        holder.bind(note);

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }


    class NoteViewHolder extends RecyclerView.ViewHolder{


        private final TextView noteDate;
        private final TextView noteTitle;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            fragment.registerForContextMenu(itemView);

            itemView.setOnClickListener(v -> {
                if (getListener() != null) {
                    getListener().onNoteClickedListener(notes.get(getAdapterPosition()));
                }
            });

            noteDate = itemView.findViewById(R.id.note_list_date);
            noteTitle = itemView.findViewById(R.id.note_list_title);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    itemView.showContextMenu();

                    if (getLongClickedListener() != null){
                        int index = getAdapterPosition();
                        getLongClickedListener().onLongNoteClickedListener(notes.get(index), index);
                    }
                    return true;
                }
            });



        }

        public void bind(Note note) {
            noteDate.setText(note.getDate());
            noteTitle.setText(note.getTitle());
        }
    }


}
