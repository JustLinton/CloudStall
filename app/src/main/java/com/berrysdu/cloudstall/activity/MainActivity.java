package com.berrysdu.cloudstall.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.berrysdu.cloudstall.CircleImageView;
import com.berrysdu.cloudstall.CustomVideoView;
import com.berrysdu.cloudstall.R;
import com.berrysdu.cloudstall.fragment.MapFragment;
import com.berrysdu.cloudstall.fragment.PreferenceFrag;
import com.berrysdu.cloudstall.func.DemoContentProvider;
import com.berrysdu.cloudstall.func.PermissionChecker;
import com.berrysdu.cloudstall.objects.Position;
import com.berrysdu.cloudstall.objects.Stall;
import com.berrysdu.cloudstall.util.CircularAnimUtil;
import com.berrysdu.cloudstall.zxing.android.CaptureActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import static com.baidu.mapapi.BMapManager.getContext;

public class MainActivity extends AppCompatActivity {

    private CustomVideoView videoview;
    //private boolean loggedIn=false;


    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    private static final int REQUEST_CODE_SCAN = 0x0000;

    public static Context appContext=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean logged=getIntent().getBooleanExtra("logged",false);
        if(!logged){  setContentView(R.layout.activity_login);
            SDKInitializer.initialize(getApplicationContext());
            //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
            //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
            SDKInitializer.setCoordType(CoordType.BD09LL);
            appContext=getApplicationContext();

            //加载视频背景
            initView();}else {
            loggedIn();
        }
    }



    private void loggedIn(){
        //  new PermissionChecker(this).checkPermission();

        PermissionChecker checker= new PermissionChecker(this);
        if (!checker.permissionIsOkay()) {
            if(!checker.hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION))
            {ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);}

            if(!checker.hasPermission(Manifest.permission.ACCESS_FINE_LOCATION))
            {            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 3);}

            if(!checker.hasPermission(Manifest.permission.READ_PHONE_STATE))
            {ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 5);}

            if(!checker.hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE))
            {ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 6);}

            if(!checker.hasPermission(Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS))
            {ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS}, 7);}

            Toast.makeText(this,"请给予权限",Toast.LENGTH_SHORT).show();

        } else {
            init();
        }

        /*Intent intent=new Intent(getContext(), ShopActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("stall",new Stall(1,new Position(12,12),1,"1"));
        intent.putExtras(bundle);*/

       // Intent intent=new Intent(getContext(), MySettingsActivity.class);

     //   startActivity(intent);
       // showPaySheet("sd");

    }

    private void init(){
        setContentView(R.layout.activity_main);
        //loggedIn=true;
        initMapFrag();
        initGUI();
    }


    private void initView() {
        //加载视频资源控件
        videoview = (CustomVideoView) findViewById(R.id.videoview);
        //设置播放加载路径
        videoview.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bg1));
        //播放
        videoview.start();
        //循环播放
        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoview.start();
            }
        });

        View loginBtn=findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoview.stopPlayback();
                loggedIn();
            }
        });

        View regBtnInLogin=findViewById(R.id.btn_card_1_action2);
        regBtnInLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegSheet();
            }
        });

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

                    case R.id.side_main_menu:
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        intent.putExtra("logged",true);
                        startActivity(intent);
break;
                    case R.id.side_search:
                        Bundle bundle=new Bundle();
                        bundle.putBoolean("firstTime",true);
                        Intent intent2=new Intent(MainActivity.this, SearchActivity.class);
                        intent2.putExtras(bundle);
                        startActivity(intent2);
                        break;

                    case R.id.T2_I1:
                        startActivity(new Intent(MainActivity.this,MySettingsActivity.class));
break;
                    case R.id.T1_I1:
                        startActivity(new Intent(MainActivity.this,PushActivity.class));
break;
                    case R.id.T1_I2:
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("欸！！")
                                .setMessage("很高兴地告诉你，该功能还在研发！！惊不惊喜意不意外！！")
                                .setPositiveButton("非常好", null)
                                .setNegativeButton("很好",null)
                                .setNeutralButton("好",null)
                                .show();
break;
                    case R.id.T2_I2:
                        startActivity(new Intent(MainActivity.this,CopyRightActivity.class));
break;
                    case R.id.T2_I3:
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("同学，")
                                .setMessage("你确定退出吗？")
                                .setPositiveButton("确定",new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                      finish();
                                    }
                                })
                                .setNegativeButton("取消",null)
                                .show();
