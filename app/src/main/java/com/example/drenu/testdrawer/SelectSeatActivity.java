package com.example.drenu.testdrawer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.provider.LiveFolders;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.parse.Parse;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectSeatActivity extends AppCompatActivity  {


    private static String URL_BOOK="http://www.excellent18.com/ciMovieapp/book";
    private static String URL_GET="http://www.excellent18.com/ciMovieapp/book/send";
    private int total;
    public Button button;
    public GridView gv;
    private ArrayList<Integer> selectedSeats;
    private ArrayList<Integer> bookedseats=new ArrayList<Integer>();
    ArrayAdapter adp;
    TextView tvs;
    List<Integer> l = new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_seat);

        total=0;
        selectedSeats = new ArrayList<>();

        for (int i = 0; i < 48; i++) {
            l.add(i);
        }
   //     Log.i("BookedSeats1:",bookedseats.toString());


        button = (Button) findViewById(R.id.seat);
        tvs = (TextView) findViewById(R.id.selected);

        gv = (GridView) findViewById(R.id.grid);
        gv.setNumColumns(6);
        getbookingdata();


        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),i+"",Toast.LENGTH_LONG).show();
                if(bookedseats.contains(i))
                {

                //    view.setBackgroundColor(Color.rgb(255,100,100));
                return;

                }
                else if(view.getDrawingCacheBackgroundColor()!=Color.rgb(255,100,100)) {
                    view.setDrawingCacheBackgroundColor(Color.rgb(255,100,100));
                    view.setBackgroundColor(Color.rgb(255,100,100));
                    selectedSeats.add(i);
                    total=total+70;
                    Log.i("Arr:",selectedSeats.toString());
                    tvs.setText(selectedSeats.toString());
                }
                else
                {
                    view.setDrawingCacheBackgroundColor(Color.BLACK);
                    view.setBackgroundColor(Color.BLACK);
                    selectedSeats.remove(new Integer(i));
                    total-=70;
                    Log.i("Arr:",selectedSeats.toString());
                    tvs.setText(selectedSeats.toString());

                }

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SelectSeatActivity.this,DetailsActivity.class);
                i.putExtra("time",getIntent().getExtras().getString("time"));
                JSONArray tosend=new JSONArray();
                for (int j = 0; j < selectedSeats.size(); j++) {
                    tosend.put(selectedSeats.get(j));
                }

                Log.i("SeatstoJSON",tosend.toString());
                i.putExtra("date",getIntent().getExtras().getString("date"));
                i.putExtra("selectedseats",tosend.toString());
                i.putExtra("seatarr",selectedSeats);

                i.putExtra("total",total);

                startActivity(i);
            }
        });
    }

    private void getbookingdata() {

        bookedseats=new ArrayList<>();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_GET,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try
                        {
                            JSONObject obj=new JSONObject(response);
                            JSONArray arr=obj.getJSONArray("success");


                            for(int i=0;i<arr.length();i++) {

                                bookedseats.add(arr.getInt(i));

                            }

                            adp = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, l){

                                @NonNull
                                @Override
                                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                                    View view = super.getView(position, convertView, parent);


                                    int color = 0x00FFFFFF; // Transparent
                                    if (bookedseats.contains(position)) {
                                        color = 0xFF0000FF; // Opaque Blue
                                    }

                                    view.setBackgroundColor(color);

                                    return view;
                                }
                            };


                            gv.setAdapter(adp);


                            // bs=bookedseats;

                            Toast.makeText(SelectSeatActivity.this,bookedseats.toString(),
                                    Toast.LENGTH_SHORT).show();



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SelectSeatActivity.this,"Error! Try again"+e.toString(),Toast.LENGTH_SHORT).show();

                            Log.i("Err:",e.getMessage());

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SelectSeatActivity.this,"ErrorResponse\n"+error, Toast.LENGTH_SHORT).show();
                Log.i("Volley: ",error.toString());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                SharedPreferences sharedPreferences=getSharedPreferences("com.example.drenu.testdrawer", Context.MODE_PRIVATE);
                String theatre=sharedPreferences.getString("cinemaslug","").trim();
                String movie=sharedPreferences.getString("movieslug","").trim();
                String time=getIntent().getExtras().getString("time");
                String date=getIntent().getExtras().getString("date");
                Map<String,String> mp=new HashMap<>();
                mp.put("theatre",theatre);
                mp.put("movie",movie);
                mp.put("user", ParseUser.getCurrentUser().getUsername().trim());
                mp.put("time",time);
                mp.put("date",date);

                Log.i("In volley:",bookedseats.toString());
                Log.i("In volley date:",date);

                return mp;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }



}
