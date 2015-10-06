package com.example.domoticapp.app.Util;

import android.util.Log;
import com.example.domoticapp.app.Modules.LightModule.Light;
import com.example.domoticapp.app.Modules.LightModule.LightState;

import java.util.ArrayList;

/**
 * Created by milton on 26/07/15.
 *
 * @brief: Helper class that includes utility methods.
 */
public class Util{


//    public static ArrayList<LightState> lightConfigurationParser(ArrayList<String> rawData)
//    {
//        ArrayList<LightState> data = new ArrayList<LightState>();
//
//        for(String rawItem: rawData)
//        {
//            String[] split = rawItem.split("\\s+");
//            data.add(new LightState(Integer.parseInt(split[2]),getStateFromString(split[3])));
//        }
//
//        return data;
//    }
//
//    public static String lightConfigurationParserToString(LightState config)
//    {
//        String data = new String("L");
//
//        data += " " + config.getId();
//        data += " " + config.getPower().toString();
//        Log.i("Util",data);
//
//        return data;
//    }
//
//    private static Light.Power getStateFromString(String string)
//    {
//        if(string.equals("ON"))
//        {
//            return Light.Power.ON;
//        }
//        else if(string.equals("OFF"))
//        {
//            return Light.Power.OFF;
//        }
//
//        return null;
//    }
}
