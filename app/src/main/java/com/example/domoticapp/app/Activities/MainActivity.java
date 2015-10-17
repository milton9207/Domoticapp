package com.example.domoticapp.app.Activities;

import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import com.example.domoticapp.app.Fragments.PlaneTab.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import com.example.domoticapp.app.Adapters.SideBarPlaneAdapter;
import com.example.domoticapp.app.Fragments.DashboardTab.DashboardMain;
import com.example.domoticapp.app.Fragments.PlaneTab.BaseModulesFragment;
import com.example.domoticapp.app.Fragments.PlaneTab.PlaneModeFragment;
import com.example.domoticapp.app.Fragments.WelcomeFragment;
import com.example.domoticapp.app.R;
import com.example.domoticapp.app.Util.LifecycleLoggingActivity;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.heartbeat.PHHeartbeatManager;
import com.philips.lighting.model.PHBridge;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends LifecycleLoggingActivity implements SideBarPlaneAdapter.SideBarClickListener{


    private Toolbar toolbar;
    private DashboardMain fragmentDashboardMain;
    private ListView listView;
    private List list;
    private PlaneModeFragment planeModeFragment;
    private SideBarPlaneAdapter adapter;

    private Fragment fragment;


    private PHHueSDK phHueSDK = PHHueSDK.create();
    private PHBridge bridge = phHueSDK.getSelectedBridge();
    private PHHeartbeatManager heartbeatManager = PHHeartbeatManager.getInstance();

    @Override
    protected void onResume() {
        super.onResume();

        heartbeatManager.enableFullConfigHeartbeat(bridge, 5000);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Log.d(TAG,isInTwoPaneMode() + "");
        //Mobile Mode
        if (!isInTwoPaneMode()) {
//            fragmentDashboardMain = new DashboardMain();
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.fragment_container_main, fragmentDashboardMain)
//                    .commit();

        }
        //Tablet Mode
        else {

            Log.d(TAG,"inside tablet Mode");
            //Setting up the toolbar
            setupToolbar();
            //Setting up side bar
            setupSideBar();
            if(savedInstanceState !=null)
            {
                return;
            }

            defaultFragment(WelcomeFragment.WELCOME_FRAGMENT_TAG);

        }
    }


    private void setupToolbar() {

        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);


    }

    private void setupSideBar()
    {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.side_Bar_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        List<Integer> list = new ArrayList<Integer>();
        list.add(R.drawable.ic_action);
        list.add(R.drawable.ic_action);

        adapter = new SideBarPlaneAdapter(this, list,this);
        recyclerView.setAdapter(adapter);
    }

    private void defaultFragment(String type)
    {
        manageFragmentTransaction(type);
    }

    private void manageFragmentTransaction(String type)
    {
        if(type.equals(WelcomeFragment.WELCOME_FRAGMENT_TAG))
        {
            fragment = WelcomeFragment.makeInstance();
        }
        else if(type.equals(BaseModulesFragment.MODULE_LIGHT_FRAGMENT_TAG))
        {
            fragment = BaseModulesFragment.makeInstance(FragmentManager.CARD_LAYOUT);
        }

        if(fragment!=null)
        {
            Log.d(TAG,"Fragment created " + type);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.modules_fragment_container,fragment);

            ft.commit();

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;

            case R.id.action_select_layer:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bridge != null) {
            if (phHueSDK.isHeartbeatEnabled(bridge))
                heartbeatManager.disableAllHeartbeats(bridge);

            phHueSDK.disconnect(bridge);
        }
    }

    public boolean isInTwoPaneMode() {
        return findViewById(R.id.fragment_container_main) == null;
    }

    @Override
    public void onMenuItemClicked(int position) {

        switch (position)
        {
            case 0:
                manageFragmentTransaction(WelcomeFragment.WELCOME_FRAGMENT_TAG);
                break;
            case 1:
                manageFragmentTransaction(BaseModulesFragment.MODULE_LIGHT_FRAGMENT_TAG);
                break;
        }

    }
}
