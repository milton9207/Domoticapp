package com.example.domoticapp.app.Fragments.PlaneTab;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import com.example.domoticapp.app.Modules.LightModule.Light;
import com.example.domoticapp.app.Modules.LightModule.Panel;
import com.example.domoticapp.app.R;


/**
 * Created by milton on 30/09/15.
 */
public class PlaneModeFragment extends LightModuleAbstractFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        mPanel = new Panel(view);
        mPanel.defaultPanelState();


        RadioButton rButton1 = (RadioButton) view.findViewById(R.id.light_item_icon);
        rButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Log.d(TAG, "Iniside light1 rbutton" + ligh1.getId());

                mPanel.setLight(Light.getInstance("1"));
                mPanel.update();
                mPanel.setupViewsListeners();
            }
        });

        RadioButton rButton2 = (RadioButton) view.findViewById(R.id.radio_light2);
        rButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Log.d(TAG,"Inside Light2 rbutton" + light2.getId());
                mPanel.setLight(Light.getInstance("3"));
                mPanel.update();
                mPanel.setupViewsListeners();

            }
        });

        return view;

    }

    @Override
    protected void manageCacheUpdate() {
        // if the update isnt from the local panel(app instance from device) then dont resend the
        // update command
        if (mPanel != null && !mPanel.isLocalPanel && mPanel.getCurrentLight() != null) {
            //getting the actual light from the panel(actual light that is focused)for reference
            final Light tempLight = mPanel.getCurrentLight();
            //refreshing light state from cache
            tempLight.refreshState(tempLight);
            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    mPanel.setLight(tempLight);
                    mPanel.update();
                    Log.d(TAG, "inside onCache runOnUi... " + tempLight.getId());

                }
            });

        }
        //if the update is from the local panel
        // wait 2 secs (sleep) then reactivate the heartbeat
        if (mPanel != null && mPanel.isLocalPanel) {
//            try {
//                //in case of overlaping ocurre increase sleep seconds
//                Thread.sleep(500);
//                if (!phHueSDK.isHeartbeatEnabled(bridge)) {
//                    manager.enableFullConfigHeartbeat(bridge, HEARTBEAT_TIME);
//                    Log.d(TAG, "heartBeat on");
//                }
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            if (!phHueSDK.isHeartbeatEnabled(bridge)) {
                manager.enableFullConfigHeartbeat(bridge, HEARTBEAT_TIME);
                Log.d(TAG, "heartBeat on");
            }
            mPanel.isLocalPanel = false;
            Log.d(TAG, "Outside if");
        }

    }

    @Override
    protected View createView(LayoutInflater inflater,ViewGroup container) {

        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppTheme_Base_Dark);
        // clone the inflater using the ContextThemeWrapper
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        // inflate the layout using the cloned inflater, not default inflater
        view = localInflater.inflate(R.layout.plane, container, false);

        return view;
    }

}
