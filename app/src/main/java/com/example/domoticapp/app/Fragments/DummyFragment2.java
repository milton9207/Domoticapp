package com.example.domoticapp.app.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.example.domoticapp.app.Adapters.SimpleRecyclerAdapter;
import com.example.domoticapp.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by milton on 14/07/15.
 */
public class DummyFragment2 extends Fragment {

    int color;



    SimpleRecyclerAdapter adapter;
    public DummyFragment2() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dummy_fragment2, container, false);



//        final FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.dummyfrag_bg2);
//
//        frameLayout.setBackgroundColor(color);
//
//        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.dummyfrag_scrollableview);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setHasFixedSize(true);
//
//        List<String> list = new ArrayList<String>();
//
//        for (int i = 0; i < 1; i++) {
//            list.add("datos " + i);
//        }
//
////        adapter = new SimpleRecyclerAdapter(getActivity(),list);
//
////        recyclerView.setAdapter(adapter);

        return view;
    }
}
