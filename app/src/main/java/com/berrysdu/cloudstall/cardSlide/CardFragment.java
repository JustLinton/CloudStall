package com.berrysdu.cloudstall.cardSlide;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.berrysdu.cloudstall.R;
import com.google.android.material.snackbar.Snackbar;

import static com.berrysdu.cloudstall.cardSlide.CardSlidePanel.VANISH_TYPE_LEFT;
import static com.berrysdu.cloudstall.cardSlide.CardSlidePanel.VANISH_TYPE_RIGHT;


/**
 * 卡片Fragment
 *
 * @author xmuSistone
 */
@SuppressLint({"HandlerLeak", "NewApi", "InflateParams"})
public class CardFragment extends Fragment {

    private CardSlidePanel.CardSwitchListener cardSwitchListener;

    private String imagePaths[] = {"assets://wall01.jpg",
            "assets://wall02.jpg", "assets://wall03.jpg",
            "assets://wall04.jpg", "assets://wall05.jpg",
            "assets://wall06.jpg", "assets://wall07.jpg",
            "assets://wall08.jpg", "assets://wall09.jpg",
            "assets://wall10.jpg", "assets://wall11.jpg",
            "assets://wall12.jpg", "assets://wall01.jpg",
            "assets://wall02.jpg", "assets://wall03.jpg",
            "assets://wall04.jpg", "assets://wall05.jpg",
            "assets://wall06.jpg", "assets://wall07.jpg",
            "assets://wall08.jpg", "assets://wall09.jpg",
            "assets://wall10.jpg", "assets://wall11.jpg", "assets://wall12.jpg"}; // 24个图片资源名称

    private String names[] = {"宝宝学说话 词汇扩展爆款", "鲜花篮 特卖", "草莓甜筒上新", "Curology洁面霜", "《太空旅客去月球》", "实木三件套 家具", "牛油果套餐",
            "霍建华儿童励志书全10套", "七爷捞面", "布织沙发坐垫 灰色爆款", "果木碳烤皇尊牛排", "粉刷 30块钱5个", "宝宝学说话 词汇扩展爆款", "鲜花篮 特卖", "草莓甜筒上新", "Curology洁面霜", "《太空旅客去月球》", "实木三件套 家具", "牛油果套餐",
            "霍建华儿童励志书全10套", "七爷捞面", "布织沙发坐垫 灰色爆款", "果木碳烤皇尊牛排", "粉刷 30块钱5个"}; // 24个人名

    private List<CardDataItem> dataList = new ArrayList<CardDataItem>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.card_layout, null);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        CardSlidePanel slidePanel = (CardSlidePanel) rootView
                .findViewById(R.id.image_slide_panel);
        cardSwitchListener = new CardSlidePanel.CardSwitchListener() {

            @Override
            public void onShow(int index) {
           // Toast.makeText (getContext(), "正在显示-" + dataList.get(index).userName,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCardVanish(int index, int type) {
                switch (type){
                    case VANISH_TYPE_LEFT:
                        Snackbar.make(getView(),"不感兴趣",Snackbar.LENGTH_SHORT).show();
                        break;
                    case VANISH_TYPE_RIGHT:
                        Snackbar.make(getView(),"已喜欢",Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onItemClick(View cardView, int index) {
              //  Toast.makeText (getContext(), "卡片点击-" + dataList.get(index).userName,Toast.LENGTH_SHORT).show();

            }
        };
        slidePanel.setCardSwitchListener(cardSwitchListener);

        prepareDataList();
        slidePanel.fillData(dataList);
    }

    private void prepareDataList() {
        int num = imagePaths.length;

        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < num; i++) {
                CardDataItem dataItem = new CardDataItem();
                dataItem.userName = names[i];
                dataItem.imagePath = imagePaths[i];
                dataItem.likeNum = (int) (Math.random() * 10);
                dataItem.imageNum = (int) (Math.random() * 6);
                dataList.add(dataItem);
            }
        }
    }

}
