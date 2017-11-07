package com.iplay.iplaychat.service.user;

import com.iplay.iplaychat.R;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by cheergoivan on 2017/7/29.
 */

public class DummyUserService {
    private static final Map<String,Integer> DUMMY_AVATARS = new HashMap<>();

    private static final int DEFAULT_AVATAR = R.drawable.avatar_default;

    private static final String[] DUMMY_USERS = {"ivan0","ivan1","ivan2","ivan3"};

    static {
        DUMMY_AVATARS.put("ivan0", R.drawable.avatar0);
        DUMMY_AVATARS.put("ivan1", R.drawable.avatar1);
        DUMMY_AVATARS.put("ivan2", R.drawable.avatar2);
        DUMMY_AVATARS.put("ivan3", R.drawable.avatar3);
    }

    public static int getUserAvatar(String username){
        if(DUMMY_AVATARS.containsKey(username))
            return DUMMY_AVATARS.get(username);
        return DEFAULT_AVATAR;
    }

    public static List<String> getAllUsersExceptSelf(String givenUser){
        List<String> users = new LinkedList<>();
        for(String user:DUMMY_USERS){
            if(!user.equals(givenUser))
                users.add(user);
        }
        return users;
    }

}
