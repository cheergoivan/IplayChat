package com.iplay.iplaychat.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.iplay.iplaychat.IplayChatApplication;
import com.iplay.iplaychat.R;
import com.iplay.iplaychat.entity.chat.ChatMessageDO;
import com.iplay.iplaychat.entity.chat.MessageDO;
import com.iplay.iplaychat.service.chat.ChatMessageService;
import com.iplay.iplaychat.ui.adapter.ChatMessageAdapter;
import com.iplay.iplaychat.vo.IncomingMessageVO;
import com.iplay.iplaychat.ui.util.KeyboardUtils;
import com.iplay.iplaychat.vo.MessageVO;
import com.iplay.iplaychat.vo.OutgoingMessageVO;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class ChatActivity extends BaseActivity {

    private RecyclerView recyclerView;

    private ChatMessageAdapter chatMessageAdapter;

    private EditText inputMessage;

    private int chatId;

    private String authenticatedUser;

    private String participant;

    private ChatMessageService chatMessageService;

    private String latestMessage;

//    private Handler handler = new Handler();

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.activity_chat_btn_back:
                onBackBtnPressed();
                break;
            case R.id.activity_chat_btn_back1:
                onBackBtnPressed();
                break;
            case R.id.activity_chat_btn_send_msg:
                String message = inputMessage.getText().toString();
                latestMessage = message;
                chatMessageAdapter.addItem(new OutgoingMessageVO(authenticatedUser,
                        message));
                chatMessageService.sendMessage(chatId,message
                        ,authenticatedUser, participant);
                inputMessage.setText("");
                recyclerView.smoothScrollToPosition(chatMessageAdapter.getItemCount()-1);
                break;
        }
    }

    @Override
    public void initParams(Bundle savedInstanceState) {
        isSubscriber = true;
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_chat;
    }

    @Override
    public void initView(View view) {
        authenticatedUser = IplayChatApplication.getAuthenticatedUser().getUsername();
        chatMessageService = new ChatMessageService();
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        participant=bundle.getString("participant");
        TextView v=(TextView)findViewById(R.id.activity_chat_list_header_title);
        v.setText(participant);
        chatId = bundle.getInt("chatId");
        inputMessage = (EditText) findViewById(R.id.activity_chat_input_msg);
        List<MessageVO> messages = chatMessageService.queryByPage(chatId, 0);
        latestMessage = messages.size()>0?messages.get(messages.size()-1).getMessage():"";
        //messages.add(new IncomingMessageVO(IplayChatApplication.getAuthenticatedUser().getUsername(),"hhhhh hahhahhhahh"));
        chatMessageAdapter = new ChatMessageAdapter(messages);
        recyclerView = (RecyclerView) findViewById(R.id.activity_chat_recycler_view);
        recyclerView.setAdapter(chatMessageAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    showToast("click recycle view");
                    hideSoftInput();
                }
                return false;
            }
        });
    }

    @Override
    public void setListeners() {
        ImageView backBtn = (ImageView) findViewById(R.id.activity_chat_btn_back);
        backBtn.setOnClickListener(this);
        Button btnSend = (Button)findViewById(R.id.activity_chat_btn_send_msg);
        btnSend.setOnClickListener(this);
        TextView backBtn2 = (TextView)findViewById(R.id.activity_chat_btn_back1);
        backBtn2.setOnClickListener(this);

        /*
        new KeyboardChangeListener(this).setKeyBoardListener(new KeyboardChangeListener.KeyBoardListener() {
            @Override
            public void onKeyboardChange(boolean isShow, int keyboardHeight) {
                if(isShow){
                    recyclerView.smoothScrollToPosition(chatMessageAdapter.getItemCount()-1);
                }
                Log.d(TAG, "isShow = [" + isShow + "], keyboardHeight = [" + keyboardHeight + "]");
            }
        });
        */

        KeyboardUtils.addKeyboardToggleListener(this, new KeyboardUtils.SoftKeyboardToggleListener()
        {
            @Override
            public void onToggleSoftKeyboard(boolean isVisible)
            {
                if(isVisible){
                    int targetPosition = chatMessageAdapter.getItemCount()-1;
                    if(targetPosition>=0)
                        recyclerView.smoothScrollToPosition(targetPosition);
                }
                log( "keyboard visible: "+isVisible);
            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {
    }



    private void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm.isActive()&&getCurrentFocus()!=null){
            if (getCurrentFocus().getWindowToken()!=null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChatMessage(ChatMessageDO chatMessageDO) {
        MessageDO messageDO = chatMessageDO.getMessageDO();
        if(messageDO.getSender().equals(participant)){
            latestMessage = messageDO.getContent();
            chatMessageAdapter.addItem(new IncomingMessageVO(messageDO.getSender(),
                    messageDO.getContent()));
            recyclerView.smoothScrollToPosition(chatMessageAdapter.getItemCount()-1);
        }
        log("chat with "+participant+" and receive a message:"+ messageDO);
    }

    @Override
    public void onBackPressed() {
        return;
    }

    private void onBackBtnPressed(){
        Intent intent = new Intent();
        intent.putExtra("participant", participant);
        intent.putExtra("latestMessage", latestMessage);
        setResult(0, intent);
        finish();
    }


}
