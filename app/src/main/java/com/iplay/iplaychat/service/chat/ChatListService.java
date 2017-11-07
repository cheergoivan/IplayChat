package com.iplay.iplaychat.service.chat;

import com.iplay.iplaychat.dao.ChatListDAO;
import com.iplay.iplaychat.dao.IplayChatDBOpenHelper;
import com.iplay.iplaychat.entity.chat.ChatDO;
import com.iplay.iplaychat.vo.ChatVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheergoivan on 2017/7/27.
 */

public class ChatListService {
    private ChatListDAO chatListDAO;
    private ChatMessageService chatMessageService;

    public ChatListService(){
        chatListDAO = new ChatListDAO();
        chatMessageService = new ChatMessageService();
    }

    /**
     * @param sender
     * @return chatId
     */
    public int updateChatList(String sender){
        ChatDO chatDO = chatListDAO.queryByUsername(sender);
        int chatId = chatDO ==null? -1: chatDO.getId();
        if(chatDO ==null)
            chatId = chatListDAO.saveChat(new ChatDO(0, sender, 1));
        else
            chatListDAO.updateNumOfUnreadMsg(chatId, chatDO.getNumOfUnreadMessages()+1);
        return chatId;
    }

    public List<ChatDO> getALlChats(){
        return chatListDAO.listChats();
    }

    public List<ChatVO> getAllChatVOs(){
        List<ChatVO> chatVOs = new ArrayList<>();
        List<ChatDO> chatDOs = getALlChats();
       for(ChatDO chatDO : chatDOs){
           String latestMessage = chatMessageService.getLatestChatMessage(chatDO.getId());
           chatVOs.add(new ChatVO(chatDO.getId(), chatDO.getParticipant(),latestMessage, chatDO.getNumOfUnreadMessages()));
       }
       return chatVOs;
    }

    public void clearUnreadMessages(int chatId){
        chatListDAO.updateNumOfUnreadMsg(chatId, 0);
    }
}
