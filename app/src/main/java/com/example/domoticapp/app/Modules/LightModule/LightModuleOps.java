package com.example.domoticapp.app.Modules.LightModule;

import android.util.Log;
import com.example.domoticapp.app.Modules.BaseModulesOps;
import com.example.domoticapp.app.Util.LightListenerLoggin;
import com.philips.lighting.hue.listener.PHLightListener;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.model.*;

import java.util.*;

/**
 * Concrete class for the BaseModuleOps, define the
 * all the operations in the light module
 */
public class LightModuleOps implements BaseModulesOps, Observer{

    private final String TAG = getClass().getSimpleName();
    private  PHHueSDK phHueSDK = PHHueSDK.create();
    private  PHBridge bridge = phHueSDK.getSelectedBridge();
    private  PHBridgeResourcesCache cache = bridge.getResourceCache();
    private PHLightState state;

    private Light light = new Light("1");

    public LightModuleOps(Light light) {
        this.light = light;
    }
    public LightModuleOps() {

    }

    @Override
    public void sendData() {

    }

    @Override
    public void receiveData() {

    }

    public List<Light> getAllLights()
    {
        List<PHLight> phLights = cache.getAllLights();
        List<Light> lights = new ArrayList<Light>();
        for(PHLight light : phLights)
        {
            lights.add(new Light(light.getIdentifier(),getStateFromCache(light)));
        }

        return lights;
    }

    public void setLight(Light light) {
        this.light = light;
    }

    public Light getLight(String id) {

        PHLight phLight = cache.getLights().get(id);

        light = new Light(phLight.getIdentifier(), getStateFromCache(phLight));

        return light;
    }

    private LightState getStateFromCache(PHLight phLight)
    {
        LightState lightState = new LightState();
        if (phLight.getLastKnownLightState().isOn())
            lightState.setPower(Light.Power.ON);
        else
            lightState.setPower(Light.Power.OFF);
        //        Log.d(TAG,phLight.toString());

        return lightState;

    }

    public void setLightIdentifier(String targetId, String newId) {
        PHLight phLight = bridge.getResourceCache().getLights().get(targetId);
        phLight.setIdentifier(newId);

    }

    public LightState refreshStateFromCache()
    {
        LightState lightState = new LightState();
        PHLight phLight = cache.getLights().get(light.getId());

        Log.d(TAG,"refreshStateFromCache " + phLight.getIdentifier());

        if (phLight.getLastKnownLightState().isOn())
            lightState.setPower(Light.Power.ON);
        else
            lightState.setPower(Light.Power.OFF);

        light.setState(lightState);

        return lightState;

    }

    public void setOn() {
        state = new PHLightState();
        state.setTransitionTime(0);
        state.setOn(true);

        Log.d(TAG,"inside setOn " + light.getId());
        PHLight phLight = cache.getLights().get(light.getId());
        bridge.updateLightState(phLight,state);
    }

    public void setOff()
    {
        state = new PHLightState();
        state.setOn(false);

        Log.d(TAG, "inside setOff " + light.getId());
        PHLight phLight = cache.getLights().get(light.getId());
        bridge.updateLightState(phLight,state);
    }

    public void heartBeatOff()
    {
        phHueSDK.disableAllHeartbeat();
    }

    public void heartBeatOn(int timeInterval)
    {
        phHueSDK.enableHeartbeat(bridge,timeInterval);
    }

    @Override
    public void update(Observable observable, Object data) {


    }

    class lightListener implements PHLightListener {

        @Override
        public void onReceivingLightDetails(PHLight phLight) {

        }

        @Override
        public void onReceivingLights(List<PHBridgeResource> list) {

        }

        @Override
        public void onSearchComplete() {

        }

        @Override
        public void onSuccess() {

        }

        @Override
        public void onError(int i, String s) {

        }

        @Override
        public void onStateUpdate(Map<String, String> map, List<PHHueError> list) {

        }
    }
}
