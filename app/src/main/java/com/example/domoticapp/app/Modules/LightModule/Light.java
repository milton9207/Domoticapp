package com.example.domoticapp.app.Modules.LightModule;

import android.util.Log;
import com.philips.lighting.model.PHBridgeResourcesCache;

import java.util.List;

/**
 * Created by milton on 17/07/15.
 * Class that works as a facade for all the Light operations,
 * you can set/get any state (schedule,color,brightnes,etc) with
 * an intuitive interface
 */
public class Light {

    private final String TAG = getClass().getSimpleName();
    private static Light returnLight;

    //helper class that actually manage all the light related operations
    private static LightModuleOps lightOps = new LightModuleOps();

    public enum Power {
        ON, OFF
    }

    public enum Alarm {
        NONE, MIN15, MIN30, HRS2, CUSTOM
    }

    public enum Schedule {
        CUSTOM
    }

    private LightState state;
    private String id;

    public Light(String id) {

        this.id = id;
    }
    public Light() {


    }

    public Light(String id, LightState state) {
        this.id = id;
        this.state = state;
    }

    public Light(LightState state) {
        this.state = state;
    }

    // Factory method that returns a Light instance with
    // pre-configured Lightstate just by knowing the phisycal
    // light bulb id
    public static Light getInstance(String id) {
        returnLight = lightOps.getLight(id);

        return returnLight;
    }

    public List<Light> getAllLights()
    {
        return lightOps.getAllLights();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setState(LightState state) {
        this.state = state;
    }

    public LightState refreshState(Light light) {

        lightOps.setLight(light);
        return lightOps.refreshStateFromCache();
    }

    public LightState getState() {
        return state;
    }

    public boolean updateState(LightState state,Light light) {
//        lightOps.heartBeatOff();
        lightOps.setLight(light);

//        Log.d(TAG, "heartBeat off light:" + returnLight.toString());
        switch (state.getPower()) {
            case ON:
                lightOps.setOn();
                return true;
//                break;
            case OFF:
                lightOps.setOff();
                return true;
//                break;
            default:
                return false;

        }

    }

    public String toString()
    {
        return this.id;
    }
}
