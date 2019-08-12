package com.example.drenu.testdrawer;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;


public class HomeFragment extends android.app.Fragment {
    public HomeFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_home, container, false);
        GridView gridView = (GridView) v.findViewById(R.id.home_grid);

        // Instance of ImageAdapter Class
        gridView.setAdapter(new HomeAdapter(getActivity()));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(i==0)
                {
                    Intent in=new Intent(getActivity(),SelectCityActivity.class);
                    startActivity(in);
                }

                if(i==1)
                {
                    FragmentManager manager = getFragmentManager();
                    manager.popBackStack();
                    manager.beginTransaction().addToBackStack(null).commit();
                    manager.beginTransaction().replace(R.id.frame, new ProfileFragment()).commit();
                }
                if(i==2)
                {
                    FragmentManager manager = getFragmentManager();
                    manager.popBackStack();
                    manager.beginTransaction().addToBackStack(null).commit();
                    manager.beginTransaction().replace(R.id.frame, new QRFragment()).commit();
                }
                if(i==3)
                {
                    Intent login=new Intent(getActivity(),MapsActivity.class);
                    startActivity(login);

                }

            }
        });

        return v;
    }

}
