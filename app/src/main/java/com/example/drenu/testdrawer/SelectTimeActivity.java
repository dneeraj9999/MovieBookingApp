package com.example.drenu.testdrawer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SelectTimeActivity extends AppCompatActivity {

    private RadioGroup radioTime;
    private RadioButton radioButton;
    private Button next;
    private static String URL_GET="http://www.excellent18.com/ciMovieapp/book/send";
    private ArrayList<Integer> bookedseats=new ArrayList<Integer>();
    private ArrayList<Integer> bs=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_time);

        radioTime=(RadioGroup)findViewById(R.id.radioGroup);
        next=(Button)findViewById(R.id.next);


       next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  getbookingdata();
                Log.i("booked time",bookedseats.toString());
                int selectedId=radioTime.getCheckedRadioButtonId();
                radioButton=(RadioButton)findViewById(selectedId);
                Toast.makeText(SelectTimeActivity.this,radioButton.getText(),Toast.LENGTH_SHORT).show();
                Intent i=new Intent(SelectTimeActivity.this,SelectSeatActivity.class);
                i.putExtra("time",radioButton.getText());
                startActivity(i);

            }
        });
    }
/*
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

                           // bs=bookedseats;

                            Toast.makeText(SelectTimeActivity.this,bookedseats.toString(),
                                    Toast.LENGTH_SHORT).show();



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SelectTimeActivity.this,"Error! Try again"+e.toString(),Toast.LENGTH_SHORT).show();

                            Log.i("Err:",e.getMessage());

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SelectTimeActivity.this,"ErrorResponse\n"+error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("Volley: ",error.toString());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                SharedPreferences sharedPreferences=getSharedPreferences("com.example.drenu.testdrawer", Context.MODE_PRIVATE);
                String theatre=sharedPreferences.getString("cinemaslug","").trim();
                String movie=sharedPreferences.getString("movieslug","").trim();
                Map<String,String> mp=new HashMap<>();
                mp.put("theatre",theatre);
                mp.put("movie",movie);
                mp.put("user", ParseUser.getCurrentUser().getUsername().trim());
                mp.put("time",radioButton.getText().toString());

                Log.i("In volley:",bookedseats.toString());

                return mp;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }
*/




 }

