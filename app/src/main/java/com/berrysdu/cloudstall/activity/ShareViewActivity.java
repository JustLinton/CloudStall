package com.berrysdu.cloudstall.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.berrysdu.cloudstall.CircleImageView;
import com.berrysdu.cloudstall.R;
import com.berrysdu.cloudstall.objects.Item;
import com.berrysdu.cloudstall.objects.ItemSer;
import com.google.android.material.card.MaterialCardView;

import static com.baidu.mapapi.BMapManager.getContext;


public class ShareViewActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Integer> {

    private TextView tv_share_view_tip;
    private RelativeLayout rela_round_big;
    Bitmap pic;

    public static boolean isLoadingPic=false;

    Handler handler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_view);
        Toolbar toolbar = findViewById(R.id.toolbar_share_view);
        toolbar.setTitle("商品详情");
        toolbar.setSubtitle("Info");
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
    }

    private void initView() {
        MaterialCardView card_share_view = findViewById(R.id.card_share_view);
        rela_round_big = findViewById(R.id.rela_round_big);
        tv_share_view_tip = findViewById(R.id.tv_share_view_tip);


        //处理intent携带包
        if (getIntent() != null) {
           ItemSer item = (ItemSer) getIntent().getExtras().getSerializable("item");
           byte[] bytes=getIntent().getExtras().getByteArray("pic");
           pic=BitmapFactory.decodeByteArray(bytes,0,bytes.length);

            //设置图片
            final ImageView picView=findViewById(R.id.itemPic);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    rela_round_big.setVisibility(View.GONE);
                    picView.setImageBitmap(pic);
                    ShareViewActivity.isLoadingPic=false;
                }
            },520);

            //设置商品描述
           TextView desView=findViewById(R.id.tv_card_4_des);
            desView.setText(item.getDescription());
            TextView balView=findViewById(R.id.tv_card_4_balance);
            balView.setText("RMB "+item.getBalance());

            //设置副标题
            getSupportActionBar().setSubtitle(item.getTitle());


            //初始化店主信息
            CircleImageView civ=findViewById(R.id.ivicon);
            civ.setBorderWidth(0);
            View ownerInfoGroup=findViewById(R.id.ownerInfoGroup);
            ownerInfoGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        Intent intent=new Intent(ShareViewActivity.this, UserInfoActivity.class);
                        //Bundle bundle=new Bundle();
                        // bundle.putSerializable("stall",stall);
                        //intent.putExtras(bundle);
                        startActivity(intent);


                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

        }

        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(1500);
        alphaAnimation.setStartOffset(1000);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tv_share_view_tip.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        tv_share_view_tip.startAnimation(alphaAnimation);

        ObjectAnimator downAnim = ObjectAnimator.ofFloat(card_share_view, "translationZ", 24);
        downAnim.setDuration(200);
        downAnim.setInterpolator(new AccelerateInterpolator());
        downAnim.start();
        card_share_view.setOnTouchListener(touchListener);
    }

    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    ObjectAnimator upAnim = ObjectAnimator.ofFloat(view, "translationZ", 0);
                    upAnim.setDuration(200);
                    upAnim.setInterpolator(new DecelerateInterpolator());
                    upAnim.start();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    ObjectAnimator downAnim = ObjectAnimator.ofFloat(view, "translationZ", 24);
                    downAnim.setDuration(200);
                    downAnim.setInterpolator(new AccelerateInterpolator());
                    downAnim.start();
                    break;
            }
            return false;
        }
    };

    @NonNull
    @Override
    public Loader<Integer> onCreateLoader(int id, @Nullable Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Integer> loader, Integer data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Integer> loader) {

    }
}
