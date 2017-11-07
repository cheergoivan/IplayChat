package com.iplay.iplaychat.service.xmpp;

import android.util.Log;

import com.iplay.iplaychat.entity.chat.ChatMessageDO;
import com.iplay.iplaychat.entity.chat.MessageDO;
import com.iplay.iplaychat.service.chat.ChatListService;
import com.iplay.iplaychat.service.chat.ChatMessageService;

import org.greenrobot.eventbus.EventBus;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jxmpp.jid.EntityBareJid;

/**
 * Created by cheergoivan on 2017/7/23.
 */

public class DefaultIncomingChatMessageListener implements IncomingChatMessageListener {
    private ChatListService chatListService;
    private ChatMessageService chatMessageService;

    public DefaultIncomingChatMessageListener(){
        chatListService = new ChatListService();
        chatMessageService = new ChatMessageService();
    }

    @Override
    public void newIncomingMessage(EntityBareJid from, org.jivesoftware.smack.packet.Message message, Chat chat) {
        Log.d(DefaultIncomingChatMessageListener.class.getName(),Thread.currentThread().getName());
        MessageDO messageDO1 = new MessageDO(from.getLocalpart().toString(),message.getBody(), System.currentTimeMillis());
        int chatId = chatListService.updateChatList(messageDO1.getSender());
        ChatMessageDO chatMessageDO = new ChatMessageDO(chatId, messageDO1);
        chatMessageService.saveChatMessage(chatMessageDO);
        EventBus.getDefault().post(chatMessageDO);
        Log.d(DefaultIncomingChatMessageListener.class.getName(),"post ChatMessageDO:"+ chatMessageDO);
    }
}
