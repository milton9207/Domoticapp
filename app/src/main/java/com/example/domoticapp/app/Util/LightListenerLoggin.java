package com.example.domoticapp.app.Util;

import android.util.Log;
import android.widget.Toast;
import com.example.domoticapp.app.Activities.MyApplicationActivity;
import com.philips.lighting.hue.listener.PHLightListener;
import com.philips.lighting.model.PHBridgeResource;
import com.philips.lighting.model.PHHueError;
import com.philips.lighting.model.PHLight;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

/**
 * Created by milton on 24/09/15.
 */
public class LightListenerLoggin implements PHLightListener {

    private String TAG = getClass().getSimpleName();
    private WeakReference<MyApplicationActivity> mActivity;

    public LightListenerLoggin(MyApplicationActivity mActivity) {
        this.mActivity = new WeakReference<MyApplicationActivity>(mActivity);
    }

    public LightListenerLoggin()
    {

    }


    @Override
    public void onReceivingLightDetails(PHLight phLight) {
        Log.d(TAG, "onReceivingLightDetails");
    }

    @Override
    public void onReceivingLights(List<PHBridgeResource> list) {

        Log.d(TAG, "onReceivingLight");
    }

    @Override
    public void onSearchComplete() {

        Log.d(TAG, "onSearchComplete");
    }

    @Override
    public void onSuccess() {
        Log.d(TAG, "onSuccess");
//        Toast.makeText(mActivity.get().getApplicationContext(), "Succes!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(int i, String s) {

        Log.d(TAG, "onError " + s);
    }

    @Override
    public void onStateUpdate(Map<String, String> map, List<PHHueError> list) {
        Log.d(TAG, "onStateUpdate");
    }
}
