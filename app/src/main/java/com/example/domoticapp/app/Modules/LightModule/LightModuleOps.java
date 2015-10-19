package com.example.domoticapp.app.Modules.LightModule;

import android.util.Log;
import com.example.domoticapp.app.Modules.BaseModulesOps;
import com.example.domoticapp.app.Util.LightListenerLoggin;
import com.philips.lighting.hue.listener.PHLightListener;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.utilities.impl.PHUtilitiesHelper;
import com.philips.lighting.model.*;

import java.util.*;

/**
 * Concrete class for the BaseModuleOps, define
 * all the operations in the light module
 */
public class LightModuleOps implements BaseModulesOps {

    private final String TAG = getClass().getSimpleName();
    private PHHueSDK phHueSDK = PHHueSDK.create();
    private PHBridge bridge = phHueSDK.getSelectedBridge();
    private PHBridgeResourcesCache cache = bridge.getResourceCache();
    private PHLightState state;

    //trying to above a null pointer exception
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

    public List<Light> getAllLights() {
        List<PHLight> phLights = cache.getAllLights();
        List<Light> lights = new ArrayList<Light>();
        for (PHLight light : phLights) {
            lights.add(new Light(light.getIdentifier(), getStateFromCache(light)));
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

    private LightState getStateFromCache(PHLight phLight) {
        return validateState(phLight);

    }

    public void setLightIdentifier(String targetId, String newId) {
        PHLight phLight = bridge.getResourceCache().getLights().get(targetId);
        phLight.setIdentifier(newId);

    }

    public LightState refreshStateFromCache() {
        PHLight phLight = cache.getLights().get(light.getId());
        LightState lightState = validateState(phLight);

        light.setState(lightState);

        return lightState;

    }


    private boolean isReacheable(PHLight light) {
        return light.getLastKnownLightState().isReachable();

    }

    //validade and get the current state from a light
    //stores the state in a LighState instance
    private LightState validateState(PHLight phLight) {
        LightState lightState = new LightState();
        PHLightState phLightState = phLight.getLastKnownLightState();

        if (isReacheable(phLight)) {
            String lightModel = phLight.getModelNumber();
            PHUtilitiesHelper phUtil = new PHUtilitiesHelper();
            int color;
            float xy[] = new float[]{
                    phLightState.getX(),
                    phLightState.getY()
            };

            color = phUtil.colorFromXY(xy, lightModel);

            if (phLightState.isOn())
                lightState.setPower(Light.Power.ON);
            else
                lightState.setPower(Light.Power.OFF);

            lightState.setColor(color);
            lightState.setReacheable(true);
            lightState.setBrightness(phLightState.getBrightness());

            phLight.getLastKnownLightState().validateState();

            return lightState;

        } else {
            lightState.setReacheable(false);

            return lightState;
        }


    }

    //Set light color with X and Y values converted
    // from the RGB representation
    public void setColor(float x, float y) {
        PHLight phLight = cache.getLights().get(light.getId());


        if (isReacheable(phLight)) {
            state = new PHLightState();
            state.setTransitionTime(0);
            state.setX(x);
            state.setY(y);


            Log.d(TAG, "inside setColor, light: " + light.getId());
            bridge.updateLightState(phLight, state);
        } else
            Log.d(TAG, "inside setColor light: " + light.getId() + "not reacheable");


    }

    //Set color from an hex representation
    public void setColor(int color) {
        //get light from cache
        PHLight phLight = cache.getLights().get(light.getId());
        if (isReacheable(phLight)) {
            //get light model to calculate the XY
            String lightModel = phLight.getModelNumber();
            Log.d(TAG, "Inside setColor: " + color);

            //get the utility class to perform the convertion
            PHUtilitiesHelper phUtil = new PHUtilitiesHelper();
            float xy[] = phUtil.calculateXY(color, lightModel);
            //obtain the X value from the xy[] array
            float x = xy[0];
            //obtain the Y value from the xy[] array
            float y = xy[1];
            //make a new state an pass in the xy values;
            state = new PHLightState();
//            state.setTransitionTime(0);
            state.setX(x);
            state.setY(y);
            //update the bridge
            bridge.updateLightState(phLight, state);

        } else
            Log.d(TAG, "inside setColor light: " + light.getId() + "not reacheable");

    }

    //Set the brightness of a light, its minimum brightness is 0
    //and its maximum brightness is 254
    public void setBrightness(int brightness) {
        PHLight phLight = cache.getLights().get(light.getId());

        if (isReacheable(phLight)) {
            state = new PHLightState();
            state.setTransitionTime(0);
            state.setBrightness(brightness);

            Log.d(TAG, "inside setBrihtness " + brightness);
            bridge.updateLightState(phLight, state);
        } else
            Log.d(TAG, "inside setBrightness light: " + light.getId() + "not reacheable");
    }

    public void setOn() {
        PHLight phLight = cache.getLights().get(light.getId());
        if (isReacheable(phLight)) {
            state = new PHLightState();
            state.setTransitionTime(0);
            state.setOn(true);

            Log.d(TAG, "inside setOn " + light.getId());
            bridge.updateLightState(phLight, state);
        } else
            Log.d(TAG, "inside setOn light: " + light.getId() + "not reacheable");
    }

    public void setOff() {
        PHLight phLight = cache.getLights().get(light.getId());

        if (isReacheable(phLight)) {
            state = new PHLightState();
            state.setOn(false);

            Log.d(TAG, "inside setOff " + light.getId());
            bridge.updateLightState(phLight, state);
        } else
            Log.d(TAG, "inside setOff light: " + light.getId() + "not reacheable");

    }

    public void heartBeatOff() {
        phHueSDK.disableAllHeartbeat();
    }

    public void heartBeatOn(int timeInterval) {
        phHueSDK.enableHeartbeat(bridge, timeInterval);
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
