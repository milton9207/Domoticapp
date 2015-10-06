package com.example.domoticapp.app.Fragments.DashboardTab;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.domoticapp.app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Section3 extends Fragment {

    private Toolbar toolbar;


    public Section3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard_section3,container,false);

        setUpToolbar(view);

        return view;
    }


    public void setUpToolbar(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);


    }


}
