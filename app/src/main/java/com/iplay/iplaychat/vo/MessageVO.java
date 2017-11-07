package com.iplay.iplaychat.vo;

/**
 * Created by cheergoivan on 2017/7/28.
 */

public class MessageVO {
    protected String author;
    protected String message;

    public MessageVO(String author, String message) {
        this.author = author;
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
