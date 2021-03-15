package com.berrysdu.cloudstall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.berrysdu.cloudstall.CircleImageView;
import com.berrysdu.cloudstall.R;
import com.berrysdu.cloudstall.util.CircularAnimUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UserInfoActivity extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    ImageView imageView;
    private boolean isShow=true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        initUI();
    }

    private void initUI(){
        floatingActionButton=findViewById(R.id.fab_scrolling);

        imageView=findViewById(R.id.image);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(UserInfoActivity.this, UserChatActivity.class);
                        CircularAnimUtil.startActivity(UserInfoActivity.this, intent, floatingActionButton, R.color.colorAccent);
                    }
                });
                isShow=!isShow;
            }
        });

        CircleImageView civ=findViewById(R.id.rela_round);
        civ.setBorderWidth(0);

    }
}