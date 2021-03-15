package com.berrysdu.cloudstall.itemfrag;

import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.berrysdu.cloudstall.R;
import com.google.android.material.snackbar.Snackbar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShopItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopItemFragment extends Fragment implements View.OnClickListener, View.OnTouchListener {

    private CardView card__1;
    private ImageView img_card_1;
    private Button btn_card_1_action1, btn_card_1_action2;
    View fragView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        card__1 = fragView.findViewById(R.id.card_1);
        img_card_1 = fragView.findViewById(R.id.img_card_1);
        btn_card_1_action1 = fragView.findViewById(R.id.btn_card_1_action1);
        btn_card_1_action2 = fragView.findViewById(R.id.btn_card_1_action2);
        btn_card_1_action1.setOnClickListener(this);
        btn_card_1_action2.setOnClickListener(this);

        card__1.setOnTouchListener(this);

        fragView=inflater.inflate(R.layout.fragment_shop_first,container,false);
        return fragView;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_card_1_action1:
                Snackbar.make(view,"收藏", Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.btn_card_1_action2:
                Snackbar.make(view, "预定", Snackbar.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                ObjectAnimator downAnimator = ObjectAnimator.ofFloat(view, "translationZ", 16);
                downAnimator.setDuration(200);
                downAnimator.setInterpolator(new DecelerateInterpolator());
                downAnimator.start();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                ObjectAnimator upAnimator = ObjectAnimator.ofFloat(view, "translationZ", 0);
                upAnimator.setDuration(200);
                upAnimator.setInterpolator(new AccelerateInterpolator());
                upAnimator.start();
                break;
        }
        return false;
    }

}