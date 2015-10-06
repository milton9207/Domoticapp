package com.example.domoticapp.app.Fragments.DashboardTab;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.example.domoticapp.app.Adapters.SimpleRecyclerAdapter;
import com.example.domoticapp.app.Modules.LightModule.Light;
import com.example.domoticapp.app.Modules.LightModule.LightModuleOps;
import com.example.domoticapp.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Section1 extends Fragment {

    private View view;
    private SimpleRecyclerAdapter adapter;
    private Toolbar toolbar;


    private FragmentActivity myContext;
    private Activity activityCompatContext;

    private LightModuleOps lightOps;


    public Section1() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        myContext = (FragmentActivity) activity;
        activityCompatContext = activity;
        super.onAttach(activity);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_dashboard_section1, container, false);
//        lightOps = new LightModuleOps();

        populate();
        setUpToolbar(view);

        return view;
    }

    public void setUpToolbar(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);


    }

    public void populate() {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.dummyfrag_scrollableview2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);



        List<String> list = new ArrayList<String>();

        for (int i = 0; i < 20; i++) {
            list.add("datos" + i);
        }

//        List<Light> lights = lightOps.getAllLights();
//
//        adapter = new SimpleRecyclerAdapter(getActivity(), lights);
//
//        recyclerView.setAdapter(adapter);
//
//        lightOps.setLightIdentifier("2","Cocina");
//
//        adapter.addItem(lightOps.getLight("2"));
//        adapter.notifyDataSetChanged();


        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                int action = e.getAction();
                switch (action) {
                    case MotionEvent.ACTION_MOVE:
                        Log.i("populate", "aqui dentro");
                        rv.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                    default:
                        Log.i("populate", "aqui dentro2222");

                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }


        });
    }


}
