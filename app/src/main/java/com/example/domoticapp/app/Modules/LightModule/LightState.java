package com.example.domoticapp.app.Modules.LightModule;

import java.io.Serializable;

/**
 * Created by milton on 27/07/15.
 */
public class LightState implements Serializable {

    private Light.Power Power = null;
    private Light.Alarm alarm = null;
    private Light.Schedule schedule = null;
//
//    private LightModuleOps lightOps;
//
//    public LightState(LightModuleOps lightOps)
//    {
//        this.lightOps = lightOps;
//    }

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
//        lightOps.setOn();


    }

    public Light.Alarm getAlarm() {
        return alarm;
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
}
