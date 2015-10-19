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
import com.example.domoticapp.app.Modules.LightModule.LightState;
import com.example.domoticapp.app.Modules.LightModule.Panel;
import com.example.domoticapp.app.R;

import java.util.Timer;


/**
 * Created by milton on 30/09/15.
 */
public class PlaneModeFragment extends LightModuleAbstractFragment {

    private volatile LightState lightState = new LightState();
    private Timer timer = new Timer();
    private volatile int tempColor;

    long t = System.currentTimeMillis();
    long end;

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        mPanel = new Panel(view, TYPE);
        mPanel.defaultPanelState();

        timer.schedule(new TimerTask(), 0L, 5000);


        RadioButton rButton1 = (RadioButton) view.findViewById(R.id.light_item_icon);
        rButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Log.d(TAG, "Iniside light1 rbutton" + ligh1.getId());

                mPanel.setLight(Light.getInstance("1"));
                mPanel.update();
                mPanel.updateColor();
                mPanel.setupViewsListeners();
                mPanel.isLocalPanel = true;
            }
        });

        RadioButton rButton2 = (RadioButton) view.findViewById(R.id.radio_light2);
        rButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Log.d(TAG,"Inside Light2 rbutton" + light2.getId());
                mPanel.setLight(Light.getInstance("3"));
                mPanel.update();
                mPanel.updateColor();
                mPanel.setupViewsListeners();
                mPanel.isLocalPanel = true;

            }
        });

        return view;

    }


    class TimerTask extends java.util.TimerTask {

        @Override
        public void run() {

            Light tempLight = mPanel.getCurrentLight();

            if (!tempLight.getId().equals("generic")) {
                lightState = tempLight.refreshState(tempLight);
                tempColor = lightState.getColor();
                mPanel.setLight(tempLight);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mPanel.update();
                        Log.d(TAG, "inside TimerTask runOnUi... ");
                    }
                });
            }

        }

    }

    @Override
    protected void manageCacheUpdate() {


        //get current light
        final Light tempLight = mPanel.getCurrentLight();
        lightState = tempLight.refreshState(tempLight);

        //currentcolor is always updated with the actual color
        //of the lamp
        int currentColor = lightState.getColor();


        //get the time that we need keep updating the
        //temporal color (tempColor)
        end = mPanel.time + 5000;
        //when the current time is > than the end the tempcolor
        //dont keep updating
        if (System.currentTimeMillis() < end) {

            tempColor = lightState.getColor();

        }

        //Debug porpouse
        boolean igual = currentColor==tempColor;
        Log.i(TAG, "current color " + currentColor + " temp color: " + tempColor + " " + igual);

        //if the color is diferent from the one that was keep(tempColor), it means
        // that we didnt change the color from this device
        if (tempColor != currentColor) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mPanel.setLight(tempLight);
                    mPanel.updateColor();
                    Log.d(TAG, "inside onCache runOnUi... ");
                }
            });

        }

    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container) {

        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppTheme_Base_Dark);
        // clone the inflater using the ContextThemeWrapper
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        // inflate the layout using the cloned inflater, not default inflater
        view = localInflater.inflate(R.layout.plane, container, false);

        return view;
    }

    @Override
    public void setLayoutType() {
        TYPE = FragmentLayoutManager.PLANE_LAYOUT;
    }

}
