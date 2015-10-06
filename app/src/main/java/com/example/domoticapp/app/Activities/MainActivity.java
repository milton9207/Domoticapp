package com.example.domoticapp.app.Activities;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.app.ActionBar.OnNavigationListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import com.example.domoticapp.app.Adapters.ViewPagerAdapter;
import com.example.domoticapp.app.Fragments.DashboardTab.DashboardMain;
import com.example.domoticapp.app.Fragments.PlaneTab.BaseModulesFragment;
import com.example.domoticapp.app.Fragments.PlaneTab.PlaneModeFragment;
import com.example.domoticapp.app.R;
import com.example.domoticapp.app.Util.LifecycleLoggingActivity;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.heartbeat.PHHeartbeatManager;
import com.philips.lighting.model.PHBridge;

import java.util.List;


public class MainActivity extends LifecycleLoggingActivity {


    private Toolbar toolbar;
    private DashboardMain fragmentDashboardMain;
    private ListView listView;
    private List list;
    private PlaneModeFragment planeModeFragment;

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


        //Mobile Mode
        if (!isInTwoPaneMode()) {
            fragmentDashboardMain = new DashboardMain();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_main, fragmentDashboardMain)
                    .commit();

        }
        //Tablet Mode
        else {
            //Setting up the toolbar
            setupToolbar();

            //Setting up the tabs
            ViewPager viewPager = (ViewPager) findViewById(R.id.tabanim_viewpager);
            setupViewPager(viewPager);
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabanim_tabs);
            tabLayout.setupWithViewPager(viewPager);

        }
    }


    private void setupToolbar() {

        toolbar = (Toolbar) findViewById(R.id.tabanim_toolbar);
        setSupportActionBar(toolbar);


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        BaseModulesFragment baseModulesFragment = new BaseModulesFragment();
        adapter.addFrag(new DashboardMain(), "DASHBOARD");
        adapter.addFrag(baseModulesFragment, "PLANE");
//        adapter.addFrag(new BaseModulesFragment(), "MOUSE");
        viewPager.setAdapter(adapter);

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
}
