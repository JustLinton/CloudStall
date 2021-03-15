package com.berrysdu.cloudstall.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.berrysdu.cloudstall.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class PreferenceFrag extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_my);
    }

}