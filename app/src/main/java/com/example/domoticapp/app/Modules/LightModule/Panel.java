package com.example.domoticapp.app.Modules.LightModule;

import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.SeekBar;
import com.example.domoticapp.app.Fragments.PlaneTab.FragmentLayoutManager;
import com.example.domoticapp.app.R;
import com.example.domoticapp.app.Util.GradientView;

import java.io.Serializable;

/**
 * Created by milton on 18/07/15.
 *
 * @brief: class that works as a mediator for one to many realations
 * between views, enabling or disabling some of them and also coupling
 * the light operations.
 * <p/>
 * Another way to get this aproach (without mediator) is to add an observer
 * to each component then make a chain reaction betwen them
 */
public class Panel implements Serializable {


    private final String TAG = getClass().getSimpleName();

    private SwitchCompat onOffswitch;
    private View viewGroup;
    private RadioButton rButton15;
    private RadioButton rButton30;
    private RadioButton rButton2hrs;
    private Button doneButton;
    private GradientView gradientView;
    private volatile SeekBar brightSeekBar;
    private volatile Button colorPickerButton;


    private volatile LightState state;
    private volatile Light light;
    private int TYPE;
    public volatile boolean isLocalPanel = true;
    public volatile long time;


    public Panel(View viewGroup) {

        this.viewGroup = viewGroup;
        onOffswitch = (SwitchCompat) viewGroup.findViewById(R.id.OnOffSwitch);
        colorPickerButton = (Button) viewGroup.findViewById(R.id.picker_color_button);
        gradientView = (GradientView) viewGroup.findViewById(R.id.top_gradient);

        //trying to above a null pointer exception
        //in the worse case is better to get a generic light insted of crash
        setLight(new Light("generic", new LightState()));

    }

    public Panel(View viewGroup, int type) {

        this.viewGroup = viewGroup;
        onOffswitch = (SwitchCompat) viewGroup.findViewById(R.id.OnOffSwitch);
        colorPickerButton = (Button) viewGroup.findViewById(R.id.picker_color_button);
        gradientView = (GradientView) viewGroup.findViewById(R.id.top_gradient);
        brightSeekBar = (SeekBar) viewGroup.findViewById(R.id.bright_seekbar);


        this.TYPE = type;
        //trying to above a null pointer exception
        //in the worse case is better to get a generic light insted of crash
        setLight(new Light("generic", new LightState()));

    }

    public void setOnOffswitch(SwitchCompat onOffswitch) {
        this.onOffswitch = onOffswitch;
    }

    public void setColorPickerButton(Button colorPickerButton) {
        this.colorPickerButton = colorPickerButton;
    }

    public void setGradientView(GradientView gradientView) {
        this.gradientView = gradientView;
    }

    public void setBrightSeekBar(SeekBar brightSeekBar) {
        this.brightSeekBar = brightSeekBar;
    }

    public Light getCurrentLight() {
        if (light == null)
            return new Light("generic");
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


    }

    public void enablePanelState() {
        onOffswitch.setEnabled(true);

    }

    private void disablePanelState()
    {
        onOffswitch.setEnabled(false);
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

    public void updateColor() {
//        isLocalPanel = true;
        if(state.isReacheable())
        {
            isLocalPanel= true;
            colorPickerButton.setBackgroundColor(state.getColor());

        }
        else
        {
            disablePanelState();
            Log.d(TAG,"Light: " + light.getId() + "not reacheable");
        }
//        gradientView.updatePointerPosition();
        //IMPORTANTE!
        //Cambiar posicion del puntero pero no el color
//        gradientView.setColor(state.getColor());

    }

    public void updateBright()
    {
        if(state.isReacheable())
        {
            isLocalPanel = true;
            brightSeekBar.setProgress(state.getBrightness());
            Log.d(TAG,"updateBRight bright: " + state.getBrightness());
        }
        else
        {
            disablePanelState();
            Log.d(TAG,"Light: " + light.getId() + "not reacheable");
        }
    }

    public void update() {

        if(state.isReacheable())
        {
            enablePanelState();
            isLocalPanel = true;

            if (state.getPower() == Light.Power.ON) {

                Log.d(TAG, "inside update POWER == ON " + light.getId() + "color: " + state.getColor());

//            switchOn();
                onOffswitch.setChecked(true);
//            colorPickerButton.setBackgroundColor(state.getColor());
//            gradientView.setColor(state.getColor());
//            gradientView.updatePointerPosition();
//            rButton15.setChecked(false);

            } else if (state.getPower() == Light.Power.OFF) {
                Log.d(TAG, "inside update POWER == OFF " + light.getId());
//            switchOff();
                onOffswitch.setChecked(false);
            }

        }
        else
        {
            disablePanelState();
            Log.d(TAG,"Light: " + light.getId() + "not reacheable");
        }


    }

    public void setupViewsListeners() {
        Log.d(TAG, "inside setupViewsListeners: is local panel: " + isLocalPanel + " fragment type layout: " + TYPE);
//
        if (TYPE == FragmentLayoutManager.PLANE_LAYOUT) {

            onOffswitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (onOffswitch.isChecked()) {
                        Log.d(TAG, "inside onClickListener (checked) ON/OFF");
//                    switchOn();
//                    onOffswitch.toggle();
                        state.setPower(Light.Power.ON);
//                        state.setColor(x,y)
                        light.updatePowerState(state, light);


                    } else {
                        Log.d(TAG, "inside onClickListener (unchecked) ON/OFF");
//                    switchOff();
//                    onOffswitch.toggle();
                        state.setPower(Light.Power.OFF);
                        light.updatePowerState(state, light);

                    }
                    isLocalPanel = true;
                }
            });

            gradientView.setOnColorChangedListener(new GradientView.OnColorChangedListener() {
                @Override
                public void onColorChanged(GradientView view, int color) {


                    Log.d(TAG, "inside onColorChanged onColorChanged: color" + color);
                    state.setColor(color);
                    colorPickerButton.setBackgroundColor(color);
                    light.updateColorState(state, light);
                    isLocalPanel = true;
                    time = System.currentTimeMillis();
                }
            });

        } else if (TYPE == FragmentLayoutManager.CARD_LAYOUT) {

            if (isLocalPanel) {
                onOffswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            Log.d(TAG, "inside onCheckerChanged (checked) ON/OFF light:" + light.toString());
//                    switchOn();
                            state.setPower(Light.Power.ON);
                            light.updatePowerState(state, light);

                        } else if (!isChecked) {
                            Log.d(TAG, "inside onCheckerChanged(unchecked) ON/OFF light:" + light.toString());
//                    switchOff();
                            state.setPower(Light.Power.OFF);
                            light.updatePowerState(state, light);
                        }
                    }
                });

                try {
                    gradientView.setOnColorChangedListener(new GradientView.OnColorChangedListener() {
                        @Override
                        public void onColorChanged(GradientView view, int color) {

                            Log.d(TAG, "inside onColorChanged: color" + color);
                            state.setColor(color);
                            colorPickerButton.setBackgroundColor(color);
                            light.updateColorState(state, light);
                        }
                    });

                    brightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            Log.d(TAG, "inside onSeekBarChange bright: " + progress);
                            state.setBrightness(progress);
                            light.updateBrighState(state, light);
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    });
                } catch (NullPointerException e) {

                }




            }
            isLocalPanel = false;
        }

    }


}
