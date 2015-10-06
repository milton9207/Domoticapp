package com.example.domoticapp.app.Modules.LightModule;

import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import com.example.domoticapp.app.R;

import java.util.Observable;

/**
 * Created by milton on 18/07/15.
 *
 * @brief: class that works as a mediator for one to many realations
 * between views, enabling or disabling some of them and also coupling
 * the light operations.
 *
 * Another way to get this aproach (without mediator) is to add an observer
 * to each component then make a chain reaction betwen them
 */
public class Panel {



    private final String TAG = getClass().getSimpleName();

    private SwitchCompat onOffswitch;
    private View viewGroup;
    private RadioButton rButton15;
    private RadioButton rButton30;
    private RadioButton rButton2hrs;
    private Button doneButton;


    private LightState state;
    private Light light;
    public boolean isLocalPanel = true;


    public Panel(View viewGroup) {

        this.viewGroup = viewGroup;
        onOffswitch = (SwitchCompat) viewGroup.findViewById(R.id.OnOffSwitch);
//        rButton15 = (RadioButton) viewGroup.findViewById(R.id.radio_15min);
//        rButton30 = (RadioButton) viewGroup.findViewById(R.id.radio_30min);
//        rButton2hrs = (RadioButton) viewGroup.findViewById(R.id.radio_2hrs);
//        doneButton = (Button) viewGroup.findViewById(R.id.buttonPanel);
        //trying to above a null pointer exception
        //in the worse case is better to get a generic light insted of crash
        setLight(new Light("generic",new LightState()));

    }

    public void setOnOffswitch(SwitchCompat onOffswitch) {
        this.onOffswitch = onOffswitch;
    }

    public Light getCurrentLight()
    {
        if(light== null)
            return new Light("1");
        else
            return this.light;
    }

    public void setLight(Light light) {
        this.light = light;
        this.state = light.getState();
    }

    public void defaultPanelState() {

        onOffswitch.setChecked(false);
        onOffswitch.setEnabled(false);
//        rButton15.setEnabled(false);
//        rButton30.setEnabled(false);
//        rButton2hrs.setEnabled(false);

    }

    public void enablePanelState() {
        onOffswitch.setEnabled(true);
//        rButton15.setEnabled(true);
//        rButton30.setEnabled(true);
//        rButton2hrs.setEnabled(true);
    }

    private void switchOn() {

        rButton15.setEnabled(false);
        rButton15.setChecked(false);

        rButton30.setEnabled(false);
        rButton30.setChecked(false);

        rButton2hrs.setEnabled(false);
        rButton2hrs.setChecked(false);
    }

    private void switchOff() {
        rButton15.setEnabled(true);
        rButton30.setEnabled(true);
        rButton2hrs.setEnabled(true);

    }

    public void update() {

        enablePanelState();

        if (state.getPower() == Light.Power.ON) {

            Log.d(TAG, "inside update POWER == ON " + light.getId());
//            switchOn();
            onOffswitch.setChecked(true);
//            rButton15.setChecked(false);

        }
        else if(state.getPower()== Light.Power.OFF)
        {
            Log.d(TAG, "inside update POWER == OFF " + light.getId());
//            switchOff();
            onOffswitch.setChecked(false);


        }

    }

    public void setupViewsListeners()
    {
        Log.d(TAG,"inside setupViewsListeners" + isLocalPanel + "");
//
        if(isLocalPanel)
        {
            onOffswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Log.d(TAG, "inside onCheckerChanged (checked) ON/OFF light:" + light.toString());
//                    switchOn();
                        state.setPower(Light.Power.ON);
                        light.updateState(state,light);

                    } else if (!isChecked)
                    {
                        Log.d(TAG, "inside onCheckerChanged(unchecked) ON/OFF light:" + light.toString());
//                    switchOff();
                        state.setPower(Light.Power.OFF);
                        light.updateState(state,light);
                    }
                }
            });
        }
        isLocalPanel = false;




//        onOffswitch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(onOffswitch.isChecked())
//                {
//                    Log.d(TAG, "inside onClickListener (checked) ON/OFF");
////                    switchOn();
////                    onOffswitch.toggle();
//                    state.setPower(Light.Power.ON);
//                    light.updateState(state,light);
//
//                }
//                else
//                {
//                    Log.d(TAG, "inside onClickListener (unchecked) ON/OFF");
////                    switchOff();
////                    onOffswitch.toggle();
//                    state.setPower(Light.Power.OFF);
//                    light.updateState(state,light);
//
//                }
//            }
//        });

//        rButton15.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                state.setAlarm(Light.Alarm.MIN15);
//            }
//        });
//
//        rButton30.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                state.setAlarm(Light.Alarm.MIN30);
//            }
//        });
//
//        rButton2hrs.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                state.setAlarm(Light.Alarm.HRS2);
//            }
//        });


    }



}
