package com.iplay.iplaychat.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.iplay.iplaychat.IplayChatApplication;
import com.iplay.iplaychat.entity.chat.ChatDO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheergoivan on 2017/7/26.
 */

public class ChatListDAO extends BaseDAO{
    protected static final String TAG = ChatListDAO.class.getName();

    private String table = "chat_list_"+ IplayChatApplication.getAuthenticatedUser().getUsername();

    public void createTableIfNotExists(){
        SQLiteDatabase  db = IplayChatDBOpenHelper.getInstance().getWritableDatabase();
        db.execSQL("create table IF NOT EXISTS "+table+"(id INTEGER PRIMARY KEY AUTOINCREMENT,participant VARCHAR(20),numOfUnreadMsg INTEGER)");
        Log.d(TAG,"create table IF NOT EXISTS "+table);
//        db.close();
    }

    public List<ChatDO> listChats(){
        List<ChatDO> chatDOs = new ArrayList<>();
        SQLiteDatabase db = IplayChatDBOpenHelper.getInstance().getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM "+ table, null);
        ChatSnapshotRowMapper rowMapper = new ChatSnapshotRowMapper();
        while(cursor.moveToNext())
        {
            chatDOs.add(rowMapper.map(cursor)) ;
        }
        cursor.close();
//        db.close();
        log("listChats() result:"+chatDOs);
        return chatDOs;
    }

    public void deleteChat(int id){
        SQLiteDatabase db = IplayChatDBOpenHelper.getInstance().getWritableDatabase();
        //db.execSQL("DELETE FROM "+table+" where id = ?",new String[]{String.valueOf(id)});
        db.delete(table, "id = ?", new String[]{String.valueOf(id)});
//        db.close();
        log("delete chat where id="+id);
    }

    public ChatDO queryByUsername(String username){
        SQLiteDatabase db = IplayChatDBOpenHelper.getInstance().getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM "+table+" where participant = ?",new String[]{username});
        ChatSnapshotRowMapper rowMapper = new ChatSnapshotRowMapper();
        ChatDO chatDO = null;
        if(cursor.moveToFirst()){
            chatDO = rowMapper.map(cursor);
        }
        cursor.close();
//        db.close();
        log("queryByUsername where username is "+username+" and result is "+chatDO);
        return chatDO;
    }

    public void updateNumOfUnreadMsg(int id, int num){
        SQLiteDatabase db = IplayChatDBOpenHelper.getInstance().getWritableDatabase();
        db.execSQL("UPDATE "+table+" SET numOfUnreadMsg = ? where id = ?",new String[]{String.valueOf(num),String.valueOf(id)});
//        db.close();
    }

    /**
     * save a chatDO
     * @param chatDO
     * @return primary key
     */
    public int saveChat(ChatDO chatDO){
        SQLiteDatabase db = IplayChatDBOpenHelper.getInstance().getWritableDatabase();
        db.execSQL("INSERT INTO "+table+"(participant,numOfUnreadMsg) values(?,?)",
                new String[]{chatDO.getParticipant(),String.valueOf(chatDO.getNumOfUnreadMessages())});
        int chatId = -1;
        Cursor cursor=db.rawQuery("SELECT LAST_INSERT_ROWID() FROM "+table, null);
        if (cursor.moveToFirst()) chatId = cursor.getInt(0);
        log("save chatDO:"+chatDO.toString()+" and return id="+chatId);
        cursor.close();
//        db.close();
        return chatId;
    }

    public void updateLatestMessage(int id, String message){
        SQLiteDatabase db = IplayChatDBOpenHelper.getInstance().getWritableDatabase();
        db.beginTransaction();
        db.execSQL("UPDATE "+table+" SET message = ? where id = ?",new String[]{message, String.valueOf(id)});
        db.execSQL("UPDATE "+table+" SET numOfUnreadMsg = numOfUnreadMsg + 1 where id = ?",new String[]{String.valueOf(id)});
        db.setTransactionSuccessful();
        db.endTransaction();
//        db.close();
    }

    private class ChatSnapshotRowMapper implements RowMapper<ChatDO>{
        @Override
        public ChatDO map(Cursor cursor) {
            ChatDO chatDO = new ChatDO();
            chatDO.setId(cursor.getInt(cursor.getColumnIndex("id")));
            chatDO.setParticipant(cursor.getString(cursor.getColumnIndex("participant")));
            chatDO.setNumOfUnreadMessages(cursor.getInt(cursor.getColumnIndex("numOfUnreadMsg")));
            return chatDO;
        }
    }

    private void log(String message){
        Log.d(TAG, message);
    }
}
