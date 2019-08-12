package com.example.drenu.testdrawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.drenu.testdrawer.model.MovieCollection;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by drenu on 10/14/2018.
 */

public class OrderAdapter extends BaseAdapter{

    Context context;
    List<ParseObject> p;

    public OrderAdapter(Context context, List<ParseObject> p)
    {
        this.context=context;
        this.p=p;
    }

    @Override
    public int getCount() {
        return p.size();
    }

    @Override
    public Object getItem(int i) {
        return p.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        View view;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.order_row, null, false);


        }else{
            view = convertView;
        }


        //view= LayoutInflater.from(context).inflate(R.layout.order_row,null);

        TextView tv=(TextView) view.findViewById(R.id.oid);
        tv.setText("Order Id: "+p.get(i).getObjectId());

        TextView tv1=(TextView) view.findViewById(R.id.theatre);
        tv1.setText("Theatre: "+p.get(i).getString("theatre"));

        TextView tv2=(TextView) view.findViewById(R.id.date);

        tv2.setText("Date: "+p.get(i).getString("date"));

        TextView tv3=(TextView) view.findViewById(R.id.seats);
        tv3.setText("Seat Nos: "+p.get(i).getString("seats"));


        return view;
    }
}
