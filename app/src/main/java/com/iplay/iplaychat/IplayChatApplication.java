package com.iplay.iplaychat;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.iplay.iplaychat.dao.ChatListDAO;
import com.iplay.iplaychat.dao.ChatMessageDAO;
import com.iplay.iplaychat.entity.chat.ChatDO;
import com.iplay.iplaychat.entity.user.SimplifiedUserInfoDO;
import com.iplay.iplaychat.service.user.DummyUserService;

import java.util.List;

/**
 * Created by cheergoivan on 2017/7/24.
 */

public class IplayChatApplication extends Application{
    private static Context context;
    private static SimplifiedUserInfoDO authenticatedUser;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContextObject(){
        return context;
    }

    public static SimplifiedUserInfoDO getAuthenticatedUser() {
        return authenticatedUser;
    }

    public static void setAuthenticatedUser(SimplifiedUserInfoDO authenticatedUser) {
        IplayChatApplication.authenticatedUser = authenticatedUser;
    }

    public static void initializeDatabase(){
        Log.d(IplayChatApplication.class.getName(),"initializeDatabase...");
        ChatListDAO chatListDAO = new ChatListDAO();
        chatListDAO.createTableIfNotExists();
        ChatMessageDAO chatMessageDAO = new ChatMessageDAO();
        chatMessageDAO.createTableIfNotExists();

        //insert dummy users
        List<String> friends = DummyUserService.getAllUsersExceptSelf(authenticatedUser.getUsername());
        for(String friend:friends){
            if(chatListDAO.queryByUsername(friend)==null)
                chatListDAO.saveChat(new ChatDO(0,friend,0));
        }
    }

}
