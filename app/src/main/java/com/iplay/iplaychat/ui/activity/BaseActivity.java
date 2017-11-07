package com.iplay.iplaychat.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by cheergoivan on 2017/7/18.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{

    /** 是否沉浸状态栏 **/
    private boolean isStatusBarTranslucent = true;

    /** 是否允许旋转屏幕 **/
    private boolean isScreenRoateAllowed = false;

    private SystemBarTintManager tintManager;

    protected  boolean isSubscriber = false;

    /** 日志输出标志 **/
    protected final String TAG = this.getClass().getName();

    /** 设置View点击事件的处理逻辑 **/
    public abstract void widgetClick(View v);

    /** 解析bundle内容或者设置是否旋转，沉浸，全屏 **/
    public abstract void initParams(Bundle savedInstanceState);

    /**  绑定View **/
    public abstract View bindView();

    /** 绑定layout，与bindView 二选一 **/
    public abstract int bindLayout();

    /** 初始化控件 **/
    public abstract void initView(final View view);

    /** 设置监听器 **/
    public abstract void setListeners();

    /** 具体业务逻辑 **/
    public abstract void doBusiness(Context mContext);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "BaseActivity-->onCreate()");
        Bundle bundle = getIntent().getExtras();
        initParams(bundle);
        View contextView = bindView();
        if (null == contextView) {
            contextView = LayoutInflater.from(this)
                    .inflate(bindLayout(), null);
        }
        if(isStatusBarTranslucent)
            setTranslucentStatusBar();
        if(!isScreenRoateAllowed)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(contextView);
        initView(contextView);
        setListeners();
        doBusiness(this);
        if(isSubscriber)
            EventBus.getDefault().register(this);
    }

    public void startActivity(Class<?> clz) {
        startActivity(new Intent(this,clz));
    }

    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void startActivityForResult(Class<?> clz, Bundle bundle , int requestCode){
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent,requestCode);
    }

    @Override
    public void onClick(View v) {
        widgetClick(v);
    }

    /**
     * 简化Toast
     */
    protected void showToast(String msg){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
        if(isSubscriber)
            EventBus.getDefault().unregister(this);
    }

    private  void setTranslucentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            /*
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                    */
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintColor(Color.parseColor("#03A9F4"));
            tintManager.setStatusBarTintEnabled(true);


        }
    }

    public boolean isStatusBarTranslucent() {
        return isStatusBarTranslucent;
    }

    public void setStatusBarTranslucent(boolean statusBarTranslucent) {
        isStatusBarTranslucent = statusBarTranslucent;
    }

    public boolean isScreenRoateAllowed() {
        return isScreenRoateAllowed;
    }

    public void setScreenRoateAllowed(boolean screenRoateAllowed) {
        isScreenRoateAllowed = screenRoateAllowed;
    }

    protected void log(String msg) {
        Log.d(TAG, msg);
    }
}
