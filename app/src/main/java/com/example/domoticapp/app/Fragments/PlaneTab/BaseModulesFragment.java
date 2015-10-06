package com.example.domoticapp.app.Fragments.PlaneTab;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.*;
import android.widget.ListView;
import android.widget.RadioButton;
import com.example.domoticapp.app.Adapters.SideBarPlaneAdapter;
import com.example.domoticapp.app.Modules.LightModule.LightState;
import com.example.domoticapp.app.Modules.LightModule.Panel;
import com.example.domoticapp.app.R;
import com.example.domoticapp.app.Modules.LightModule.Light;
import com.example.domoticapp.app.Util.LifecycleLogginFragment;
import com.example.domoticapp.app.Util.ObserverInterface.LightCacheUpdateListener;
import com.example.domoticapp.app.Util.ObserverInterface.MyChangeEvent;
import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHMessageType;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.hue.sdk.heartbeat.PHHeartbeatManager;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHHueParsingError;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by milton on 14/07/15.
 */
public class BaseModulesFragment extends LifecycleLogginFragment {

    private final int HEARTBEAT_TIME = 5000;

    private LightModuleAbstractFragment planeModeFragment;

    // Use CopyOnWriteArrayList to avoid ConcurrentModificationExceptions if a
    // listener attempts to remove itself during event notification.
    private final CopyOnWriteArrayList<LightCacheUpdateListener> listeners =
            new CopyOnWriteArrayList<LightCacheUpdateListener>();
//    private final List<LightCacheUpdateListener> listeners = new ArrayList<LightCacheUpdateListener>();

    private final String TAG = getClass().getSimpleName();
    private SideBarPlaneAdapter adapter;
    private View view;


    private PHHueSDK phHueSDK = PHHueSDK.create();
    private PHBridge bridge = phHueSDK.getSelectedBridge();
    private PHHeartbeatManager heartbeatManager = PHHeartbeatManager.getInstance();


    @Override
    public void onResume() {
        super.onResume();

        phHueSDK.getNotificationManager().registerSDKListener(phsdkListener);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.base_modules_fragment, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.side_Bar_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        List<Integer> list = new ArrayList<Integer>();
        list.add(R.drawable.ic_action);
        list.add(R.drawable.ic_action);

        adapter = new SideBarPlaneAdapter(getActivity(), list);
        recyclerView.setAdapter(adapter);




        return view;
    }

    public void manageFragmentTransaction()
    {

        planeModeFragment = (PlaneModeFragment) getFragmentManager()
                .findFragmentById(R.id.dummyfrag_bg);
        if(planeModeFragment == null)
        {
            planeModeFragment = FragmentManager
                    .newLightModuleFragment(FragmentManager.PLANE_LAYOUT);

        }

        removeAllListeners();
        addMyChangeListener(planeModeFragment);
        FragmentTransaction tr = getFragmentManager().beginTransaction();
        tr.replace(R.id.modules_fragment_container, planeModeFragment);
        tr.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        tr.commit();

    }

    public void manageFragmentTransaction2()
    {


        planeModeFragment = (LightModuleAbstractFragment) getFragmentManager()
                .findFragmentById(R.id.card_layout);
        if(planeModeFragment == null)
        {
            planeModeFragment = FragmentManager
                    .newLightModuleFragment(FragmentManager.CARD_LAYOUT);

        }

        removeAllListeners();
        addMyChangeListener(planeModeFragment);
        FragmentTransaction tr = getFragmentManager().beginTransaction();
        tr.replace(R.id.modules_fragment_container, planeModeFragment);
        tr.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        tr.commit();

    }

    @Override
    public void onStop() {
        super.onStop();

        if (phsdkListener != null) {
            phHueSDK.getNotificationManager().unregisterSDKListener(phsdkListener);
            Log.d(TAG, "PHSDKListener not null");

        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_settings_layer1:
                manageFragmentTransaction2();
                return true;
            case R.id.action_settings_layer2:
                manageFragmentTransaction();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addMyChangeListener(LightCacheUpdateListener l) {
        listeners.add(l);
    }

    public void removeMyChangeListener(LightCacheUpdateListener l) {
        listeners.remove(l);
    }

    public void removeAllListeners()
    {
        listeners.clear();
    }

    // Event firing method.  Called internally by other class methods.
    protected void fireChangeEvent() {
        Log.d(TAG,"Inside fireChangeEvent " +listeners.size());


        MyChangeEvent evt = new MyChangeEvent(this,phHueSDK,bridge,heartbeatManager);

        for (LightCacheUpdateListener l : listeners) {
            l.onLightCacheUpdated(evt);
//            Log.d(TAG,l.toString());
        }
    }

    private PHSDKListener phsdkListener = new PHSDKListener() {
        @Override
        public void onCacheUpdated(List<Integer> list, final PHBridge phBridge) {
            Log.d(TAG, "onCacheUpdated");

            if (list.contains(PHMessageType.LIGHTS_CACHE_UPDATED)) {
                Log.d(TAG, "contains light cache update");

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        fireChangeEvent();
                    }
                });

            }
        }

        @Override
        public void onBridgeConnected(PHBridge phBridge, String s) {

        }

        @Override
        public void onAuthenticationRequired(PHAccessPoint phAccessPoint) {

        }

        @Override
        public void onAccessPointsFound(List<PHAccessPoint> list) {

        }

        @Override
        public void onError(int i, String s) {

        }

        @Override
        public void onConnectionResumed(PHBridge phBridge) {

        }

        @Override
        public void onConnectionLost(PHAccessPoint phAccessPoint) {

        }

        @Override
        public void onParsingErrors(List<PHHueParsingError> list) {

        }
    };
}
