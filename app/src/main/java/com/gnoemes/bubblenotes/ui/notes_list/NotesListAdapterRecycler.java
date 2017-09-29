package com.gnoemes.bubblenotes.ui.notes_list;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gnoemes.bubblenotes.R;
import com.gnoemes.bubblenotes.data.model.Note;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kenji1947 on 25.09.2017.
 */

//TODO Draft
public class NotesListAdapterRecycler extends RecyclerView.Adapter<NotesListAdapterRecycler.NoteHolder> {

    private final OnItemClickListener clickListener;
    private List<Note> adapterData;

    public interface OnItemClickListener {
        void onClick(String id);
    }

    public NotesListAdapterRecycler(@Nullable List<Note> adapterData, OnItemClickListener clickListener) {
        this.clickListener = clickListener;
        this.adapterData = adapterData;
    }

    @Override
    public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_note, parent, false);
        return new NotesListAdapterRecycler.NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteHolder holder, int position) {
        Note note = getItem(position);
        holder.id.setText(note.getId());
        holder.name.setText(note.getName());
        if (note.getPriority() < 4) {
            holder.itemContainer.setBackgroundColor(holder.itemContainer.getResources().getColor(R.color.softRed));
            holder.priority.setBackgroundColor(holder.priority.getResources().getColor(R.color.deepRed));
        } else if (note.getPriority() < 8){
            holder.itemContainer.setBackgroundColor(holder.itemContainer.getResources().getColor(R.color.softYellow));
            holder.priority.setBackgroundColor(holder.priority.getResources().getColor(R.color.deepYellow));
        } else {
            holder.itemContainer.setBackgroundColor(holder.itemContainer.getResources().getColor(R.color.softGreen));
            holder.priority.setBackgroundColor(holder.priority.getResources().getColor(R.color.deepGreen));
        }
        holder.priority.setText(note.getPriority() + "");
    }


    @Override
    public int getItemCount() {
        return isDataValid() ? adapterData.size() : 0;
    }

    private boolean isDataValid() {
        return adapterData != null;
    }


    public Note getItem(int index) {
        return isDataValid() ? adapterData.get(index) : null;
    }

    public void updateData(@Nullable List<Note> data) {
        this.adapterData = data;
        notifyDataSetChanged();
    }

    //TODO make Holder class static
    public class NoteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.itemContainer)
        LinearLayout itemContainer;
        @BindView(R.id.idTextView)
        TextView id;
        @BindView(R.id.nameTextView)
        TextView name;
        @BindView(R.id.priorityTextView)
        TextView priority;

        public NoteHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(getItem(getAdapterPosition()).getId());
        }
    }
}
