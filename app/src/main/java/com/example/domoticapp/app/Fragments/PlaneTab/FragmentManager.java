package com.example.domoticapp.app.Fragments.PlaneTab;


/**
 * Created by milton on 1/10/15.
 */
public class FragmentManager {

    public static int LIGHT_MODULE_FRAGMENT = 0;
    public static int SOUND_MODULE_FRAGMENT = 1;
    public static final int PLANE_LAYOUT = 2;
    public static final int CARD_LAYOUT = 3;


    public static LightModuleAbstractFragment newLightModuleFragment(int type)
    {
        switch (type)
        {
            case PLANE_LAYOUT:
                return new PlaneModeFragment();
            case CARD_LAYOUT:
                return new CardModuleFragment();
            default:
                return null;
        }

    }




}
