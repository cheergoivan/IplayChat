package com.iplay.iplaychat.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.iplay.iplaychat.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by cheergoivan on 2017/7/21.
 */

public class ChatMessageViewHolder extends RecyclerView.ViewHolder{

    CircleImageView avatarView;

    TextView messageView;

    public ChatMessageViewHolder(View itemView) {
        super(itemView);
        avatarView = (CircleImageView)itemView.findViewById(R.id.activity_chat_user_avatar);
        messageView = (TextView)itemView.findViewById(R.id.activity_chat_message);
    }
}
