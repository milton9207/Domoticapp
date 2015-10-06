package com.example.domoticapp.app.Fragments.PlaneTab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.domoticapp.app.Adapters.LightListRecyclerAdapter;
import com.example.domoticapp.app.Modules.LightModule.Light;
import com.example.domoticapp.app.Modules.LightModule.Panel;
import com.example.domoticapp.app.R;
import com.example.domoticapp.app.Util.ObserverInterface.LightCacheUpdateListener;
import com.example.domoticapp.app.Util.ObserverInterface.MyChangeEvent;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by milton on 1/10/15.
 */
public class CardModuleFragment extends LightModuleAbstractFragment
        implements LightListRecyclerAdapter.RecyclerViewClickListener {

    LightListRecyclerAdapter adapter;
    List<Panel> mPanels = new ArrayList<Panel>();
    List<Light> lights = new ArrayList<Light>();
    boolean isLocal = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        lights = new Light().getAllLights();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.card_layout_recyclerview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));
//        recyclerView.setHasFixedSize(true);
        adapter = new LightListRecyclerAdapter(getActivity(), lights, mPanel, this);
        recyclerView.setAdapter(adapter);
        mPanels = adapter.getmPanels();


        return view;
    }

    @Override
    protected void manageCacheUpdate() {

        Log.d(TAG, "LOCAL + " + isLocal + " ADAPTer LOCAL +" + adapter.isLocal);
        if (!isLocal) {
            adapter.removeAllItems();
            adapter.addListItems(new Light().getAllLights());
            adapter.notifyDataSetChanged();
            Log.d(TAG, "isnt local " + isLocal + " " + adapter.isLocal);

        }

        else if(isLocal && adapter.isLocal) {
            Log.d(TAG,"is local");
        }

        isLocal = false;
        adapter.isLocal = false;

    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container) {

        return inflater.inflate(R.layout.card_layout, container, false);
    }

    @Override
    public void recyclerViewListClicked(View v, int position, boolean isLocal) {

        Log.i(TAG, position + "");
        //true
        this.isLocal = isLocal;
    }

    @Override
    public void switchListClickerd(View v, int position, SwitchCompat switchCompat) {

    }


}
