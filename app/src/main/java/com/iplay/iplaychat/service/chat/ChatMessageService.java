package com.iplay.iplaychat.service.chat;

import android.util.Log;

import com.iplay.iplaychat.IplayChatApplication;
import com.iplay.iplaychat.dao.ChatMessageDAO;
import com.iplay.iplaychat.dao.IplayChatDBOpenHelper;
import com.iplay.iplaychat.entity.chat.ChatMessageDO;
import com.iplay.iplaychat.entity.chat.MessageDO;
import com.iplay.iplaychat.service.xmpp.XMPPClient;
import com.iplay.iplaychat.vo.IncomingMessageVO;
import com.iplay.iplaychat.vo.MessageSendingStatus;
import com.iplay.iplaychat.vo.MessageVO;
import com.iplay.iplaychat.vo.OutgoingMessageVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheergoivan on 2017/7/27.
 */

public class ChatMessageService {

    private static final int rowsPerPage = 10;

    private ChatMessageDAO chatMessageDAO;

    public ChatMessageService() {
        chatMessageDAO = new ChatMessageDAO();
    }

    public List<MessageVO> queryByPage(int chatId, int page) {
        List<MessageVO> messageVOs = new ArrayList<>();
        List<MessageDO> messageDOs =chatMessageDAO.listChatMessagesByChatId(chatId, page * rowsPerPage, rowsPerPage);
        String authenticatedUser = IplayChatApplication.getAuthenticatedUser().getUsername();
        for(MessageDO messageDO:messageDOs){
            MessageVO messageVO = null;
            if(messageDO.getSender().equals(authenticatedUser)){
                messageVO = new OutgoingMessageVO(authenticatedUser, messageDO.getContent(),messageDO.isSuccessfullySent()?
                        MessageSendingStatus.SUCCESSFUL:MessageSendingStatus.FAILED);
            }else{
                messageVO = new IncomingMessageVO(messageDO.getSender(), messageDO.getContent());
            }
            messageVOs.add(0,messageVO);
        }
        return messageVOs;
    }

    public void saveChatMessage(ChatMessageDO chatMessageDO) {
        chatMessageDAO.saveChatMessage(chatMessageDO);
    }

    public boolean sendMessage(int chatId, String message, String sender, String receiver) {
        boolean successfullySent = false;
        if (XMPPClient.sendMessage(message, receiver)) {
            successfullySent = true;
        }
        saveChatMessage(new ChatMessageDO(chatId, new MessageDO(sender, message, System.currentTimeMillis(),successfullySent)));
        return successfullySent;
    }

    public String getLatestChatMessage(int chatId) {
        return chatMessageDAO.getLatestMessageByChatId(chatId);
    }
}
