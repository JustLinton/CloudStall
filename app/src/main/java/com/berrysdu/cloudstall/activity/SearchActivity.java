package com.berrysdu.cloudstall.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.berrysdu.cloudstall.R;
import com.google.android.material.snackbar.Snackbar;

public class SearchActivity extends AppCompatActivity {

    private String query="world";
    private boolean firstTime=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initIntent();
        initUI();
    }

    private void initIntent(){
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        String qu=bundle.getString("qu");
        firstTime=bundle.getBoolean("firstTime");
        query=qu;
    }

    private void initUI(){

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Search");


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_top_right_menu,menu);

        MenuItem searchItem=menu.findItem(R.id.action_search);
        androidx.appcompat.widget.SearchView searchView=(androidx.appcompat.widget.SearchView) searchItem.getActionView();

        if(!firstTime){
            searchView.setIconified(false);
            searchView.setQuery(query,false);
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String queSub) {
                new AlertDialog.Builder(SearchActivity.this)
                        .setTitle("欸！！")
                        .setMessage("很高兴地告诉你，该功能刚刚步入测试！！惊不惊喜意不意外！！")
                        .setPositiveButton("非常好", null)
                        .setNegativeButton("很好",null)
                        .setNeutralButton("好",null)
                        .show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }
}