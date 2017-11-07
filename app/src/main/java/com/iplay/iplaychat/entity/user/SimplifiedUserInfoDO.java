package com.iplay.iplaychat.entity.user;

/**
 * Created by cheergoivan on 2017/7/25.
 */

public class SimplifiedUserInfoDO {
    private long userId;
    private String username;

    public SimplifiedUserInfoDO(){}

    public SimplifiedUserInfoDO(long userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "SimplifiedUserInfoDO{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                '}';
    }
}
