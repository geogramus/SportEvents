package com.example.geogr.sportevents;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.example.geogr.sportevents.api.Controller;
import com.vk.sdk.VKSdk;

/**
 * Created by geogr on 20.11.2017.
 */

public class SelectCityFragment extends android.support.v4.app.Fragment {
   Context context;
   static int cityindex;
    public SelectCityFragment(Context context) {
        this.context=context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.townselect_fragment, container, false);

        v.findViewById(R.id.Moscow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Controller.setBaaseUrl("http://moscow.getsandbox.com/");
               cityindex=0;
               continuework();
            }

        });
        v.findViewById(R.id.saintPetersburg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Controller.setBaaseUrl("http://saintp.getsandbox.com/");
                cityindex=1;
                continuework();
            }

        });
        v.findViewById(R.id.otherCity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Controller.setBaaseUrl("http://other.getsandbox.com/");
                cityindex=2;
                continuework();
            }
        });


        return v;
    }

    private void continuework() {
        android.support.v4.app.FragmentManager fragmentManager=getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, new SportEventsListFragment())
                .commitAllowingStateLoss();
    }
}
