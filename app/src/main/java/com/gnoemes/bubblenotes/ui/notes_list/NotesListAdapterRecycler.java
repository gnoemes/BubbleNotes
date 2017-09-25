package com.gnoemes.bubblenotes.ui.notes_list;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gnoemes.bubblenotes.R;
import com.gnoemes.bubblenotes.model.Note;

import io.realm.OrderedRealmCollection;

/**
 * Created by kenji1947 on 25.09.2017.
 */

//TODO Draft
public class NotesListAdapterRecycler extends RecyclerView.Adapter<NotesListAdapterRecycler.NoteHolder> {
    private NotesListAdapter.ItemClickListener clickListener;
    private OrderedRealmCollection<Note> adapterData;

    @Override
    public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(NoteHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public interface ItemClickListener {
        void onClick(String note_id);
    }

    public NotesListAdapterRecycler(@Nullable OrderedRealmCollection<Note> data, NotesListAdapter.ItemClickListener clickListener) {
        //TODO разобрать конструктор суперкласса
        this.clickListener = clickListener;
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
            //clickListener.onClick(getItem(getAdapterPosition()).getId());
        }
    }
}
