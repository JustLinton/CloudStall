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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.recyclerview.widget.RecyclerView;

import com.berrysdu.cloudstall.CircleImageView;
import com.berrysdu.cloudstall.R;
import com.berrysdu.cloudstall.activity.ShareViewActivity;
import com.berrysdu.cloudstall.activity.UserInfoActivity;
import com.berrysdu.cloudstall.fragment.ShopFragment;
import com.berrysdu.cloudstall.func.DemoContentProvider;
import com.berrysdu.cloudstall.objects.Item;
import com.berrysdu.cloudstall.objects.ItemSer;
import com.berrysdu.cloudstall.objects.Stall;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.baidu.mapapi.BMapManager.getContext;

/**
 * Created by zhang on 2016.08.07.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements onMoveAndSwipedListener {

    private Context context;
    private List<Item> mItems;
    private int color = 0;
    public View parentView;
    private boolean isFirst=true;
    private Stall stall;

    Resources resources;


    private final int TYPE_NORMAL = 1;
    private final int TYPE_FOOTER = 2;
    private final int TYPE_HEADER = 3;
    public final Item FOOTER = new Item(999,"","",999);
    private Item HEADER= new Item(998,"","",999);

    public RecyclerViewAdapter(Context context, Stall stall_,Resources resources1) {
        this.context = context;
        stall=stall_;
        resources=resources1;
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
        if(isFirst){
            mItems.add(HEADER);
        }

        mItems.addAll(data);
        notifyItemInserted(mItems.size() - 1);
    }

    public void addHeader() {
        if(isFirst){
            this.mItems.add(HEADER);
        }

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

    public void setColor(int color) {
        this.color = color;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        parentView = parent;
        if (viewType == TYPE_NORMAL) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, parent, false);
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

            TextView titleView= recyclerViewHolder.mView.findViewById(R.id.titleRec);
            final Item item=mItems.get(position);
            titleView.setText(item.getTitle());

            TextView descView= recyclerViewHolder.mView.findViewById(R.id.descriptionRec);
            descView.setText(item.getDescription());

            TextView balView= recyclerViewHolder.mView.findViewById(R.id.balanceRec);
            balView.setText("RMB " + item.getBalance());

            ImageView picView=recyclerViewHolder.mView.findViewById(R.id.rela_round);
            picView.setImageBitmap(item.getPic());

            AlphaAnimation aa1 = new AlphaAnimation(1.0f, 0.1f);
            aa1.setDuration(400);
            recyclerViewHolder.rela_round.startAnimation(aa1);

            AlphaAnimation aa = new AlphaAnimation(0.1f, 1.0f);
            aa.setDuration(400);


            //颜色
            recyclerViewHolder.rela_round.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorPrimary)));

            recyclerViewHolder.rela_round.startAnimation(aa);

            recyclerViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!ShareViewActivity.isLoadingPic){

                        ShareViewActivity.isLoadingPic=true;

                        Intent intent = new Intent(context, ShareViewActivity.class);

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("item",new ItemSer(mItems.get(position)));
                        bundle.putByteArray("pic", bitmap2Bytes(mItems.get(position).getPic()));

                        intent.putExtras(bundle);

                        context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation
                                ((Activity) context, recyclerViewHolder.rela_round, "shareView").toBundle());
                    }else {
                        Toast.makeText(context,"你点击得太快了！",Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

        if(holder instanceof HeaderViewHolder){
                ((HeaderViewHolder) holder).header_text.setText(stall.getTitle());
            ((HeaderViewHolder) holder).headCover.setImageBitmap(new DemoContentProvider(stall.getType(),resources).getCover(stall.getID()));
                ((HeaderViewHolder) holder).ownerInfoGrop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {

                            Intent intent=new Intent(getContext(), UserInfoActivity.class);
                            //Bundle bundle=new Bundle();
                            // bundle.putSerializable("stall",stall);
                            //intent.putExtras(bundle);
                            context.startActivity(intent);


                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });

                isFirst=false;
        }
    }


    public byte[] bitmap2Bytes(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
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
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mItems, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return false;
    }

    @Override
    public void onItemDismiss(final int position) {
        final Item item=mItems.get(position);
        mItems.remove(position);
        notifyItemRemoved(position);

        Snackbar.make(parentView, "不感兴趣", Snackbar.LENGTH_SHORT)
                .setAction("撤销", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RecyclerViewAdapter.this.addItem(position,item);
                    }
                }).show();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
       public View mView;
       public CircleImageView rela_round;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            rela_round = itemView.findViewById(R.id.rela_round);
            rela_round.setBorderWidth(0);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progress_bar_load_more;

        public FooterViewHolder(View itemView) {
            super(itemView);
            progress_bar_load_more = itemView.findViewById(R.id.progress_bar_load_more);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView header_text;
        public CircleImageView civ;
        public View ownerInfoGrop;
        public ImageView headCover;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            header_text = itemView.findViewById(R.id.header_text);
            headCover=itemView.findViewById(R.id.img_card_2);
            civ=itemView.findViewById(R.id.ivicon);
            civ.setBorderWidth(0);
            ownerInfoGrop=itemView.findViewById(R.id.ownerInfoGroup);
        }
    }

}
