package com.berrysdu.cloudstall.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.berrysdu.cloudstall.fragment.MapFragment;
import com.berrysdu.cloudstall.R;
import com.google.android.material.navigation.NavigationView;

public class MainMapActivity extends AppCompatActivity {

   /* //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener=null;

    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize(){
        //  new PermissionChecker(this).checkPermission();

        initMapFrag();
        initGUI();
    }

    private void initGUI(){
        androidx.appcompat.widget.Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView=(NavigationView)findViewById(R.id.main_side_drawer_naviView);
        DrawerLayout drawerLayout=(DrawerLayout)findViewById(R.id.main_side_drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, 0, 0);
        drawerLayout.setDrawerListener(toggle);

        toggle.syncState();



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    default:
                        Toast.makeText(MainMapActivity.this,"default.",Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {

                    default:
                        Toast.makeText(MainMapActivity.this,"otherDefault.",Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_top_right_menu,menu);

        MenuItem searchItem=menu.findItem(R.id.action_search);
        androidx.appcompat.widget.SearchView searchView=(androidx.appcompat.widget.SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                /*Bundle bundle=new Bundle();
                bundle.putString("qu",query);
                bundle.putBoolean("firstTime",false);
                Intent intent=new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);*/
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    private void initMapFrag(){
        getSupportFragmentManager().beginTransaction().replace(R.id.mainActivityFragPlace,new MapFragment()).commit();
    }

    private void onClickMain(){
    }


}
