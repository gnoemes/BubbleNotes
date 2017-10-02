package com.gnoemes.bubblenotes.ui.notes_list;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.gnoemes.bubblenotes.R;
import com.gnoemes.bubblenotes.repo.model.Note;
import com.gnoemes.bubblenotes.util.CommonUtils;


import java.util.List;

import timber.log.Timber;

/**
 * Created by kenji1947 on 25.09.2017.
 */


public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.NoteHolder> {

    private NotesListAdapter.ItemClickListener clickListener;
    private List<Note> adapterData;
    private List<String> priorityNames;


    public List<Note> getData() {
        return adapterData;
    }

    public interface ItemClickListener {
        void onClick(Long id);
    }

    public NotesListAdapter(@Nullable List<Note> adapterData,
                            NotesListAdapter.ItemClickListener clickListener) {
        this.clickListener = clickListener;
        this.adapterData = adapterData;

    }

    @Override
    public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_note_new, parent, false);
        return new NotesListAdapter.NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteHolder holder, int position) {
        Timber.d("onBindViewHolder " + position);
        Note note = getItem(position);

        holder.name.setText(note.getName());
        holder.isComplete.setChecked(note.isComplete());

        //TODO Отрефакторить:
        List<String> list = CommonUtils.getPriorityNames(holder.itemView.getContext().getResources());
        int p = note.getDescription().getTarget().getPriority();
        if (p < list.size())
            holder.priority.setText(list.get(p));
        holder.commentsNumber.setText(note.getComments().size() + "");
    }


    @Override
    public int getItemCount() {
        return adapterData.size();
    }

    public Note getItem(int index) {
        return adapterData.get(index);
    }

    public void updateData(@Nullable List<Note> data) {
//        adapterData.clear();
//        adapterData.addAll(data);
        adapterData = data;
        //notifyDataSetChanged();
    }

    //TODO make Holder class static
    public class NoteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //Note entity
        TextView name;
        CheckBox isComplete;

        //Description entity
        TextView priority;

        //Comment entity
        TextView commentsNumber;

        public NoteHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameTextView);
            isComplete = itemView.findViewById(R.id.completeCheckBox);
            priority = itemView.findViewById(R.id.priorityTextView);
            commentsNumber = itemView.findViewById(R.id.commentsNumberTextview);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(getItem(getAdapterPosition()).getId());
        }
    }
}
