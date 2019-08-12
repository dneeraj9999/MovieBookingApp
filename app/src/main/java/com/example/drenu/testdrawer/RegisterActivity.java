package com.example.drenu.testdrawer;


import android.app.VoiceInteractor;
import android.content.Intent;
import android.opengl.Visibility;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText fname,lname,username,email,pass,cpass;
    private Button btn_reg;
    private ProgressBar loading;
    private static String URL_REG="http://192.168.43.175/ci1/users/register";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fname=(EditText)findViewById(R.id.fname);
        lname=(EditText)findViewById(R.id.lname);
        username=(EditText)findViewById(R.id.uname);
        email=(EditText)findViewById(R.id.mail);
        pass=(EditText)findViewById(R.id.pass);
        cpass=(EditText)findViewById(R.id.confpass);
        loading=(ProgressBar)findViewById(R.id.loading);
        btn_reg=(Button)findViewById(R.id.register);

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });


        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    private void register()
    {

        loading.setVisibility(View.VISIBLE);
        btn_reg.setVisibility(View.GONE);

        final String fname=this.fname.getText().toString().trim();
        final String lname=this.lname.getText().toString().trim();
        final String username=this.username.getText().toString().trim();
        final String email=this.email.getText().toString().trim();
        final String password=this.pass.getText().toString().trim();
        final String cpass=this.cpass.getText().toString().trim();

     /*   StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_REG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try
                        {
                            Toast.makeText(RegisterActivity.this,response,Toast.LENGTH_SHORT).show();

                            JSONObject obj=new JSONObject(response);
                            // Toast.makeText(RegisterActivity.this,"Test!",Toast.LENGTH_SHORT).show();

                            String success=obj.getString("success");

                            //Toast.makeText(RegisterActivity.this,"Test!",Toast.LENGTH_SHORT).show();


                            if(success.equals("1"))
                            {
                                Toast.makeText(RegisterActivity.this,"Register Success!",Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(RegisterActivity.this,MainActivity.class);
                                startActivity(i);
                            }
                            else
                            {
                                loading.setVisibility(View.GONE);
                                btn_reg.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this,"Register Error! Try again"+e.toString(),Toast.LENGTH_SHORT).show();

                            Log.i("Err:",e.getMessage());

                            loading.setVisibility(View.GONE);
                            btn_reg.setVisibility(View.VISIBLE);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this,"Register ErrorResp"+error,Toast.LENGTH_SHORT).show();
                loading.setVisibility(View.GONE);
                btn_reg.setVisibility(View.VISIBLE);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> mp=new HashMap<>();
                mp.put("fname",fname);
                mp.put("lname",lname);
                mp.put("username",username);
                mp.put("email",email);
                mp.put("pass",pass);
                mp.put("cpass",cpass);

                return mp;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

     */

        final ParseUser user=new ParseUser();
        if(username.matches("")||password.matches("")||email.matches(""))
        {
            Toast.makeText(this, "username,password,email are required", Toast.LENGTH_LONG).show();
            loading.setVisibility(View.GONE);
            btn_reg.setVisibility(View.VISIBLE);
        }

        else {
            ParseACL defaultACL=new ParseACL();

            defaultACL.setPublicWriteAccess(true);
            defaultACL.setPublicReadAccess(true);
            ParseACL.setDefaultACL(defaultACL,true);


            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.put("firstname",fname);
            user.put("lastname",lname);
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {

                    if (e == null) {
                        Toast.makeText(RegisterActivity.this, "Sign Up successful", Toast.LENGTH_SHORT).show();
                        ParseObject acc=new ParseObject("Account");

                        acc.put("id",user.getObjectId());
                        acc.put("balance",""+300);

                        acc.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(e!=null)
                                Log.i("saved",e.toString());
                            }
                        });


                        Intent i=new Intent(RegisterActivity.this,MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this, "Sign Up failed"+"\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        btn_reg.setVisibility(View.VISIBLE);
                    }

                }
            });

        }
    }


}