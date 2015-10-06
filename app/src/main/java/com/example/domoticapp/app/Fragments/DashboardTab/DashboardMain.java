package com.example.domoticapp.app.Fragments.DashboardTab;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.*;
import com.example.domoticapp.app.R;


public class DashboardMain extends Fragment {
    private View view;
    private ViewPager viewPager;

    public DashboardMain() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }
//
//        ScrollView scrollView = (ScrollView) view.findViewById(R.id.scrollView_Dashboard);
//        scrollView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                // Disallow the touch request for parent scroll on touch of child view
//                view.getParent().requestDisallowInterceptTouchEvent(true);
//                return true;
//            }
//        });


        return view;



    }

    @Override
    public void onDestroyView() {
        super.onDestroy();
    }
}
