package com.berrysdu.cloudstall.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.berrysdu.cloudstall.CircleImageView;
import com.berrysdu.cloudstall.R;
import com.berrysdu.cloudstall.util.CircularAnimUtil;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class CreateStallActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_stall);



      initUI();
    }

    private void initUI(){
        androidx.appcompat.widget.Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        View confirmBtn=findViewById(R.id.loginBtn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(CreateStallActivity.this)
                        .setTitle("恭喜")
                        .setMessage("您的申请已提交，请等待审核，审核结果将在3个工作日内反馈，请留意通知中心。")
                        .setPositiveButton("好", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new AlertDialog.Builder(CreateStallActivity.this)
                                        .setTitle("恭喜")
                                        .setMessage("您的路摊审核已经通过，快分享给朋友吧！你可以从主页->我的路摊 直接进入管理中心。")
                                        .setPositiveButton("好", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(CreateStallActivity.this, MainActivity.class);
                                                intent.putExtra("logged",true);
                                                CircularAnimUtil.startActivity(CreateStallActivity.this, intent, findViewById(R.id.loginBtn), R.color.colorAccent);
                                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.appContext) ;
                                                SharedPreferences.Editor editor = prefs.edit();//获取编辑器
                                                editor.putBoolean("ownStall", true);
                                                editor.commit();//提交修改
                                            }
                                        })
                                        .setNegativeButton("取消",null)
                                        .setNeutralButton("分享",null)
                                        .show();
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.chat_menu,menu);
        return true;
    }


}