package com.iplay.iplaychat.entity.chat;

/**
 * Created by cheergoivan on 2017/7/27.
 */

public class ChatDO {
    private int id;
    private String participant;
    private int numOfUnreadMessages;

    public ChatDO(){}

    public ChatDO(int id, String participant, int numOfUnreadMessages) {
        this.id = id;
        this.participant = participant;
        this.numOfUnreadMessages = numOfUnreadMessages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }

    public int getNumOfUnreadMessages() {
        return numOfUnreadMessages;
    }

    public void setNumOfUnreadMessages(int numOfUnreadMessages) {
        this.numOfUnreadMessages = numOfUnreadMessages;
    }

    @Override
    public String toString() {
        return "ChatDO{" +
                "id=" + id +
                ", participant='" + participant + '\'' +
                ", numOfUnreadMessages=" + numOfUnreadMessages +
                '}';
    }
}
