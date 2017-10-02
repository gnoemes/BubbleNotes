package com.gnoemes.bubblenotes.ui.note_detail;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gnoemes.bubblenotes.R;
import com.gnoemes.bubblenotes.repo.model.Comment;
import com.gnoemes.bubblenotes.repo.model.Note;
import com.gnoemes.bubblenotes.ui.notes_list.NotesListAdapter;

import java.util.List;

/**
 * Created by kenji1947 on 01.10.2017.
 */

public class CommentsListAdapter extends RecyclerView.Adapter<CommentsListAdapter.CommentsHolder> {
    private CommentsListAdapter.ItemClickListener clickListener;

    public interface ItemClickListener {
        void onClick(int pos);
    }
    private List<Comment> adapterData;

    public CommentsListAdapter(ItemClickListener clickListener, List<Comment> adapterData) {
        this.clickListener = clickListener;
        this.adapterData = adapterData;
    }

    @Override
    public CommentsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_comment, parent, false);
        return new CommentsListAdapter.CommentsHolder(view);
    }

    public void updateData(@Nullable List<Comment> data) {
        adapterData = data;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(CommentsHolder holder, int position) {
        Comment comment = getItem(position);
        holder.body.setText(comment.getBody());
    }

    @Override
    public int getItemCount() {
        return adapterData.size();
    }

    public Comment getItem(int index) {
        return adapterData.get(index);
    }

    public class CommentsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView body;
        ImageButton delete;

        public CommentsHolder(View itemView) {
            super(itemView);
            body = itemView.findViewById(R.id.bodyTextView);
            delete = itemView.findViewById(R.id.deleteCommentImageButton);
            delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(getAdapterPosition());
        }
    }
}
