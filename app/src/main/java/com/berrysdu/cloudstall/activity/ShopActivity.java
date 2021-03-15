package com.berrysdu.cloudstall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.berrysdu.cloudstall.R;
import com.berrysdu.cloudstall.fragment.ShopFragment;
import com.berrysdu.cloudstall.objects.Position;
import com.berrysdu.cloudstall.objects.Stall;

public class ShopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);


        Bundle bundle=getIntent().getExtras();

        Stall stall=(Stall) bundle.getSerializable("stall");

        ShopFragment.currentLoadingStall=stall;


        getSupportFragmentManager().beginTransaction().replace(R.id.fragPlaceHolder,new ShopFragment()).commit();
    }
}