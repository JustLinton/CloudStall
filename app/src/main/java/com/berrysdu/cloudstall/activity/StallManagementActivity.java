package com.berrysdu.cloudstall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.berrysdu.cloudstall.CircleImageView;
import com.berrysdu.cloudstall.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class StallManagementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stall_management);
        initUI();
        CircleImageView civ=findViewById(R.id.ivicon);
        civ.setBorderWidth(0);
    }


    private void showAddGoodSheet(){
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        final View view = getLayoutInflater().inflate(R.layout.sheet_add_new_good, null);
        dialog.setContentView(view);



        // hack bg color of the BottomSheetDialog

        /*final View fl=view.findViewById(R.id.fab_scrolling);
        fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(this, NotificationCenterActivity.class);
                CircularAnimUtil.startActivity(this, intent, view.getRootView(), R.color.colorAccent);

            }
        });*/

        dialog.show();
        dialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
    }

    private void initUI(){
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View addNewBtn=findViewById(R.id.loginBtn);
        addNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddGoodSheet();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.nc_menu,menu);
        return true;
    }

}