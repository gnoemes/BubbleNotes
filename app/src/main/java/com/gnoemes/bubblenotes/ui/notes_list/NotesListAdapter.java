package com.gnoemes.bubblenotes.ui.notes_list;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gnoemes.bubblenotes.R;
import com.gnoemes.bubblenotes.data.model.Note;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by kenji1947 on 25.09.2017.
 */

public class NotesListAdapter extends RealmRecyclerViewAdapter<Note, NotesListAdapter.NoteHolder> {

    private ItemClickListener clickListener;
    public interface ItemClickListener {
        void onClick(String note_id);
    }

    public NotesListAdapter(@Nullable OrderedRealmCollection<Note> data, ItemClickListener clickListener) {
        //TODO Отключение автообновления
        //super(data, false, false);

        super(data, true);

        //TODO разобрать конструктор суперкласса
        this.clickListener = clickListener;
    }

    @Override
    public NotesListAdapter.NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_note, parent, false);
        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(NotesListAdapter.NoteHolder holder, int position) {
        Note note = getItem(position);
        holder.id.setText(note.getId());
        holder.name.setText(note.getName());
        holder.priority.setText(note.getPriority() + "");
    }

    //TODO make Holder class static
    public class NoteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView id;
        TextView name;
        TextView priority;

        public NoteHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.idTextView);
            name = (TextView) itemView.findViewById(R.id.nameTextView);
            priority = (TextView) itemView.findViewById(R.id.priorityTextView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(getItem(getAdapterPosition()).getId());
        }
    }
}
