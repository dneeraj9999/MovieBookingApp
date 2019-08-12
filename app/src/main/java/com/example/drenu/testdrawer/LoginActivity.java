package com.example.drenu.testdrawer;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private Button login;
    private EditText user,pass;
    private static String URL_LOGIN="http://192.168.43.175/ci1/users/login";
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login=(Button)findViewById(R.id.login);
        user=(EditText)findViewById(R.id.username);
        pass=(EditText)findViewById(R.id.password);
        progressBar=(ProgressBar)findViewById(R.id.Loginloading);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginFun();}
        });

    }
    private void loginFun() {

        progressBar.setVisibility(View.VISIBLE);
        login.setVisibility(View.GONE);

        final String username=this.user.getText().toString().trim();
        final String password=this.pass.getText().toString().trim();


        /*StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();
                            JSONObject jsonObject=new JSONObject(response);
                            Boolean loggedin=jsonObject.getBoolean("logged_in");
                            String loggedname=jsonObject.getString("username");
                            Toast.makeText(LoginActivity.this,loggedname,Toast.LENGTH_LONG).show();

                            if(loggedin)
                            {
                                Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_LONG).show();
                                Intent i=new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(i);
                                progressBar.setVisibility(View.GONE);
                                login.setVisibility(View.VISIBLE);

                            }
                            else
                            {
                                Toast.makeText(LoginActivity.this,"Login Failed",Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                login.setVisibility(View.VISIBLE);

                            }

                        } catch (JSONException e) {
                            Toast.makeText(LoginActivity.this,
                                    "JSONError"+e.getMessage(),
                                    Toast.LENGTH_LONG);
                            progressBar.setVisibility(View.GONE);
                            login.setVisibility(View.VISIBLE);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(LoginActivity.this,
                                "VolleyError"+error.getMessage(),
                                Toast.LENGTH_LONG);
                        progressBar.setVisibility(View.GONE);
                        login.setVisibility(View.VISIBLE);

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> hm=new HashMap<>();

                hm.put("username",username);
                hm.put("password",password);


                return hm;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
*/
       ParseUser.logInInBackground(username, password, new LogInCallback() {
           @Override
           public void done(ParseUser user, ParseException e) {
               if(user!=null && e==null)
               {
                   Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                   Intent i=new Intent(LoginActivity.this,MainActivity.class);
                   startActivity(i);
                   finish();
               }
               else
               {
                   Toast.makeText(LoginActivity.this, "Login Failed\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                   progressBar.setVisibility(View.GONE);
                   login.setVisibility(View.VISIBLE);

               }
           }
       });


    }
}
