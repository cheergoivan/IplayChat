package com.iplay.iplaychat.service.xmpp;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * Created by cheergoivan on 2017/7/31.
 */

public class XMPPServiceFactory {
    private XMPPService xmppService;

    private ServiceConnection xmppServiceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            xmppService = ((XMPPService.LocalBinder)service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            xmppService = null;
        }
    };

    public XMPPService getXMPPService(Context context){
        context.bindService(new Intent(context, XMPPService.class),xmppServiceConn, Service.BIND_AUTO_CREATE);
        return xmppService;
    }
}
