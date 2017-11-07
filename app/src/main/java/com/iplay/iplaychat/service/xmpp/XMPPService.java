package com.iplay.iplaychat.service.xmpp;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class XMPPService extends Service {
    private static final String TAG = XMPPService.class.getName();

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private LocalBinder binder = new LocalBinder();

    @Override
    public void onCreate() {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "Service is invoke onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "Service is invoke Destroyed");
        super.onDestroy();
    }

    public class LocalBinder extends Binder {
        public XMPPService getService(){
            return XMPPService.this;
        }
    }
}
