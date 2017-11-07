package com.iplay.iplaychat.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.iplay.iplaychat.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by cheergoivan on 2017/7/19.
 */

public class ChatListItemViewHolder extends RecyclerView.ViewHolder{

    CircleImageView avatarView;
    TextView usernameView;
    TextView messageView;
    TextView numOfUnreadMessageView;

    public ChatListItemViewHolder(View itemView) {
        super(itemView);
        avatarView = (CircleImageView)itemView.findViewById(R.id.activity_chat_list_avatar);
        usernameView = (TextView)itemView.findViewById(R.id.activity_chat_list_user);
        messageView = (TextView) itemView.findViewById(R.id.activity_chat_list_says);
        numOfUnreadMessageView = (TextView)itemView.findViewById(R.id.activity_chat_list_unread_messages_number);
    }

}
