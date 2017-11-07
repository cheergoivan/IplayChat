package com.iplay.iplaychat.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.iplay.iplaychat.IplayChatApplication;

/**
 * Created by cheergoivan on 2017/7/26.
 */

public class IplayChatDBOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE = "IplayChat";
    private static final int VERSION = 1;
    private static IplayChatDBOpenHelper instance = null;

    public synchronized static IplayChatDBOpenHelper getInstance(){
        if(instance==null)
            instance = new IplayChatDBOpenHelper(IplayChatApplication.getContextObject(),DATABASE,null,VERSION);
        return instance;
    }

    private IplayChatDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
