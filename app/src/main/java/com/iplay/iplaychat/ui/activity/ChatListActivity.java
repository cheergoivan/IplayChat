package com.iplay.iplaychat.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.iplay.iplaychat.R;
import com.iplay.iplaychat.entity.chat.ChatMessageDO;
import com.iplay.iplaychat.entity.chat.MessageDO;
import com.iplay.iplaychat.service.chat.ChatListService;
import com.iplay.iplaychat.service.user.UserService;
import com.iplay.iplaychat.service.xmpp.XMPPClient;
import com.iplay.iplaychat.ui.adapter.ChatListAdapter;
import com.iplay.iplaychat.ui.adapter.RecyclerViewOnItemClickListener;
import com.iplay.iplaychat.vo.ChatVO;

import java.util.List;
import android.os.Bundle;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ChatListActivity extends BaseActivity {

    private RecyclerView recyclerView;

   // private ImageView backBtn;

    private ImageView searchBtn;

    private ImageView logoutBtn;

    private ChatListAdapter chatListAdapter;

    private ChatListService chatListService;

    private UserService userService;

    private int currentlyChatId = -1;

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.activity_chat_list_header_btn_search:
                showToast("click search button");
                //chatListAdapter.addItem(new ChatVO("user0","hhhhh0"),0);
                //recyclerView.scrollToPosition(0);
                break;
            case R.id.activity_chat_list_header_btn_logout:
                userService.removeAuthenticatedUser();
                XMPPClient.closeXMPPConnection();
                startActivity(LoginActivity.class);
                finish();
                break;
            default:
                showToast("click");
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
        return R.layout.activity_chat_list;
    }

    @Override
    public void initView(View view) {
        chatListService = new ChatListService();
        userService = new UserService();
        searchBtn = (ImageView) findViewById(R.id.activity_chat_list_header_btn_search);
        logoutBtn = (ImageView)findViewById(R.id.activity_chat_list_header_btn_logout);
       // backBtn = (ImageView) findViewById(R.id.activity_chat_list_header_btn_back);
        recyclerView = (RecyclerView) findViewById(R.id.activity_chat_list_recycler_view);
        final List<ChatVO> chats = chatListService.getAllChatVOs();
        chatListAdapter = new ChatListAdapter(chats);
        chatListAdapter.setOnItemClickListener(new RecyclerViewOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle data = new Bundle();
                ChatVO chatVO = chatListAdapter.getChatList().get(position);
                data.putInt("chatId",chatVO.getId());
                data.putString("participant",chatVO.getParticipant());
                currentlyChatId = chatVO.getId();
                startActivityForResult(ChatActivity.class,data, 0);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                showToast("click user "+ chatListAdapter.getChatList().get(position).getParticipant());
            }
        });
        recyclerView.setAdapter(chatListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void setListeners() {
       // backBtn.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChatMessage(ChatMessageDO chatMessageDO) {
        if(currentlyChatId ==-1 || chatMessageDO.getChatId() != currentlyChatId){
            MessageDO messageDO = chatMessageDO.getMessageDO();
            int addOrUpdate = chatListAdapter.addOrUpdate(new ChatVO(chatMessageDO.getChatId(), messageDO.getSender(), messageDO.getContent(),1));
            if(addOrUpdate==0)
                recyclerView.smoothScrollToPosition(0);
        }
        log("receive chatMessageDO:"+ chatMessageDO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        currentlyChatId = -1;
        int chatId = chatListAdapter.setLatestMessageAndClearUnreadMessages(data.getExtras().getString("participant")
                ,data.getExtras().getString("latestMessage"));
        chatListService.clearUnreadMessages(chatId);
    }
}
