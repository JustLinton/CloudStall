package com.berrysdu.cloudstall.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;



import android.app.Activity;

import android.view.ViewParent;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.Point;
import com.berrysdu.cloudstall.CircleImageView;
import com.berrysdu.cloudstall.R;
import com.berrysdu.cloudstall.activity.MainOptionsActivity;
import com.berrysdu.cloudstall.activity.NotificationCenterActivity;
import com.berrysdu.cloudstall.activity.ShopActivity;
import com.berrysdu.cloudstall.activity.UserChatActivity;
import com.berrysdu.cloudstall.activity.UserInfoActivity;
import com.berrysdu.cloudstall.func.PermissionChecker;
import com.berrysdu.cloudstall.objects.Position;
import com.berrysdu.cloudstall.objects.Stall;
import com.berrysdu.cloudstall.util.CircularAnimUtil;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class MapFragment extends Fragment {
    private Handler handler = new Handler();
    private HashMap<Integer,ArrayList<Stall>> stallsList;


    View fragView=null;
    MapView mMapView;
    BaiduMap mBaiduMap;

    double latitude=0;
    double longitude=0;


    private LocationClient bLocationClient=null;

    private FloatingActionButton fab;
    private BottomAppBar bottomAppBar;
    private TextView bottomAppBarTitle;
    private Chip unKnownChip, foodChip, groceryChip, bookChip;
    boolean isFabAlignRight = false;
    boolean isCutoutMarginZero = false;
    boolean isCutoutRadiusZero = false;
    boolean showBottomBarTitle = false;

    private HashMap<Integer,ArrayList<OverlayOptions>> overlayOptionsMap=new HashMap<>();

    Marker selectedMarker=null;

    private boolean unKnownchecked=true;
    private boolean foodchecked=true;
    private boolean grocerychecked=true;
    private boolean bookschecked=true;


    public MapFragment() {
        // Required empty public constructor
    }


    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        initialize();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragView = inflater.inflate(R.layout.fragment_map, container, false);

        initViews();
        showMap();
        initMarkerClick();
        //设置地图单击事件监听
        mBaiduMap.setOnMapClickListener(listener);

        handler.postDelayed(showDemoStalls,2500);

        return fragView;
    }



    private void initialize(){

        new PermissionChecker((Activity) getContext()).checkPermission();

    }

    private void showMap(){
        mMapView = (MapView) fragView.findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        //显示卫星图层

        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        showUsersLoc();

        UiSettings uiSettings=mBaiduMap.getUiSettings();
        uiSettings.setCompassEnabled(false);
        mMapView.showScaleControl(false);
        mMapView.showZoomControls(false);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(18));
        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING,false,BitmapDescriptorFactory
                .fromResource(R.drawable.baseline_album_white_24dp),0xAAA5D6A7,0xAA388E3c));

        new Handler().postDelayed(new Runnable(){
            public void run() {
                mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL,false,BitmapDescriptorFactory
                        .fromResource(R.drawable.baseline_album_white_24dp),0xAAA5D6A7,0xAA388E3c));
            }
        }, 3000);

        uiSettings.setOverlookingGesturesEnabled(false);


}



    private void showUsersLoc(){
        BaiduMap mBaiduMap = mMapView.getMap();
        //显示卫星图层

        mBaiduMap.setMyLocationEnabled(true);

        bLocationClient = new LocationClient(getActivity().getApplicationContext());

    //通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);

    //设置locationClientOption
        bLocationClient.setLocOption(option);

    //注册LocationListener监听器
        MyLocationListener myLocationListener = new MapFragment.MyLocationListener();
        bLocationClient.registerLocationListener(myLocationListener);
    //开启地图定位图层
        bLocationClient.start();

    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mMapView == null){
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();

            latitude=location.getLatitude();
            longitude=location.getLongitude();

            //Toast.makeText(getContext(),location.getLatitude()+""+location.getLongitude(),Toast.LENGTH_SHORT).show();
            mBaiduMap.setMyLocationData(locData);
        }
    }






    private void initViews() {
        fab = fragView.findViewById(R.id.fab_bottom_appbar);

        bottomAppBar = fragView.findViewById(R.id.bottom_App_bar);
        bottomAppBar.inflateMenu(R.menu.default_bottom_bar);
        setUnclickedBottomMenuListener();


        unKnownChip = fragView.findViewById(R.id.unkown_chip);
        foodChip = fragView.findViewById(R.id.food_chip);
        groceryChip = fragView.findViewById(R.id.grocery_chip);
        bookChip = fragView.findViewById(R.id.book_chip);


        bottomAppBarTitle = fragView.findViewById(R.id.bottom_app_bar_title);

        setupUiClicks();
        //initBottomSheet();
    }



    private void setupUiClicks() {
      /* positionChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    isFabAlignRight = checked;
                    startActivity(new Intent(getContext(), ShopActivity.class));
                    resetBottomAppBar();
            }
        });*/

        unKnownChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    Snackbar.make(getView(),"显示未分类",Snackbar.LENGTH_SHORT).show();
                }else {
                    Snackbar.make(getView(),"隐藏未分类",Snackbar.LENGTH_SHORT).show();
                }
                onClickCollect(Stall.TYPE_UNKOWN,checked);
            }
        });

        foodChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    Snackbar.make(getView(),"显示美食",Snackbar.LENGTH_SHORT).show();
                }else {
                    Snackbar.make(getView(),"隐藏美食",Snackbar.LENGTH_SHORT).show();
                }
                onClickCollect(Stall.TYPE_FOOD,checked);
            }
        });

        groceryChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    Snackbar.make(getView(),"显示杂货",Snackbar.LENGTH_SHORT).show();
                }else {
                    Snackbar.make(getView(),"隐藏杂货",Snackbar.LENGTH_SHORT).show();
                }

                onClickCollect(Stall.TYPE_GROCERY,checked);
            }
        });

        bookChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    Snackbar.make(getView(),"显示书籍",Snackbar.LENGTH_SHORT).show();
                }else {
                    Snackbar.make(getView(),"隐藏书籍",Snackbar.LENGTH_SHORT).show();
                }
                onClickCollect(Stall.TYPE_BOOK,checked);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFabAlignRight){
                    Stall stall=(Stall) selectedMarker.getExtraInfo().getSerializable("stall");

                    showShopDetailSheet(stall);


                }else {
                    showPlusSheet();
                }
            }
        });
    }

    private void resetBottomAppBar() {
        bottomAppBar.setFabAlignmentMode(isFabAlignRight ? BottomAppBar.FAB_ALIGNMENT_MODE_END : BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
        bottomAppBar.setFabCradleMargin(isCutoutMarginZero ? 0 : 17f); //initial default value 17f
        bottomAppBar.setFabCradleRoundedCornerRadius(isCutoutRadiusZero ? 0 : 28f); //initial default value 28f

        bottomAppBarTitle.setVisibility(showBottomBarTitle ? View.VISIBLE : View.GONE); //By Default its not suggested to add title but this is just a method if required.
        if (showBottomBarTitle)
            bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);

        bottomAppBar.invalidate();
    }


   private void showShopDetailSheet(final Stall stall){
       BottomSheetDialog dialog = new BottomSheetDialog(getContext());
       View view = getLayoutInflater().inflate(R.layout.fragment_shop_item, null);

       dialog.setContentView(view);

       TextView stallTitle=view.findViewById(R.id.tv_card_1_title);
       stallTitle.setText(stall.getTitle()+" >");

       TextView stallSubTitle=view.findViewById(R.id.tv_card_1_subtitle);
       stallSubTitle.setText("（测试） 地理位置经度："+ stall.getPosition().getLatitude()+"");

       CircleImageView civ=view.findViewById(R.id.ivicon);
       civ.setBorderWidth(0);

       View ownerInfoGroup=view.findViewById(R.id.ownerInfoGroup);
       ownerInfoGroup.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               try {

                   Intent intent=new Intent(getContext(), UserInfoActivity.class);
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

                       Intent intent=new Intent(getContext(), ShopActivity.class);
                       Bundle bundle=new Bundle();
                       bundle.putSerializable("stall",stall);
                       intent.putExtras(bundle);
                       startActivity(intent);


               }catch (Exception e){
                   e.printStackTrace();
               }

           }
       });

       View favShop=view.findViewById(R.id.btn_card_1_action1);
       favShop.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
             Snackbar.make(getView(),"已关注",Snackbar.LENGTH_SHORT).show();
           }
       });

       // hack bg color of the BottomSheetDialog

       dialog.show();
       dialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
   }

   private void showPlusSheet(){
       BottomSheetDialog dialog = new BottomSheetDialog(getContext());
       final View view = getLayoutInflater().inflate(R.layout.activity_main_options, null);
       dialog.setContentView(view);



           // hack bg color of the BottomSheetDialog

       final View fl=view.findViewById(R.id.fab_scrolling);
       fl.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent intent = new Intent(getContext(), NotificationCenterActivity.class);
               CircularAnimUtil.startActivity(getActivity(), intent, view.getRootView(), R.color.colorAccent);

           }
       });

       dialog.show();
       dialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
   }




    private void createMarker(Stall stall){
        LatLng point = new LatLng(stall.getPosition().getLatitude(),stall.getPosition().getLongitude());
        BitmapDescriptor bitmap;
        Bundle extraInfo=new Bundle();
        extraInfo.putSerializable("stall",stall);

        bitmap=getBitMap(stall.getType(),false);

        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap).extraInfo(extraInfo);

        if(overlayOptionsMap.get(stall.getType())==null){
            overlayOptionsMap.put(stall.getType(),new ArrayList<OverlayOptions>());
        }

        if(overlayOptionsMap.get(stall.getType())!=null){
            overlayOptionsMap.get(stall.getType()).add(option);
        }

        mBaiduMap.addOverlay(option);
    }

    private void initMarkerClick(){
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            //marker被点击时回调的方法
            //若响应点击事件，返回true，否则返回false
            //默认返回false
            @Override
            public boolean onMarkerClick(Marker marker) {
                if(isFabAlignRight){Snackbar.make(fragView,"请先取消另一路摊的选择",Snackbar.LENGTH_LONG).show();   return false;}

                Bundle extra=marker.getExtraInfo();
                Stall stall=(Stall) extra.getSerializable("stall");
                isFabAlignRight = true;
                showBottomBarTitle = true;

                handler.postDelayed(toReplaceToSelectedMenu,530);

                resetBottomAppBar();
                TextView bottomBarTitle=fragView.findViewById(R.id.bottom_app_bar_title);
                bottomBarTitle.setText(stall.getTitle());

                selectedMarker=marker;
                resetMarkerIcon(true);

                bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.action_share:
                                Snackbar.make(getView(),"分享路摊",Snackbar.LENGTH_SHORT).show();

                                break;
                        }
                        return false;
                    }
                });

                /*try {
                    handler.postDelayed(toDefaultBottomBar,8*1000);
                }catch (NullPointerException npe){
                    npe.printStackTrace();
                }*/



                /*InfoWindow.OnInfoWindowClickListener listener = new InfoWindow.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick() {
                    }
                };
                BitmapDescriptor popUpBitMap = BitmapDescriptorFactory.fromResource(R.drawable.baseline_send_white_24dp);
                InfoWindow mInfoWindow = new InfoWindow(popUpBitMap, new LatLng(stall.getPosition().getLatitude(),stall.getPosition().getLongitude()), -100,listener);
                mBaiduMap.showInfoWindow(mInfoWindow);*/

                return true;
            }
        });
    }

    private BitmapDescriptor getBitMap(int type, boolean selected){
        BitmapDescriptor bitmap;
        if(!selected){
            switch (type){
                case Stall.TYPE_GROCERY:
                    bitmap = BitmapDescriptorFactory
                            .fromResource(R.drawable.baseline_storage_white_24dp);
                    break;

                case Stall.TYPE_BOOK:
                    bitmap = BitmapDescriptorFactory
                            .fromResource(R.drawable.baseline_book_white_24dp);
                    break;
                case Stall.TYPE_FOOD:
                    bitmap = BitmapDescriptorFactory
                            .fromResource(R.drawable.baseline_food_bank_white_24dp);
                    break;
                default:
                    bitmap = BitmapDescriptorFactory
                            .fromResource(R.drawable.baseline_store_white_24dp);
            }

        }else {
            switch (type){
                case Stall.TYPE_GROCERY:
                    bitmap = BitmapDescriptorFactory
                            .fromResource(R.drawable.baseline_menu_open_white_24dp_clicked);
                    break;

                case Stall.TYPE_BOOK:
                    bitmap = BitmapDescriptorFactory
                            .fromResource(R.drawable.baseline_book_white_24dp_clicked);
                    break;
                case Stall.TYPE_FOOD:
                    bitmap = BitmapDescriptorFactory
                            .fromResource(R.drawable.baseline_food_bank_white_24dp_clicked);
                    break;
                default:
                    bitmap = BitmapDescriptorFactory
                            .fromResource(R.drawable.baseline_store_white_24dp_clicked);
            }
        }
        return bitmap;
    }

    private void resetMarkerIcon(boolean toSelectedState){
        if(selectedMarker!=null){
            Stall stall=(Stall)selectedMarker.getExtraInfo().getSerializable("stall");
            if(toSelectedState){
                selectedMarker.setIcon(getBitMap(stall.getType(),true));
            }else {
                selectedMarker.setIcon(getBitMap(stall.getType(),false));
            }
        }
    }

    private Runnable toDefaultBottomBar =new Runnable() {// 从option列表定期刷新货摊的task
        public void run() {
            bottomAppBar.replaceMenu(R.menu.default_bottom_bar);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_white_24dp));
                }
            },500);

            resetMarkerIcon(false);
            isFabAlignRight = false;
            showBottomBarTitle = false;
            resetBottomAppBar();
            setUnclickedBottomMenuListener();
        }
    };

    private Runnable toReplaceToSelectedMenu =new Runnable() {// 从option列表定期刷新货摊的task
        public void run() {
            fab.setImageDrawable(getResources().getDrawable(R.drawable.baseline_explore_black_24dp));
            bottomAppBar.replaceMenu(R.menu.marker_selected_menu);
            resetBottomAppBar();
        }
    };

    private Runnable task =new Runnable() {// 从option列表定期刷新货摊的task
        public void run() {
            // TODOAuto-generated method stub
            handler.postDelayed(this,5*60*1000);
            mBaiduMap.clear();
           // mBaiduMap.addOverlays(markersList);
        }
    };

    BaiduMap.OnMapClickListener listener = new BaiduMap.OnMapClickListener() {
        /**
         * 地图单击事件回调函数
         *
         * @param point 点击的地理坐标
         */
        @Override
        public void onMapClick(LatLng point) {
            handler.post(toDefaultBottomBar);
        }

        /**
         * 地图内 Poi 单击事件回调函数
         *
         * @param mapPoi 点击的 poi 信息
         */
        @Override
        public void onMapPoiClick(MapPoi mapPoi) {

        }
    };

    private void onClickCollect(int type,boolean checked){

        handler.post(toDefaultBottomBar);

        switch (type){
            case Stall.TYPE_UNKOWN:
                unKnownchecked=checked;
                break;
            case Stall.TYPE_BOOK:
                bookschecked=checked;
                break;
                case Stall.TYPE_FOOD:
                    foodchecked=checked;
                    break;
                    case Stall.TYPE_GROCERY:
                        grocerychecked=checked;
                        break;
        }
        mBaiduMap.clear();
        ArrayList<OverlayOptions> tmpList=new ArrayList<>();

        if(unKnownchecked&&overlayOptionsMap.get(Stall.TYPE_UNKOWN)!=null){
                for(OverlayOptions option:overlayOptionsMap.get(Stall.TYPE_UNKOWN)){
                    tmpList.add(option);
                }
        }
        if(bookschecked&&overlayOptionsMap.get(Stall.TYPE_BOOK)!=null){
            for(OverlayOptions option:overlayOptionsMap.get(Stall.TYPE_BOOK)){
                tmpList.add(option);
            }
        }
        if(foodchecked&&overlayOptionsMap.get(Stall.TYPE_FOOD)!=null){
            for(OverlayOptions option:overlayOptionsMap.get(Stall.TYPE_FOOD)){
                tmpList.add(option);
            }
        }
        if(grocerychecked&&overlayOptionsMap.get(Stall.TYPE_GROCERY)!=null){
            for(OverlayOptions option:overlayOptionsMap.get(Stall.TYPE_GROCERY)){
                tmpList.add(option);
            }
        }

        mBaiduMap.addOverlays(tmpList);
    }


    private Runnable showDemoStalls=new Runnable() {
        @Override
        public void run() {
            //Toast.makeText(getContext(),Math.random()+"",Toast.LENGTH_SHORT).show();

            createMarker(new Stall(99,new Position(getLat(latitude),getLon(longitude)),Stall.TYPE_BOOK,"憨豆先生的书店"));
            createMarker(new Stall(99,new Position(getLat(latitude),getLon(longitude)),Stall.TYPE_UNKOWN,"空的商店1（供演示）"));
            createMarker(new Stall(0,new Position(getLat(latitude),getLon(longitude)),Stall.TYPE_BOOK,"济南小书店"));
            createMarker(new Stall(0,new Position(getLat(latitude),getLon(longitude)),Stall.TYPE_UNKOWN,"空的商店2（供演示）"));
            createMarker(new Stall(99,new Position(getLat(latitude),getLon(longitude)),Stall.TYPE_FOOD,"香香零食摊"));
            createMarker(new Stall(99,new Position(getLat(latitude),getLon(longitude)),Stall.TYPE_GROCERY,"沃克杂货店"));
            createMarker(new Stall(0,new Position(getLat(latitude),getLon(longitude)),Stall.TYPE_GROCERY,"钉钉当当杂货店"));
            createMarker(new Stall(0,new Position(getLat(latitude),getLon(longitude)),Stall.TYPE_FOOD,"海绵宝宝的零食摊"));

            handler.postDelayed(stopLoadingAnim,1200);
        }
    };

    private double getLat(double latitude_){
        if(Math.random()>=0.5){
        return latitude_+Math.random()*0.01-0.002;}else {
            return latitude_-Math.random()*0.01+0.002;
        }
    }

    private double getLon(double longitude_){
        if(Math.random()>=0.5){
            return longitude_+Math.random()*0.01-0.002;
        }else {
            return longitude_-Math.random()*0.01+0.002;
        }

    }

    private Runnable stopLoadingAnim=new Runnable() {
        @Override
        public void run() {
            fragView.findViewById(R.id.loadingPage).setVisibility(View.GONE);
            Snackbar.make(fragView,"路摊已生成，你可以在方圆5公里内找到。",Snackbar.LENGTH_LONG).show();
        }
    };

    private void setUnclickedBottomMenuListener(){
        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.notificationsIcon){
                    fragView.findViewById(R.id.loadingPage).setVisibility(View.VISIBLE);
                    handler.postDelayed(showDemoStalls,2000);
                }
                return false;
            }
        });
    }

    private void setHeatMap(boolean b){
        mMapView.getMap().setBaiduHeatMapEnabled(b);
    }


}



