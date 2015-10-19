package com.example.domoticapp.app.Modules.LightModule;

import com.philips.lighting.hue.sdk.utilities.impl.Color;
import com.philips.lighting.model.PHBridgeResourcesCache;

import java.io.Serializable;

/**
 * Created by milton on 27/07/15.
 */
public class LightState implements Serializable {

    private Light.Power Power = null;
    private Light.Alarm alarm = null;
    private Light.Schedule schedule = null;
    private float colorX;
    private float colorY;
    private int color;
    private int brightness;
    private boolean reacheable;

    public LightState(Light.Power Power) {
        this.Power = Power;
    }

    public LightState(Light.Alarm alarm) {
        this.alarm = alarm;
    }

    public LightState() {

    }

    public Light.Power getPower() {
        return Power;
    }

    public void setPower(Light.Power power) {
        this.Power = power;

    }

    public Light.Alarm getAlarm() {
        return alarm;
    }

    public void setColor(float colorX, float colorY)
    {
        this.colorX = colorX;
        this.colorY = colorY;
    }


    public float getColorX() {
        return colorX;
    }

    public float getColorY() {
        return colorY;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public void setAlarm(Light.Alarm alarm) {
        this.alarm = alarm;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;

        if (getClass() != o.getClass()) return false;

        final LightState comparedObject = (LightState) o;

        if (this.Power != comparedObject.Power) return false;

        if (this.alarm != comparedObject.alarm) return false;

        return true;
    }


    public void setReacheable(boolean reacheable) {
        this.reacheable = reacheable;
    }

    public boolean isReacheable() {
        return reacheable;
    }
}
