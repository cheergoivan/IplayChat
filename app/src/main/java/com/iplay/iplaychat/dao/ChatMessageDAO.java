package com.iplay.iplaychat.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.iplay.iplaychat.IplayChatApplication;
import com.iplay.iplaychat.entity.chat.ChatMessageDO;
import com.iplay.iplaychat.entity.chat.MessageDO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheergoivan on 2017/7/26.
 */

public class ChatMessageDAO extends BaseDAO{
    private static final String TAG = ChatMessageDAO.class.getName();

    private String table = "chat_message_history_"+ IplayChatApplication.getAuthenticatedUser().getUsername();

    @Override
    public void createTableIfNotExists() {
        SQLiteDatabase db = IplayChatDBOpenHelper.getInstance().getWritableDatabase();
        db.execSQL("create table IF NOT EXISTS "+table+"(id INTEGER PRIMARY KEY AUTOINCREMENT,chatId INTEGER, " +
                "sender VARCHAR(20),message VARCHAR(200),timestamp INTEGER, sending_status INTEGER)");
        Log.d(TAG,"create table IF NOT EXISTS "+table);
//        db.close();
    }

    public List<MessageDO> listChatMessagesByChatId(int chatId, int offset, int limit){
        List<MessageDO> messageDOs = new ArrayList<MessageDO>(limit);
        SQLiteDatabase db = IplayChatDBOpenHelper.getInstance().getWritableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM "+table+" WHERE chatId = ? ORDER BY timestamp DESC LIMIT ?,?",
                new String[]{String.valueOf(chatId),String.valueOf(offset),String.valueOf(limit)});
        ChatMessageRowMapper chatMessageRowMapper = new ChatMessageRowMapper();
        while(cursor.moveToNext())
        {
            messageDOs.add(chatMessageRowMapper.map(cursor));
        }
        cursor.close();
//        db.close();
        log("listChatMessagesByChatId offset:"+offset+" limit:"+limit+" result:"+messageDOs);
        return messageDOs;
    }

    public void deleteByChatId(int chatId){
        SQLiteDatabase db = IplayChatDBOpenHelper.getInstance().getWritableDatabase();
        db.delete(table, "chatId = ?",new String[]{String.valueOf(chatId)});
//        db.close();
        log("deleteByChatId where chatId is "+chatId);
    }

    public void saveChatMessage(ChatMessageDO chatMessageDO){
        SQLiteDatabase db = IplayChatDBOpenHelper.getInstance().getWritableDatabase();
        MessageDO messageDO = chatMessageDO.getMessageDO();
        db.execSQL("INSERT INTO "+table+"(chatId,sender,message,timestamp,sending_status) VALUES(?,?,?,?,?)"
                ,new String[]{String.valueOf(chatMessageDO.getChatId()), messageDO.getSender(), messageDO.getContent(),String.valueOf(messageDO.getTimestamp()),String.valueOf(messageDO.isSuccessfullySent()?1:0)});
        log("saveChatMessage "+chatMessageDO);
//        db.close();
    }

    public String getLatestMessageByChatId(int chatId){
        SQLiteDatabase db = IplayChatDBOpenHelper.getInstance().getWritableDatabase();
        Cursor cursor =  db.rawQuery("SELECT message FROM "+table+" WHERE chatId = ? ORDER BY timestamp DESC LIMIT 0,1",
                new String[]{String.valueOf(chatId)});
        String latestMessage = "";
        if(cursor.moveToFirst())
            latestMessage = cursor.getString(0);
//        db.close();
        log("getLatestMessageByChatId where chatId is "+chatId+" and result is "+latestMessage);
        return latestMessage;
    }

    private class ChatMessageRowMapper implements RowMapper<MessageDO>{
        @Override
        public MessageDO map(Cursor cursor) {
            MessageDO messageDO = new MessageDO();
            messageDO.setTimestamp(cursor.getLong(cursor.getColumnIndex("timestamp")));
            messageDO.setSender(cursor.getString(cursor.getColumnIndex("sender")));
            messageDO.setContent(cursor.getString(cursor.getColumnIndex("message")));
            messageDO.setSuccessfullySent(cursor.getInt(cursor.getColumnIndex("sending_status"))==1);
            return messageDO;
        }
    }

    private void log(String message){
        Log.d(TAG, message);
    }
}
