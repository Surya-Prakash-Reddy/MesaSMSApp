package com.mesa.sms.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mesa.sms.R;
import com.mesa.sms.layout.RootMessageItem;

import java.util.ArrayList;

public class RootMessagesAdapter extends RecyclerView.Adapter<RootMessagesAdapter.RootMessageViewHolder> {

    final ArrayList<RootMessageItem> mMessageItems;

    public static class RootMessageViewHolder extends RecyclerView.ViewHolder {

        public ImageView mMessageIcon;
        public TextView mMessageTitle;
        public TextView mMessageContent;

        public RootMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            mMessageIcon = itemView.findViewById(R.id.root_message_icon);
            mMessageTitle = itemView.findViewById(R.id.root_message_title);
            mMessageContent = itemView.findViewById(R.id.root_message_content);
        }
    }

    public RootMessagesAdapter(final ArrayList<RootMessageItem> messageItems) {
        this.mMessageItems = messageItems;
    }

    @NonNull
    @Override
    public RootMessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View messageView = layoutInflater.inflate(R.layout.root_message_item, parent, false);
        RootMessageViewHolder holder = new RootMessageViewHolder(messageView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RootMessageViewHolder holder, int position) {
        RootMessageItem currentMessageItem = mMessageItems.get(position);

        holder.mMessageIcon.setImageResource(currentMessageItem.getRootMessageIcon());
        holder.mMessageTitle.setText(currentMessageItem.getTitle());
        holder.mMessageContent.setText(currentMessageItem.getContent());
    }

    @Override
    public int getItemCount() {
        return mMessageItems.size();
    }
}
