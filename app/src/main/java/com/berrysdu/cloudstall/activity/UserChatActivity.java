package com.berrysdu.cloudstall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.berrysdu.cloudstall.R;
import com.berrysdu.cloudstall.adapter.ChatAdapter;
import com.berrysdu.cloudstall.objects.PersonChat;

import java.util.ArrayList;
import java.util.List;

public class UserChatActivity extends AppCompatActivity {

    private ChatAdapter chatAdapter;
    /**
     * 声明ListView
     */
    private ListView lv_chat_dialog;
    /**
     * 集合
     */
    private List<PersonChat> personChats = new ArrayList<PersonChat>();
    private Handler handler;

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat);

        initUI();

        handler= new Handler() {
            public void handleMessage(android.os.Message msg) {
                int what = msg.what;
                switch (what) {
                    case 1:
                        /**
                         * ListView条目控制在最后一行
                         */
                        lv_chat_dialog.setSelection(personChats.size());
                        break;

                    default:
                        break;
                }
            }
        };

        /**
         * 虚拟对方的消息
         */
        for (int i = 0; i <= 1; i++) {

            switch (i){
                case 0:
                    PersonChat personChat = new PersonChat();
                    personChat.setMeSend(false);
                    personChat.setChatMessage("你好，欢迎光临小摊！");
                    personChats.add(personChat);
                    break;
                case 1:
                    PersonChat personChat2 = new PersonChat();
                    personChat2.setMeSend(false);
                    personChat2.setChatMessage("点个关注不迷路~");
                    personChats.add(personChat2);
                    break;
            }

        }
        
        lv_chat_dialog = (ListView) findViewById(R.id.lv_chat_dialog);
        View btn_chat_message_send = findViewById(R.id.btn_chat_message_send);
        final com.google.android.material.textfield.TextInputEditText et_chat_message = (com.google.android.material.textfield.TextInputEditText) findViewById(R.id.et_chat_message);
        /**
         *setAdapter
         */
        chatAdapter = new ChatAdapter(this, personChats);
        lv_chat_dialog.setAdapter(chatAdapter);
        /**
         * 发送按钮的点击事件
         */
        btn_chat_message_send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (TextUtils.isEmpty(et_chat_message.getText().toString())) {
                    Toast.makeText(UserChatActivity.this, "发送内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                PersonChat personChat = new PersonChat();
                //代表自己发送
                personChat.setMeSend(true);
                //得到发送内容
                personChat.setChatMessage(et_chat_message.getText().toString());
                //加入集合
                personChats.add(personChat);
                //清空输入框
                et_chat_message.setText("");
                //刷新ListView
                chatAdapter.notifyDataSetChanged();
                handler.sendEmptyMessage(1);
            }
        });
    }


    private void initUI(){
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.chat_menu,menu);
        return true;
    }

}
