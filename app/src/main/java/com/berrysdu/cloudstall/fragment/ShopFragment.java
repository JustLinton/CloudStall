package com.berrysdu.cloudstall.fragment;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.berrysdu.cloudstall.ItemTouchHelperCallback;
import com.berrysdu.cloudstall.R;
import com.berrysdu.cloudstall.activity.UserChatActivity;
import com.berrysdu.cloudstall.activity.UserInfoActivity;
import com.berrysdu.cloudstall.adapter.RecyclerViewAdapter;
import com.berrysdu.cloudstall.func.DemoContentProvider;
import com.berrysdu.cloudstall.objects.Item;
import com.berrysdu.cloudstall.objects.Position;
import com.berrysdu.cloudstall.objects.Stall;
import com.berrysdu.cloudstall.util.AppUtils;
import com.berrysdu.cloudstall.util.CircularAnimUtil;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopFragment extends Fragment {

    View fragView;
    public static Stall currentLoadingStall;

    boolean alreadyToTheLastOne=false;

    private int testInt=1;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private ExtendedFloatingActionButton efab;

    private int color = 0;
    private List<Item> data;
    private Item insertData;
    private boolean loading;
    private int loadTimes = 0;


    public ShopFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ShopFragment newInstance() {
        ShopFragment fragment = new ShopFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragView= inflater.inflate(R.layout.fragment_shop, container, false);
        initView(fragView,new RecyclerViewAdapter(getContext(),currentLoadingStall,getResources()));
        Toolbar toolbar = fragView.findViewById(R.id.toolbar_recycler_view);
        toolbar.setNavigationIcon(R.drawable.baseline_album_white_24dp);
        toolbar.setTitle(currentLoadingStall.getTitle());
        toolbar.setSubtitle("的橱窗 >");
        return fragView;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();

    }


    //提供数据源
    private void initData() {
        data = new ArrayList<>();
        if(currentLoadingStall!=null){
            switch (currentLoadingStall.getType()){
                case Stall.TYPE_UNKOWN:
                    if(testInt>=40){
                        int c=1;
                    }else {
                        for (int i = testInt; i <= testInt+ 20; i++) {
                            Item newItem=new Item(i,i+"",i+"",i);

                            if(i<=15){
                                newItem.setPic(BitmapFactory.decodeResource(getResources(),R.drawable.material_design_1));
                            }else {
                                newItem.setPic(BitmapFactory.decodeResource(getResources(),R.drawable.book6));
                            }
                            if(i==2){newItem=new DemoContentProvider(Stall.TYPE_BOOK,getResources()).getItem(1); }
                            data.add(newItem);
                            if(i==32)break;
                        }
                        testInt=testInt+21;
                    }
                case Stall.TYPE_BOOK:
                    if(testInt>=40){
                        int c=1;
                    }else {
                        for (int i = testInt; i <= testInt+ 20; i++) {
                            Item newItem;

                            if(i<=7){
                                if(currentLoadingStall.getID()==99){
                                    newItem=new DemoContentProvider(Stall.TYPE_BOOK,getResources()).getItem(i);
                                }else {
                                    newItem=new DemoContentProvider(Stall.TYPE_BOOK,getResources()).getItem(i+7);
                                }
                            }else {
                                newItem = new DemoContentProvider(Stall.TYPE_BOOK,getResources()).getItem(0);
                            }
                            data.add(newItem);
                            if(i==32)break;
                        }
                        testInt=testInt+21;
                    }

                case Stall.TYPE_FOOD:
                    if(testInt>=40){
                        int c=1;
                    }else {
                        for (int i = testInt; i <= testInt+ 20; i++) {
                            Item newItem;

                            if(i<=6){
                                if(currentLoadingStall.getID()==99){
                                    newItem=new DemoContentProvider(Stall.TYPE_FOOD,getResources()).getItem(i);
                                }else {
                                    newItem=new DemoContentProvider(Stall.TYPE_FOOD,getResources()).getItem(i+6);
                                }
                            }else {
                                newItem = new DemoContentProvider(Stall.TYPE_FOOD,getResources()).getItem(0);
                            }
                            data.add(newItem);
                            if(i==32)break;
                        }
                        testInt=testInt+21;
                    }

                case Stall.TYPE_GROCERY:
                    if(testInt>=40){
                        int c=1;
                    }else {
                        for (int i = testInt; i <= testInt+ 20; i++) {
                            Item newItem;

                            if(i<=6){
                                if(currentLoadingStall.getID()==99){
                                    newItem=new DemoContentProvider(Stall.TYPE_GROCERY,getResources()).getItem(i);
                                }else {
                                    newItem=new DemoContentProvider(Stall.TYPE_GROCERY,getResources()).getItem(i+6);
                                }
                            }else {
                                newItem = new DemoContentProvider(Stall.TYPE_GROCERY,getResources()).getItem(0);
                            }
                            data.add(newItem);
                            if(i==32)break;
                        }
                        testInt=testInt+21;
                    }
            }
        }else {
            if(testInt>=40){
                int c=1;
            }else {
                for (int i = testInt; i <= testInt+ 20; i++) {
                    Item newItem=new Item(i,i+"",i+"",i);
                    if(i<=15){
                        newItem.setPic(BitmapFactory.decodeResource(getResources(),R.drawable.material_design_1));
                    }else {
                        newItem.setPic(BitmapFactory.decodeResource(getResources(),R.drawable.material_design_3));
                    }
                    if(i==1){newItem=new Item(i,"逛哪儿官方","逛哪儿官方",i);}
                    if(i==2){newItem=new Item(i,"张三","张三",i);}
                    if(i==3){newItem=new Item(i,"李四","李四",i);}
                    if(i==4){newItem=new Item(i,"王五","王五",i);}
                    data.add(newItem);
                    if(i==32)break;
                }
                testInt=testInt+21;
            }
        }



        insertData = new Item(0,0+"",0+"",0);
    }

    public void initView(View fragView_,RecyclerViewAdapter adapter) {
        efab = fragView_. findViewById(R.id.efab_recycler_view);

        mRecyclerView = fragView_. findViewById(R.id.recycler_view_recycler_view);

        if (AppUtils.getScreenWidthDp(getContext()) >= 1200) {
            final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
            mRecyclerView.setLayoutManager(gridLayoutManager);
        } else if (AppUtils.getScreenWidthDp(getContext()) >= 800) {
            final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
            mRecyclerView.setLayoutManager(gridLayoutManager);
        } else {
            final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(linearLayoutManager);
        }

        try {
           // adapter = new RecyclerViewAdapter(getContext(),currentLoadingStall);
            mRecyclerView.setAdapter(adapter);
            adapter.addHeader();
            adapter.setItems(data);
            adapter.addFooter();
        }catch (NullPointerException e){
            Toast.makeText(getContext(),"发生网络错误",Toast.LENGTH_SHORT).show();
        }


        efab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();

                Intent intent = new Intent(getContext(), UserChatActivity.class);
                CircularAnimUtil.startActivity(getActivity(), intent, efab, R.color.colorAccent);
            }
        });

        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(adapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

        swipeRefreshLayout = fragView_. findViewById(R.id.swipe_refresh_layout_recycler_view);
        swipeRefreshLayout.setColorSchemeResources(R.color.google_blue, R.color.google_green, R.color.google_red, R.color.google_yellow);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (color > 4) {
                                color = 0;
                            }

                            alreadyToTheLastOne=false;
                            swipeRefreshLayout.setRefreshing(false); }
                }, 2000);
            }
        });

        mRecyclerView.addOnScrollListener(getScrollListener(adapter));
    }


    public RecyclerView.OnScrollListener getScrollListener(final RecyclerViewAdapter adapter){
        RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    efab.shrink();
                } else {
                    efab.extend();
                }

                final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (!loading && linearLayoutManager.getItemCount() == (linearLayoutManager.findLastVisibleItemPosition() + 1)) {
                    loadMoreData(adapter);
                    loading = true;
                }
            }
        };
        return scrollListener;
    }



    //加载更多
    public void loadMoreData(final RecyclerViewAdapter adapter) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (loadTimes <= 5) {

                    if(data.size()>0){
                        adapter.removeFooter();
                        loading = false;
                        data.clear();
                        initData();
                    }

                    if(data.size()>0){
                        adapter.addItems(data);
                        adapter.addFooter();
                        loadTimes++;
                    }else {
                        if(!alreadyToTheLastOne){
                            Snackbar.make(mRecyclerView,"我是有底线的!", Snackbar.LENGTH_SHORT).show();
                            alreadyToTheLastOne=true;
                        }

                    }
                }
            }

            }, 1500);
    }
}