break;
                    default:
                        Toast.makeText(MainActivity.this,"default.",Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.scan_QR:
                        onClickScan();
                        break;

                    case R.id.settings:
                        startActivity(new Intent(MainActivity.this,MySettingsActivity.class));
                        break;

                    default:
                        Toast.makeText(MainActivity.this,"otherDefault.",Toast.LENGTH_SHORT).show();
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
                Bundle bundle=new Bundle();
                bundle.putString("qu",query);
                bundle.putBoolean("firstTime",false);
                Intent intent=new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
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


    private void showRegSheet(){
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.register_sheet, null);

        dialog.setContentView(view);

        // hack bg color of the BottomSheetDialog

        dialog.show();
        dialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
    }




    public void onClickMyStallCard(View view){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this) ;
        boolean ownStall=prefs.getBoolean("ownStall",false);

        if(!ownStall){
            new AlertDialog.Builder(this)
                    .setTitle("你好呀!")
                    .setMessage("当前你还没有创建自己的摊位。通过简单的步骤即可拥有自己的摊位~")
                    .setPositiveButton("创建", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getContext(), CreateStallActivity.class);
                            CircularAnimUtil.startActivity(MainActivity.this, intent, findViewById(R.id.toolbar), R.color.colorAccent);
                        }
                    })
                    .setNegativeButton("下次吧", null)
                    .setNeutralButton("服务条款", null)
                    .show();
        }else {
            Intent intent = new Intent(getContext(), StallManagementActivity.class);
            CircularAnimUtil.startActivity(MainActivity.this, intent, findViewById(R.id.toolbar), R.color.colorAccent);
        }
    }

    public void onClickGoodsRadar(View view){
        Intent intent = new Intent(getContext(), PushActivity.class);
        CircularAnimUtil.startActivity(MainActivity.this, intent, findViewById(R.id.toolbar), R.color.colorAccent);
    }


    public void onClickScan() {
                //动态权限申请
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                } else {
                    goScan();
                }
        }

    private void goScan(){
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    goScan();
                } else {
                    Toast.makeText(this, "你拒绝了权限申请，可能无法打开相机扫码哟！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                //返回的文本内容
                String content = data.getStringExtra(DECODED_CONTENT_KEY);
                //返回的BitMap图像
                Bitmap bitmap = data.getParcelableExtra(DECODED_BITMAP_KEY);

                Toast.makeText(this,"（测试） IMEI识别码："+ content,Toast.LENGTH_SHORT).show();

                   if(content.contains("shop")){
                        switch (content){
                            case "com.berrysdu.shop/id/0000001":
                                showPaySheet("憨豆先生的店","用户1031");
                                break;
                            case "com.berrysdu.shop/id/0000002":
                                showPaySheet("海绵宝宝的店","用户4098");
                                break;
                        }
                    }


            }
        }
    }

    private void showPaySheet(final String stallName, final String ownerName){
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        final View view = getLayoutInflater().inflate(R.layout.sheet_pay, null);
        dialog.setContentView(view);

        TextView stallNameK=view.findViewById(R.id.owner_stall);
        stallNameK.setText(stallName);

        TextView stallOwnerK=view.findViewById(R.id.owner_name);
        stallOwnerK.setText(ownerName);

        View openStoreBtn=view.findViewById(R.id.stallInfoBtn);
        openStoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Stall tmpStall=new Stall(23,new Position(36.67,36.67),1,stallName);
                tmpStall.setOwnerName(ownerName);
                showShopDetailSheet(tmpStall);
            }
        });

        // hack bg color of the BottomSheetDialog

        /*final View fl=view.findViewById(R.id.fab_scrolling);
        fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), NotificationCenterActivity.class);
                CircularAnimUtil.startActivity(MainActivity.this, intent, view.getRootView(), R.color.colorAccent);

            }
        });*/

       CircleImageView civ=view.findViewById(R.id.ivicon);
       civ.setBorderWidth(0);

        dialog.show();
        dialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
    }

    private void showShopDetailSheet(final Stall stall){
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.fragment_shop_item, null);

        dialog.setContentView(view);

        TextView stallTitle=view.findViewById(R.id.tv_card_1_title);
        stallTitle.setText(stall.getTitle()+" >");

        TextView stallSubTitle=view.findViewById(R.id.tv_card_1_subtitle);
        stallSubTitle.setText("（测试） 地理位置经度：" +stall.getPosition().getLatitude()+"");

       CircleImageView civ=view.findViewById(R.id.ivicon);
       civ.setBorderWidth(0);

        View ownerInfoGroup=view.findViewById(R.id.ownerInfoGroup);
        ownerInfoGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Intent intent=new Intent(MainActivity.this, UserInfoActivity.class);
                    //Bundle bundle=new Bundle();
                    // bundle.putSerializable("stall",stall);
                    //intent.putExtras(bundle);
                    startActivity(intent);


                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        View enterShop=view.findViewById(R.id.btn_card_1_action2);
        enterShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Intent intent=new Intent(MainActivity.this, ShopActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("stall",stall);
                    intent.putExtras(bundle);
                    startActivity(intent);


                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        final View favShop=view.findViewById(R.id.btn_card_1_action1);
        favShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(favShop,"已关注",Snackbar.LENGTH_SHORT).show();
            }
        });

        // hack bg color of the BottomSheetDialog

        dialog.show();
        dialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
    }

}

