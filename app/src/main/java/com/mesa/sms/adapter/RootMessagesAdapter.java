package com.mesa.sms.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mesa.sms.R;
import com.mesa.sms.layout.RootMessageItem;
import com.mesa.sms.listener.RootMessageClickListener;

import java.util.ArrayList;

public class RootMessagesAdapter extends RecyclerView.Adapter<RootMessagesAdapter.RootMessageViewHolder> {

    final ArrayList<RootMessageItem> mMessageItems;
    final RootMessageClickListener mListener;

    public RootMessagesAdapter(final ArrayList<RootMessageItem> messageItems,
                               final RootMessageClickListener listener) {
        this.mMessageItems = messageItems;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public RootMessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View messageView = layoutInflater.inflate(R.layout.root_message_item, parent, false);
        RootMessageViewHolder holder = new RootMessageViewHolder(messageView, mListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RootMessageViewHolder holder, int position) {
        RootMessageItem currentMessageItem = mMessageItems.get(position);

        holder.mMessageTitle.setText(currentMessageItem.getTitle());
        holder.mMessageContent.setText(currentMessageItem.getContent());
    }

    @Override
    public int getItemCount() {
        return mMessageItems.size();
    }

    public static class RootMessageViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        public TextView mMessageTitle;
        public TextView mMessageContent;
        public RootMessageClickListener mListener;

        public RootMessageViewHolder(@NonNull final View itemView,
                                     @NonNull final RootMessageClickListener listener) {
            super(itemView);
            this.mMessageTitle = itemView.findViewById(R.id.root_message_title);
            this.mMessageContent = itemView.findViewById(R.id.root_message_content);
            this.mListener = listener;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onClick();
        }

        @Override
        public boolean onLongClick(View v) {
            mListener.onLongClick();
            return true;
        }
    }
}
