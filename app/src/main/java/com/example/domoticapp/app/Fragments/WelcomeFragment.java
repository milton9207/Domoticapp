package com.example.domoticapp.app.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.domoticapp.app.R;

/**
 * Created by milton on 16/10/15.
 */
public class WelcomeFragment extends Fragment {

    public static final String WELCOME_FRAGMENT_TAG = "welcome fragment";

    public static WelcomeFragment makeInstance()
    {
        return new WelcomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View view = inflater.inflate(R.layout.welcome_twopanes,container,false);


        return view;
    }
}
