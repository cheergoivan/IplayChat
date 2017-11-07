package com.iplay.iplaychat.vo;

/**
 * Created by cheergoivan on 2017/7/18.
 */

public class ChatVO {
    private int id;
    private String participant;
    private String latestMessage;
    private int numOfUnreadMessages;

    public ChatVO(){}

    public ChatVO(int id, String participant, String latestMessage, int numOfUnreadMessages) {
        this.id = id;
        this.participant = participant;
        this.latestMessage = latestMessage;
        this.numOfUnreadMessages = numOfUnreadMessages;
    }

    public ChatVO(String participant, String latestMessage, int numOfUnreadMessages) {
        this.participant = participant;
        this.latestMessage = latestMessage;
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

    public String getLatestMessage() {
        return latestMessage;
    }

    public void setLatestMessage(String latestMessage) {
        this.latestMessage = latestMessage;
    }

    public int getNumOfUnreadMessages() {
        return numOfUnreadMessages;
    }

    public void setNumOfUnreadMessages(int numOfUnreadMessages) {
        this.numOfUnreadMessages = numOfUnreadMessages;
    }
}
