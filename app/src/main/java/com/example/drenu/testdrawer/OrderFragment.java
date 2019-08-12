package com.example.drenu.testdrawer;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OrderFragment extends android.app.Fragment {

    private SwipeMenuListView mListView;
    View v;
    public OrderFragment(){}
    public List<ParseObject> l;
    OrderAdapter adp;
    private static String URL_CANCEL="http://www.excellent18.com/ciMovieapp/book/cancel";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_order, container, false);
        mListView = (SwipeMenuListView) v.findViewById(R.id.listView);

        SwipeMenuCreator creator=new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {

                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getActivity());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(90);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);

            }
        };

        mListView.setMenuCreator(creator);
       ParseQuery<ParseObject> q=ParseQuery.getQuery("Order");
        q.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        q.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                    l=objects;
                    for(ParseObject o: objects)
                    {
                        Log.i("order",o.getString("date"));
                    }

                    adp=new OrderAdapter(getActivity(),l);
                    mListView.setAdapter(adp);
            }
        });

        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {

                switch(index)
                {
                    case 0:
                        Log.i("POS",l.get(position).getObjectId());

                        cancel(l.get(position));

                        l.get(position).deleteInBackground(new DeleteCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(e!=null)
                                    Log.i("deleted",e.toString());
                            }
                        });

                        l.remove(position);
                        adp.notifyDataSetChanged();
                        mListView.setAdapter(adp);
                        Toast.makeText(getActivity(), ""+position,Toast.LENGTH_SHORT).show();
                        break;
                }

                return false;
            }

        });

        return v;
    }

    private void cancel(final ParseObject p) {

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_CANCEL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("response",response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"ErrorResponse\n"+error, Toast.LENGTH_SHORT).show();
                Log.i("Volley: ",error.toString());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> mp=new HashMap<>();

                mp.put("theatre",p.getString("theatre").trim());
                mp.put("movie",p.getString("movie").trim());
                mp.put("user", ParseUser.getCurrentUser().getUsername().trim());
                mp.put("time",p.getString("time").trim());
                mp.put("date",p.getString("date").trim());
                mp.put("selectedseats",p.getString("seats").trim());

                return mp;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);



    }



}
