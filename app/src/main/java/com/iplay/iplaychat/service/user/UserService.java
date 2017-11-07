package com.iplay.iplaychat.service.user;

import android.util.Log;

import com.iplay.iplaychat.IplayChatApplication;
import com.iplay.iplaychat.R;
import com.iplay.iplaychat.dao.UserDAO;
import com.iplay.iplaychat.entity.user.SimplifiedUserInfoDO;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by cheergoivan on 2017/7/24.
 */

public class UserService {
    private static final String TAG = UserService.class.getName();

    private UserDAO userDAO;

    public UserService(){
        userDAO = new UserDAO(IplayChatApplication.getContextObject());
    }

    public void setAuthenticatedUser(SimplifiedUserInfoDO simplifiedUserInfoDO){
        userDAO.saveAuthenticatedUserRecord(simplifiedUserInfoDO);
        IplayChatApplication.setAuthenticatedUser(simplifiedUserInfoDO);
        Log.d(TAG,"setAuthenticatedUser:"+simplifiedUserInfoDO);
    }

    public void removeAuthenticatedUser(){
        userDAO.deleteAuthenticatedUserRecord();
        IplayChatApplication.setAuthenticatedUser(null);
        Log.d(TAG,"removeAuthenticatedUser");
    }

    public boolean hasAuthenticatedUser(){
        boolean hasAuthenticatedUser = IplayChatApplication.getAuthenticatedUser()!=null || userDAO.hasAuthenticatedUserRecord();
        Log.d(TAG,"hasAuthenticatedUser:"+hasAuthenticatedUser);
        if(hasAuthenticatedUser&&IplayChatApplication.getAuthenticatedUser()==null) {
            IplayChatApplication.setAuthenticatedUser(userDAO.loadAuthenticatedUser());
            Log.d(TAG,"load hasAuthenticatedUser from SP to IplayChatApplication"+ IplayChatApplication.getAuthenticatedUser());
        }
        return hasAuthenticatedUser;
    }

}
