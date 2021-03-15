package com.berrysdu.cloudstall.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.berrysdu.cloudstall.CircleImageView;
import com.berrysdu.cloudstall.R;
import com.berrysdu.cloudstall.activity.ShareViewActivity;
import com.berrysdu.cloudstall.activity.UserChatActivity;
import com.berrysdu.cloudstall.activity.UserInfoActivity;
import com.berrysdu.cloudstall.objects.Item;
import com.berrysdu.cloudstall.objects.ItemSer;
import com.berrysdu.cloudstall.objects.Stall;
import com.berrysdu.cloudstall.util.CircularAnimUtil;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.baidu.mapapi.BMapManager.getContext;

/**
 * Created by zhang on 2016.08.07.
 */
public class NotificationAdapter extends RecyclerViewAdapter implements onMoveAndSwipedListener {

    private Context context;
    private List<Item> mItems;


    private final int TYPE_NORMAL = 1;
    private final int TYPE_FOOTER = 2;
    private final int TYPE_HEADER = 3;

    public NotificationAdapter(Context context, Stall stall_, Resources resources1) {
        super(context,stall_,resources1);
        this.context = context;
        mItems = new ArrayList();
    }

    public void setItems(List<Item> data) {
        this.mItems.addAll(data);
        notifyDataSetChanged();
    }

    public void addItem(int position, Item insertData) {
        mItems.add(position, insertData);
        notifyItemInserted(position);
    }

    public void addItems(List<Item> data) {
        mItems.addAll(data);
        notifyItemInserted(mItems.size() - 1);
    }

    public void addHeader() {
       notifyItemInserted(mItems.size() - 1);
    }

    public void addFooter() {
        mItems.add(FOOTER);
        notifyItemInserted(mItems.size() - 1);
    }

    public void removeFooter() {
        mItems.remove(mItems.size() - 1);
        notifyItemRemoved(mItems.size());
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        parentView = parent;
        if (viewType == TYPE_NORMAL) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_noti, parent, false);
            return new RecyclerViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_footer, parent, false);
            return new FooterViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_shop_first, parent, false);
            return new HeaderViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof RecyclerViewHolder) {
            final RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;

            Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_recycler_item_show);
            recyclerViewHolder.mView.startAnimation(animation);

            TextView textView= recyclerViewHolder.mView.findViewById(R.id.titleRec);
            final Item item=mItems.get(position);
            textView.setText(item.getTitle());

            ImageView picView=recyclerViewHolder.mView.findViewById(R.id.rela_round);
            picView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.head1));


            recyclerViewHolder.mView.findViewById(R.id.rela_round).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, UserInfoActivity.class);
                    context.startActivity(intent);
                }
            });

            recyclerViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, UserChatActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        Item item = mItems.get(position);
        switch (item.getID()) {
            case 998:
                return TYPE_HEADER;
            case 999:
                return TYPE_FOOTER;
            default:
                return TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public void onItemDismiss(final int position) {
        final Item item=mItems.get(position);
        mItems.remove(position);
        notifyItemRemoved(position);

        Snackbar.make(parentView, "删除会话", Snackbar.LENGTH_SHORT)
                .setAction("撤销", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NotificationAdapter.this.addItem(position,item);
                    }
                }).show();
    }


}
