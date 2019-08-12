package com.example.drenu.testdrawer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseUser;


public class ProfileFragment extends android.app.Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.fragment_profile, container, false);

        TextView name=(TextView)v.findViewById(R.id.name);
        TextView username=(TextView)v.findViewById(R.id.username);
        TextView email=(TextView)v.findViewById(R.id.email);

        String s=ParseUser.getCurrentUser().getString("firstname")+" "+ParseUser.getCurrentUser().getString("lastname");
        name.setText(s);

        username.setText(ParseUser.getCurrentUser().getUsername());
        email.setText(ParseUser.getCurrentUser().getEmail());

        return v;
    }


}
