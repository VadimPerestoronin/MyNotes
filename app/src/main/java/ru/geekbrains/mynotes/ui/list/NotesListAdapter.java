package ru.geekbrains.mynotes.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.mynotes.R;
import ru.geekbrains.mynotes.domain.Note;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.NoteViewHolder> {

    public NotesListAdapter() {
    }

    public interface OnNoteClickedListener{
        void onNoteClickedListener(@NonNull Note note);
    }

    private ArrayList<Note> notes = new ArrayList<>();

    public void setData(List<Note> toSet){
        notes.clear();
        notes.addAll(toSet);
    }



    private OnNoteClickedListener listener;

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
        holder.noteDate.setText(note.getDate());
        holder.noteTitle.setText(note.getTitle());

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder{


        TextView noteDate;
        TextView noteTitle;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getListener() != null){
                        getListener().onNoteClickedListener(notes.get(getAdapterPosition()));
                    }
                }
            });

            noteDate = itemView.findViewById(R.id.note_list_date);
            noteTitle = itemView.findViewById(R.id.note_list_title);

        }
    }

}
