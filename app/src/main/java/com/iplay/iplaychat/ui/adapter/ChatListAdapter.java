package com.iplay.iplaychat.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iplay.iplaychat.R;
import com.iplay.iplaychat.service.chat.ChatListService;
import com.iplay.iplaychat.service.user.DummyUserService;
import com.iplay.iplaychat.vo.ChatVO;
import com.iplay.iplaychat.service.user.UserService;

import java.util.List;

/**
 * Created by cheergoivan on 2017/7/19.
 */

public class ChatListAdapter extends RecyclerView.Adapter<ChatListItemViewHolder>{

    private List<ChatVO> chatList;

    private RecyclerViewOnItemClickListener onItemClickListener;

    public ChatListAdapter(List<ChatVO> chatList) {
        this.chatList = chatList;
    }

    public void setOnItemClickListener(RecyclerViewOnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ChatListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_chat_list_item, parent, false);
        return new ChatListItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ChatListItemViewHolder holder, int position) {
        ChatVO chatVO = chatList.get(position);
        holder.avatarView.setImageResource(DummyUserService.getUserAvatar(chatVO.getParticipant()));
        holder.usernameView.setText(chatVO.getParticipant());
        holder.messageView.setText(chatVO.getLatestMessage());
        if(chatVO.getNumOfUnreadMessages()==0){
            holder.numOfUnreadMessageView.setVisibility(View.GONE);
        }else{
            holder.numOfUnreadMessageView.setVisibility(View.VISIBLE);
            holder.numOfUnreadMessageView.setText(String.valueOf(chatVO.getNumOfUnreadMessages()));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View v) {
                if(onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, pos);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemLongClick(holder.itemView, pos);
                }
                //表示此事件已经消费，不会触发单击事件
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatList==null?0:chatList.size();
    }

    //添加一个item
    public void addItem(ChatVO chat, int position) {
        chatList.add(position, chat);
        notifyItemInserted(position);
    }

    public void removeItem(final int position) {
        chatList.remove(position);
        notifyItemRemoved(position);
    }

    public List<ChatVO> getChatList() {
        return chatList;
    }

    /**
     *
     * @param chatVO
     * @return return 0 if add a new item, return 1 if the chatVO already exists.
     */
    public int addOrUpdate(ChatVO chatVO){
      for(int i=0;i<chatList.size();i++){
          if(chatVO.getParticipant().equals(chatList.get(i).getParticipant())){
              update(i, chatVO);
              return 1;
          }
      }
      addItem(chatVO,0);
      return 0;
    }

    public void update(int position, ChatVO newChatVO){
        if(position<chatList.size()){
            ChatVO chatVO = chatList.get(position);
            chatVO.setLatestMessage(newChatVO.getLatestMessage());
            chatVO.setNumOfUnreadMessages(chatVO.getNumOfUnreadMessages()+1);
            notifyItemChanged(position);
        }
    }

    public void clearUnreadMessages(String participant){
        for(int i=0;i<chatList.size();i++){
            if(participant.equals(chatList.get(i).getParticipant())){
                chatList.get(i).setNumOfUnreadMessages(0);
                notifyItemChanged(i);
            }
        }
    }

    /**
     *
     * @param participant
     * @param message
     * @return
     */
    public int setLatestMessageAndClearUnreadMessages(String participant,String message){
        int chatId = -1;
        for(int i=0;i<chatList.size();i++){
            if(participant.equals(chatList.get(i).getParticipant())){
                chatList.get(i).setNumOfUnreadMessages(0);
                chatList.get(i).setLatestMessage(message);
                chatId = chatList.get(i).getId();
                notifyItemChanged(i);
                break;
            }
        }
        return chatId;
    }
}
