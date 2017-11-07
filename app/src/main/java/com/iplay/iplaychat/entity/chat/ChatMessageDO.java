package com.iplay.iplaychat.entity.chat;

/**
 * Created by cheergoivan on 2017/7/27.
 */

public class ChatMessageDO {
    private int chatId;
    private MessageDO messageDO;

    public ChatMessageDO(int chatId, MessageDO messageDO) {
        this.chatId = chatId;
        this.messageDO = messageDO;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public MessageDO getMessageDO() {
        return messageDO;
    }

    public void setMessageDO(MessageDO messageDO) {
        this.messageDO = messageDO;
    }

    @Override
    public String toString() {
        return "ChatMessageDO{" +
                "chatId=" + chatId +
                ", messageDO=" + messageDO +
                '}';
    }
}
