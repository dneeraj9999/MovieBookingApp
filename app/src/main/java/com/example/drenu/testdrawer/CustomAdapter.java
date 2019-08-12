package com.example.drenu.testdrawer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drenu.testdrawer.model.CinemaCollection;

import java.util.Calendar;

/**
 * Created by drenu on 9/8/2018.
 */

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private CinemaCollection cinemaCollection;
    private Button a;
    private Button b;
    private Button c;
    private Button d;
    private TextView tv;
    private TextView tv1;

    public CustomAdapter(Context context, CinemaCollection cinemaCollection)
    {
        this.cinemaCollection=cinemaCollection;
        this.context=context;
    }

    @Override
    public int getCount() {
        return cinemaCollection.cinemas.length;
    }

    @Override
    public Object getItem(int i) {
        return cinemaCollection.cinemas[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        @SuppressLint("ViewHolder")
        View v= LayoutInflater.from(context).inflate(R.layout.listview_row,null);



        tv=v.findViewById(R.id.theatre_name);
        tv.setText(cinemaCollection.cinemas[i].name);

        tv1=v.findViewById(R.id.slug);
        tv1.setText(cinemaCollection.cinemas[i].slug);




        return v;
    }
}
