package com.iplay.iplaychat.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iplay.iplaychat.R;
import com.iplay.iplaychat.service.user.DummyUserService;
import com.iplay.iplaychat.service.user.UserService;
import com.iplay.iplaychat.vo.IncomingMessageVO;
import com.iplay.iplaychat.vo.MessageVO;

import java.util.List;

/**
 * Created by cheergoivan on 2017/7/21.
 */

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageViewHolder>{

    private static final int VIEW_INCOMING_MESSAGE = 0;

    private static final int VIEW_OUTCOMING_MESSAGE = 1;

    private List<MessageVO> messageVOs;

    private UserService userService = new UserService();

    public ChatMessageAdapter(List<MessageVO> messageVOs) {
        this.messageVOs = messageVOs;
    }

    @Override
    public ChatMessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ChatMessageViewHolder chatMessageViewHolder = null;
        View v = null;
        switch (viewType){
            case VIEW_INCOMING_MESSAGE:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_chat_incoming_message, parent, false);
                chatMessageViewHolder = new IncomingMessageViewHolder(v);
                break;
            case VIEW_OUTCOMING_MESSAGE:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_chat_outgoing_message, parent, false);
                chatMessageViewHolder = new OutgoingMessageViewHolder(v);
                break;
        }
        return chatMessageViewHolder;
    }

    @Override
    public void onBindViewHolder(ChatMessageViewHolder holder, int position) {
        MessageVO chatMessageVO = messageVOs.get(position);
        holder.avatarView.setImageResource(DummyUserService.getUserAvatar(chatMessageVO.getAuthor()));
        holder.messageView.setText(chatMessageVO.getMessage());
        if(holder instanceof OutgoingMessageViewHolder){
            //
        }
    }

    @Override
    public int getItemViewType(int position) {
        MessageVO messageVO = messageVOs.get(position);
        return messageVO instanceof IncomingMessageVO? VIEW_INCOMING_MESSAGE:VIEW_OUTCOMING_MESSAGE;
    }

    @Override
    public int getItemCount() {
        return messageVOs ==null?0: messageVOs.size();
    }

    public void addItem(MessageVO message){
        messageVOs.add(message);
        notifyDataSetChanged();
    }
}
