package com.berrysdu.cloudstall.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.berrysdu.cloudstall.R;
import com.berrysdu.cloudstall.adapter.NotificationAdapter;
import com.berrysdu.cloudstall.objects.Position;
import com.berrysdu.cloudstall.objects.Stall;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationCenterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationCenterFragment extends ShopFragment {


    View fragView;

    public NotificationCenterFragment() {
        // Required empty public constructor
    }


    public static NotificationCenterFragment newInstance(String param1, String param2) {
        NotificationCenterFragment fragment = new NotificationCenterFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        fragView = inflater.inflate(R.layout.fragment_notification_center, container, false);
        super.initView(fragView,new NotificationAdapter(getContext(),new Stall(1,new Position(12,12),1,"1"),getResources()));

        return fragView;
    }
}