package com.example.domoticapp.app.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.domoticapp.app.Fragments.PlaneTab.FragmentLayoutManager;
import com.example.domoticapp.app.Modules.LightModule.Light;
import com.example.domoticapp.app.Modules.LightModule.Panel;
import com.example.domoticapp.app.R;
import com.example.domoticapp.app.Util.GradientView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by milton on 2/10/15.
 */
public class LightListRecyclerAdapter extends RecyclerView.Adapter<LightListRecyclerAdapter.MyViewHolheder>{

    private final String TAG = getClass().getSimpleName();
    private List<Light> lightsData;
    private LayoutInflater inflater;
    private WeakReference<Panel> mPanel;
    private List<Panel> mPanels;
    private View view;

    private final int type = FragmentLayoutManager.CARD_LAYOUT;
    public volatile boolean isLocal = true;
    private static RecyclerViewClickListener listener;

    public interface RecyclerViewClickListener
    {

        void recyclerViewListClicked(View v, int position,boolean isLocal);
        void switchListClickerd(View v, int position, SwitchCompat switchCompat);
        void colorPickerButtonListClicked(View v, int position, Panel panel);
    }

    public LightListRecyclerAdapter(Context context,List<Light> lights,Panel mPanel,RecyclerViewClickListener listener)
    {
        this.lightsData = lights;
        this.listener = listener;
        inflater = LayoutInflater.from(context);

//        this.mPanel = mPanel;
        mPanels = new ArrayList<Panel>(lightsData.size());
        Log.i(TAG,lightsData.size() + "");
    }

    private void initializePanels(List<Panel> panels, View view)
    {
        for(int i = 0; i<= lightsData.size(); i++)
        {
            mPanels.add(new Panel(view,type));
        }
    }

    @Override
    public MyViewHolheder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG,"onCreateViewHolder");

        view = inflater.inflate(R.layout.light_recycler_row,parent,false);
        mPanel = new WeakReference<Panel>(new Panel(view));
        initializePanels(mPanels,view);

        return new MyViewHolheder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolheder holder, int position) {


        Log.i(TAG, "OnBindViewHolder" + position + "  " + mPanels.size());


        final Light item = lightsData.get(position);

        holder.title.setText(item.getId());

        Panel panel = mPanels.get(position);
        panel.setOnOffswitch(holder.onOfSwitch);
        panel.setColorPickerButton(holder.colorPickerButton);
//        panel.setGradientView(holder.gradientView);
        panel.setLight(item);
        panel.isLocalPanel = isLocal;
        panel.setupViewsListeners();
        panel.update();
        panel.updateColor();
//        isLocal = true;




    }

    public List<Panel> getmPanels() {
        return mPanels;
    }

    public void removeAllItems()
    {
        lightsData.clear();
        mPanels.clear();
    }

    public void addListItems(List<Light> lights)
    {
        this.lightsData = lights;
        mPanels = new ArrayList<Panel>(lightsData.size());
        if(view!= null)
            initializePanels(mPanels,view);
    }
    @Override
    public int getItemCount() {
        return lightsData.size();
    }

    public class MyViewHolheder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;
        SwitchCompat onOfSwitch;
        Button colorPickerButton;
        GradientView gradientView;


        public MyViewHolheder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            title = (TextView) itemView.findViewById(R.id.light_item_title);
            onOfSwitch = (SwitchCompat) itemView.findViewById(R.id.OnOffSwitch);
            onOfSwitch.setOnClickListener(this);
            colorPickerButton = (Button) itemView.findViewById(R.id.picker_color_button);
            colorPickerButton.setOnClickListener(this);
            gradientView = (GradientView) itemView.findViewById(R.id.top_gradient);
            Log.d(getClass().getSimpleName(),"MyviewHolder constructor");
        }

        @Override
        public void onClick(View v) {
            Log.d(getClass().getSimpleName(), "List item number: " + getPosition() + "current Light reference " + mPanels.get(getPosition()).getCurrentLight().toString());


            switch (v.getId())
            {
                case R.layout.light_recycler_row:

                    break;
                case R.id.OnOffSwitch:
                    mPanels.get(getPosition()).isLocalPanel = true;
                    listener.recyclerViewListClicked(v, this.getPosition(),true);
                    break;
                case R.id.picker_color_button:
                    listener.colorPickerButtonListClicked(v,this.getPosition(),mPanels.get(getPosition()));
                    break;

            }

        }
    }
}
