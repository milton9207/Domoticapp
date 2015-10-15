package com.example.domoticapp.app.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import com.example.domoticapp.app.Modules.LightModule.Light;
import com.example.domoticapp.app.Modules.LightModule.Panel;
import com.example.domoticapp.app.R;
import com.example.domoticapp.app.Util.GradientView;

/**
 * Created by milton on 14/10/15.
 */
public class ColorPickerFragment extends DialogFragment {

    public static final String PANEL_BUNDLE_TAG = "panel";
    private final String TAG = getClass().getSimpleName();

    private GradientView gradientView;
    private SeekBar seekBar;
    private View view;
    private Panel mPanel;

    public static ColorPickerFragment newInstance(Panel panel)
    {
        ColorPickerFragment colorPickerFragment = new ColorPickerFragment();
        Bundle args = new Bundle();
        args.putSerializable(PANEL_BUNDLE_TAG,panel);
        colorPickerFragment.setArguments(args);

        return colorPickerFragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.dialog_picker_fragment,container,false);
        gradientView = (GradientView) view.findViewById(R.id.top_gradient);
        seekBar = (SeekBar) view.findViewById(R.id.bright_seekbar);
        mPanel = (Panel) getArguments().getSerializable(PANEL_BUNDLE_TAG);

        if(mPanel!=null)
        {
            Light currentLight = mPanel.getCurrentLight();
            mPanel.setLight(currentLight);

            mPanel.setGradientView(gradientView);
            mPanel.setBrightSeekBar(seekBar);
            mPanel.updateBright();
            mPanel.setupViewsListeners();
        }

        getDialog().setTitle("Seleccion el color");
        return view;
    }
}
