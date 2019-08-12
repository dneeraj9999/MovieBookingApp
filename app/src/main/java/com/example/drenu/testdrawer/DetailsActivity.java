package com.example.drenu.testdrawer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailsActivity extends AppCompatActivity {

    private static String URL_BOOK="http://www.excellent18.com/ciMovieapp/book";

    TextView tv;
    TextView tv1;
    TextView tv2;
    TextView tv3;
    TextView tv4;
    TextView tv5;
    private int balint;

    private ArrayList<Integer> selectedSeats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        SharedPreferences sharedPreferences=this.getSharedPreferences("com.example.drenu.testdrawer", Context.MODE_PRIVATE);

        String city=sharedPreferences.getString("city","");
        String cinema=sharedPreferences.getString("cinema","");
        String movie=sharedPreferences.getString("movie","");

        tv=(TextView)findViewById(R.id.details1);
        tv1=(TextView)findViewById(R.id.details2);
        tv2=(TextView)findViewById(R.id.details3);
        tv3=(TextView)findViewById(R.id.details4);
        tv4=(TextView)findViewById(R.id.seatnos);
        tv5=(TextView)findViewById(R.id.total);


        tv.setText(cinema);
        tv1.setText(movie);
        tv2.setText(getIntent().getExtras().getString("time"));
        tv3.setText(getIntent().getExtras().getString("date"));

        String tosend=getIntent().getExtras().getString("selectedseats");
        tv4.setText(tosend);
        final Integer tot=getIntent().getExtras().getInt("total");
        tv5.setText(tot.toString());

        Log.i("Details: ",city+cinema+movie);

        Bitmap bitmap = null;
        try {
            bitmap = TextToImageEncode(city+cinema+movie);

        } catch (WriterException e) {
            e.printStackTrace();
        }
        ImageView imageViewQrCode = (ImageView) findViewById(R.id.qrCode);
        imageViewQrCode.setImageBitmap(bitmap);

        Button confirm=(Button)findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] bal = new String[1];
                String oid=ParseUser.getCurrentUser().getObjectId();
                Log.i("oid",ParseUser.getCurrentUser().getObjectId());
                ParseQuery<ParseObject> q=ParseQuery.getQuery("Account");
                q.whereEqualTo("id",oid);
                q.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
          //              Log.i("qrbal:",e.toString());

                        if(e==null)
                        {
                            if(objects.size()==1)
                            {
                                for(ParseObject o : objects) {
                                    bal[0] =o.getString("balance");
                                    Log.i("balance in: ",bal[0]);
                                    balint=Integer.parseInt(bal[0]);
                                    Log.i("intofbal in",balint+"");
                                    if(balint >= tot)
                                        sendbookingdata();
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(),"Insufficient balance",Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    String newbal=(balint-tot)+"";
                                    o.put("balance",newbal);
                                    o.saveInBackground(new SaveCallback() {


                                        @Override
                                        public void done(ParseException e) {
                                            if(e!=null)
                                                Log.i("balsave",e.toString());
                                        }
                                    });

                                    ParseObject order=new ParseObject("Order");
                                    SharedPreferences sharedPreferences=getSharedPreferences("com.example.drenu.testdrawer", Context.MODE_PRIVATE);
                                    String theatre=sharedPreferences.getString("cinemaslug","").trim();
                                    String movie=sharedPreferences.getString("movieslug","").trim();

                                    order.put("username",ParseUser.getCurrentUser().getUsername());
                                    order.put("movie",movie);
                                    order.put("theatre",theatre);
                                    order.put("date",getIntent().getExtras().getString("date"));
                                    order.put("time",getIntent().getExtras().getString("time"));
                                    order.put("seats",getIntent().getExtras().getString("selectedseats"));
                                    order.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            if(e!=null)
                                                Log.i("OrderPlaced: ",e.toString());
                                        }
                                    });

                                    Toast.makeText(getApplicationContext(),"Booked Successfully",Toast.LENGTH_LONG).show();
                                    Intent in=new Intent(DetailsActivity.this,MainActivity.class);
                                    startActivity(in);

                                }
                            }
                            else {
                                Log.i("Size: ",objects.size()+"");

                            }
                        }
                        else
                        {
                            Log.i("qrbal:",e.toString());
                        }
                    }
                });

            }
        });


    }

    private Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    500, 500, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.colorBlack) : getResources().getColor(R.color.colorWhite);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    private void sendbookingdata() {

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_BOOK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try
                        {
                            Toast.makeText(DetailsActivity.this,response,Toast.LENGTH_SHORT).show();

                            JSONObject obj=new JSONObject(response);
                            // Toast.makeText(RegisterActivity.this,"Test!",Toast.LENGTH_SHORT).show();

                            String success=obj.getString("success");

                            //Toast.makeText(RegisterActivity.this,"Test!",Toast.LENGTH_SHORT).show();


                            if(success.equals("1"))
                            {
                                Toast.makeText(DetailsActivity.this,"Success!",Toast.LENGTH_SHORT).show();
                                // Intent i=new Intent(SelectSeatActivity.this,MainActivity.class);
                                // startActivity(i);
                            }
                            else
                            {
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DetailsActivity.this,"Error! Try again"+e.toString(),Toast.LENGTH_SHORT).show();

                            Log.i("Err:",e.getMessage());

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailsActivity.this,"ErrorResponse\n"+error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("Volley: ",error.toString());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String tosend=getIntent().getExtras().getString("selectedseats");
                Log.i("SeatstoJSON",tosend);

                SharedPreferences sharedPreferences=getSharedPreferences("com.example.drenu.testdrawer", Context.MODE_PRIVATE);
                String theatre=sharedPreferences.getString("cinemaslug","").trim();
                String movie=sharedPreferences.getString("movieslug","").trim();
                String time=getIntent().getExtras().getString("time").trim();
                String date=getIntent().getExtras().getString("date").trim();

                Map<String,String> mp=new HashMap<>();
                mp.put("theatre",theatre);
                mp.put("movie",movie);
                mp.put("user", ParseUser.getCurrentUser().getUsername().trim());
                mp.put("time",time);
                mp.put("date",date);
                mp.put("selectedseats",tosend);


                return mp;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }

}
