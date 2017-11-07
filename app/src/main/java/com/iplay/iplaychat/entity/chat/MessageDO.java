package com.iplay.iplaychat.entity.chat;

/**
 * Created by cheergoivan on 2017/7/26.
 */

public class MessageDO {
    private String sender;
    private String content;
    private long timestamp;
    private boolean successfullySent;

    public MessageDO(){}

    public MessageDO(String sender, String content, long timestamp) {
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
        this.successfullySent = true;
    }

    public MessageDO(String sender, String content, long timestamp, boolean successfullySent) {
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
        this.successfullySent = successfullySent;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isSuccessfullySent() {
        return successfullySent;
    }

    public void setSuccessfullySent(boolean successfullySent) {
        this.successfullySent = successfullySent;
    }

    @Override
    public String toString() {
        return "MessageDO{" +
                "sender='" + sender + '\'' +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                ", successfullySent=" + successfullySent +
                '}';
    }
}
