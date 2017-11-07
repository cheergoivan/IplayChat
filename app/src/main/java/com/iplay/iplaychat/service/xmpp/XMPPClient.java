package com.iplay.iplaychat.service.xmpp;

import android.util.Log;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ReconnectionManager;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by cheergoivan on 2017/7/24.
 */

public class XMPPClient {
    private static final String TAG = XMPPClient.class.getName();

    private static final String DOMAIN = "106.14.165.145";

    private static final int PORT = 5222;

    private static XMPPTCPConnection xmppConnection = null;

    private static ChatManager chatManager = null;

    public static void closeXMPPConnection() {
        if (xmppConnection != null) {
            xmppConnection.disconnect();
            xmppConnection = null;
            chatManager = null;
        }
    }

    public static boolean login(String username, String password) {
        if(xmppConnection!=null&&xmppConnection.isConnected()&&xmppConnection.isAuthenticated())
            return true;
        try {
            XMPPTCPConnectionConfiguration configuration = XMPPTCPConnectionConfiguration.builder()
                    .setHostAddress(InetAddress.getByName(DOMAIN))
                    .setXmppDomain(DOMAIN)//ip
                    .setPort(PORT)//端口
                    //.setCompressionEnabled(true)//是否允许使用压缩
                    .setSendPresence(true)//是否发送Presece信息
                    .setDebuggerEnabled(true)//是否开启调试
                    //.setResource("Android")//设置登陆设备标识
                    .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                    .setUsernameAndPassword(username, password)
                    .build();
            xmppConnection = new XMPPTCPConnection(configuration);
            xmppConnection.connect();
            xmppConnection.login();
            enableAutomaticReconnection();
            return true;
        } catch (XmppStringprepException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException | SmackException | XMPPException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void enableAutomaticReconnection(){
        ReconnectionManager reconnectionManager = ReconnectionManager.getInstanceFor(xmppConnection);
      //  reconnectionManager.setReconnectionPolicy(ReconnectionManager.ReconnectionPolicy.FIXED_DELAY);
        reconnectionManager.enableAutomaticReconnection();
    }

    public static void addIncomingChatMessageListener(IncomingChatMessageListener listener){
        if(chatManager==null)
            chatManager = ChatManager.getInstanceFor(xmppConnection);
        chatManager.addIncomingListener(listener);
    }

    public static boolean sendMessage(String message, String receiver){
        try {
            Chat chat = getChat(receiver);
            chat.send(message);
            return true;
        }catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

   private static Chat getChat(String username){
       EntityBareJid jid = null;
       try {
           jid = JidCreate.entityBareFrom(username+"@"+DOMAIN);
       } catch (XmppStringprepException e) {
           e.printStackTrace();
       }
       Log.d(TAG,"connection is null:"+xmppConnection);
       Log.d(TAG,"chatManager is null:"+chatManager);
       Log.d(TAG,"Get chat for user:"+username);
       Chat chat = chatManager.chatWith(jid);
       return chat;
   }


}
