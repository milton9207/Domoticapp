package com.example.domoticapp.app.Fragments.PlaneTab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.domoticapp.app.Modules.LightModule.Light;
import com.example.domoticapp.app.Modules.LightModule.Panel;
import com.example.domoticapp.app.R;
import com.example.domoticapp.app.Util.LifecycleLogginFragment;
import com.example.domoticapp.app.Util.ObserverInterface.LightCacheUpdateListener;
import com.example.domoticapp.app.Util.ObserverInterface.MyChangeEvent;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.heartbeat.PHHeartbeatManager;
import com.philips.lighting.model.PHBridge;

import java.util.Observable;

/**
 * Created by milton on 1/10/15.
 */
public abstract class LightModuleAbstractFragment extends LifecycleLogginFragment implements LightCacheUpdateListener{


    protected final String TAG = getClass().getSimpleName();
    public static final int  PLANE_LAYOUT = 0;
    public static final int LIST_LAYOUT = 1;

    protected final int HEARTBEAT_TIME = 5000;


    protected Panel mPanel;
    protected View view;

    protected PHHueSDK phHueSDK;
    protected PHBridge bridge;
    protected PHHeartbeatManager manager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);


        view = createView(inflater,container);
//        mPanel = new Panel(view);

        return view;
    }

    //template method
    protected abstract View createView(LayoutInflater inflater, ViewGroup container);

    public void setLayoutType(int type){

    }

    protected abstract void manageCacheUpdate();

    @Override
    public void onLightCacheUpdated(MyChangeEvent event) {
        Log.i(TAG, "onLightCacheUpdated HEHE SI JALA!" );

        phHueSDK = event.getPhHueSDK();
        bridge = event.getBridge();
        manager = event.gethManager();

        manageCacheUpdate();

    }
}
