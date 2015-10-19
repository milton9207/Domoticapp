package com.example.domoticapp.app.Fragments.PlaneTab;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.domoticapp.app.Adapters.LightListRecyclerAdapter;
import com.example.domoticapp.app.Fragments.ColorPickerFragment;
import com.example.domoticapp.app.Modules.LightModule.Light;
import com.example.domoticapp.app.Modules.LightModule.Panel;
import com.example.domoticapp.app.R;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;


/**
 * Created by milton on 1/10/15.
 */
public class CardModuleFragment extends LightModuleAbstractFragment
        implements LightListRecyclerAdapter.RecyclerViewClickListener {



    private Timer timer = new Timer();

    private volatile LightListRecyclerAdapter adapter;
    private List<Panel> mPanels = new ArrayList<Panel>();
    private List<Light> lights = new ArrayList<Light>();
    private volatile boolean isLocal = true;

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        timer.cancel();
//    }

//    @Override
//    public void onStop() {
//        super.onStop();
//        timer.cancel();
//    }


    @Override
    public void onPause() {
        super.onPause();
        timer.cancel();
        Log.i(TAG,"timer task canceled");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        lights = new Light().getAllLights();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.card_layout_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
//        recyclerView.setHasFixedSize(true);
        adapter = new LightListRecyclerAdapter(getActivity(), lights, mPanel, this);
        recyclerView.setAdapter(adapter);
        mPanels = adapter.getmPanels();

        timer.schedule(new TimerTask(), 0L, 5000);



        return view;
    }

    class TimerTask extends java.util.TimerTask{

        @Override
        public void run() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    adapter.removeAllItems();
                    adapter.addListItems(new Light().getAllLights());
                    adapter.notifyDataSetChanged();
                    Log.d(TAG,"inside TimerTask runOnui...");
                }
            });

        }
    }

    @Override
    protected void manageCacheUpdate() {


//        Log.d(TAG, "LOCAL + " + isLocal + " ADAPTer LOCAL +" + adapter.isLocal);
//        if (!isLocal) {
////            getActivity().runOnUiThread(new Runnable() {
////                @Override
////                public void run() {
////
////                    adapter.removeAllItems();
////                    adapter.addListItems(new Light().getAllLights());
////                    adapter.notifyDataSetChanged();
////                }
////            });
//
//            Log.d(TAG, "isnt local " + isLocal + " " + adapter.isLocal);
//
//        }
//
//        else if(isLocal && adapter.isLocal) {
//            Log.d(TAG,"is local");
//        }
//
//        isLocal = false;
//        adapter.isLocal = false;

    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container) {

        return inflater.inflate(R.layout.card_layout, container, false);
    }

    @Override
    public void setLayoutType() {
        TYPE = FragmentLayoutManager.CARD_LAYOUT;
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

    @Override
    public void colorPickerButtonListClicked(View v, int position, Panel panel) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        Fragment prev = getFragmentManager().findFragmentByTag("frag");
        if (prev != null) {
            Log.d(TAG, "prev Fragment Picker " + prev.toString());
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        ColorPickerFragment pickerFragment = ColorPickerFragment.newInstance(panel);
        pickerFragment.show(ft, "frag");

    }


}
