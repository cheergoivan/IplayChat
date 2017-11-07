package com.iplay.iplaychat.dao;

import android.content.Context;

import com.iplay.iplaychat.dao.sp.SPUtils;
import com.iplay.iplaychat.entity.user.SimplifiedUserInfoDO;

/**
 * Created by cheergoivan on 2017/7/26.
 */

public class UserDAO {
    private Context context ;
    private static final String AUTHENTICATED_USERNAME = "AUTHENTICATED_USERNAME";
    private static final String AUTHENTICATED_USER_ID = "AUTHENTICATED_USER_ID";

    public UserDAO(Context context) {
        this.context = context;
    }

    public boolean hasAuthenticatedUserRecord(){
        return SPUtils.contains(context,AUTHENTICATED_USERNAME)&&SPUtils.contains(context,AUTHENTICATED_USER_ID);
    }

    public void saveAuthenticatedUserRecord(SimplifiedUserInfoDO simplifiedUserInfoDO){
        SPUtils.put(context,AUTHENTICATED_USER_ID, simplifiedUserInfoDO.getUserId());
        SPUtils.put(context,AUTHENTICATED_USERNAME, simplifiedUserInfoDO.getUsername());
    }

    public SimplifiedUserInfoDO loadAuthenticatedUser(){
        return new SimplifiedUserInfoDO((Long)SPUtils.get(context,AUTHENTICATED_USER_ID,new Long(0L))
                ,(String)SPUtils.get(context,AUTHENTICATED_USERNAME,new String("")));
    }

    public void deleteAuthenticatedUserRecord(){
        SPUtils.clear(context);
    }
}
