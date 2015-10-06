package com.example.domoticapp.app.Util.ObserverInterface;

import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.heartbeat.PHHeartbeatManager;
import com.philips.lighting.model.PHBridge;

import java.util.EventObject;

/**
 * Created by milton on 1/10/15.
 *
 *
 */
public class MyChangeEvent extends EventObject {
    // This event definition is stateless but you could always
    // add other information here.
    private PHHueSDK phHueSDK;
    private PHBridge bridge;
    private PHHeartbeatManager hManager;

    public MyChangeEvent(Object source, PHHueSDK phHueSDK, PHBridge bridge, PHHeartbeatManager hManager) {
        super(source);
        this.phHueSDK = phHueSDK;
        this.bridge = bridge;
        this.hManager = hManager;
    }

    public PHBridge getBridge() {
        return bridge;
    }

    public PHHeartbeatManager gethManager() {
        return hManager;
    }

    public PHHueSDK getPhHueSDK() {
        return phHueSDK;
    }
}