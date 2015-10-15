package com.example.domoticapp.app.Util;

import android.graphics.Color;
import android.util.Log;
import com.example.domoticapp.app.Modules.LightModule.Light;
import com.example.domoticapp.app.Modules.LightModule.LightState;
import com.philips.lighting.hue.sdk.utilities.impl.Point;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by milton on 26/07/15.
 *
 * @brief: Helper class that includes utility methods.
 */
public class Util {

    Color color;

    public Color getColor() {
        return color;
    }

    public static int hex2Rgb(String colorStr) {
        String color;

        int r = Integer.valueOf(colorStr.substring(1, 3), 16);
        int g = Integer.valueOf(colorStr.substring(3, 5), 16);
        int b = Integer.valueOf(colorStr.substring(5, 7), 16);

        color = Integer.toString(r) + Integer.toString(g) + Integer.toString(b);


        return Integer.parseInt(color);
    }

    public double[] calculateXY(int color) {
        // Get the RGB values from your color object
        float rawRed = Color.red(color);
        float rawBlue = Color.blue(color);
        float rawGreen = Color.green(color);

        //convert them to be between 0 and 1. So the RGB color (255, 0, 100) becomes (1.0, 0.0, 0.39)
        float fRed = rawRed / 255;
        float fBlue = rawBlue / 255;
        float fGreen = rawGreen / 255;

        //Apply a gamma correction to the RGB values
        double red = (fRed > 0.04045f) ? Math.pow((fRed + 0.055f) / (1.0f + 0.055f), 2.4f) : (fRed / 12.92f);
        double green = (fGreen > 0.04045f) ? Math.pow((fGreen + 0.055f) / (1.0f + 0.055f), 2.4f) : (fGreen / 12.92f);
        double blue = (fBlue > 0.04045f) ? Math.pow((fBlue + 0.055f) / (1.0f + 0.055f), 2.4f) : (fBlue / 12.92f);

        //Convert the RGB values to XYZ using the Wide RGB D65 conversion formula
        double X = red * 0.664511f + green * 0.154324f + blue * 0.162028f;
        double Y = red * 0.283881f + green * 0.668433f + blue * 0.047685f;
        double Z = red * 0.000088f + green * 0.072310f + blue * 0.986039f;

        //Calculate the xy values from the XYZ values

        double x = X / (X + Y + Z);
        double y = Y / (X + Y + Z);

        return new double[]{x, y};
    }



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
