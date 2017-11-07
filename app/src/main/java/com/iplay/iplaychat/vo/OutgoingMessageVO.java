package com.iplay.iplaychat.vo;

/**
 * Created by cheergoivan on 2017/7/28.
 */

public class OutgoingMessageVO extends MessageVO{
    private MessageSendingStatus messageSendingStatus;

    public OutgoingMessageVO(String author, String message) {
        super(author, message);
        messageSendingStatus = MessageSendingStatus.SENDING;
    }

    public OutgoingMessageVO(String author, String message, MessageSendingStatus messageSendingStatus) {
        super(author, message);
        this.messageSendingStatus = messageSendingStatus;
    }

    public MessageSendingStatus getMessageSendingStatus() {
        return messageSendingStatus;
    }

    public void setMessageSendingStatus(MessageSendingStatus messageSendingStatus) {
        this.messageSendingStatus = messageSendingStatus;
    }
}
