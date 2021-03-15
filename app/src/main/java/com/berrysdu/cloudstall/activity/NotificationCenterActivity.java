package com.berrysdu.cloudstall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.berrysdu.cloudstall.R;
import com.berrysdu.cloudstall.fragment.NotificationCenterFragment;
import com.berrysdu.cloudstall.fragment.ShopFragment;
import com.berrysdu.cloudstall.objects.Stall;

public class NotificationCenterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_center);

        Bundle bundle=getIntent().getExtras();

       // Stall stall=(Stall) bundle.getSerializable("stall");

       // ShopFragment.currentLoadingStall=stall;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragPlaceHolder,new NotificationCenterFragment()).commit();
        initUI();
    }

    private void initUI(){
        Toolbar toolbar=findViewById(R.id.toolbar_recycler_view);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("消息");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.nc_menu,menu);
        return true;
    }
}