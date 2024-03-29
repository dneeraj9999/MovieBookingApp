package com.example.drenu.testdrawer;


import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    IntentIntegrator qrScan;
    FragmentManager manager = getFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        TextView tv=(TextView)header.findViewById(R.id.nav_username);
        TextView tv1=(TextView)header.findViewById(R.id.nav_email);

            if(ParseUser.getCurrentUser()!=null) {
            tv.setText(ParseUser.getCurrentUser().getUsername());
            tv1.setText(ParseUser.getCurrentUser().getEmail());
        }

        manager.popBackStack();
        manager.beginTransaction().addToBackStack(null).commit();
        manager.beginTransaction().replace(R.id.frame, new HomeFragment()).commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action

            Intent reg=new Intent(MainActivity.this,RegisterActivity.class);
            startActivity(reg);
        }
        else if (id == R.id.home) {

            manager.popBackStack();
            manager.beginTransaction().addToBackStack(null).commit();
            manager.beginTransaction().replace(R.id.frame, new HomeFragment()).commit();
        }


        else if (id == R.id.nav_gallery) {

            Intent login=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(login);


        } else if (id == R.id.nav_slideshow) {

            ParseUser.logOut();
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            View header=navigationView.getHeaderView(0);
            TextView tv=(TextView)header.findViewById(R.id.nav_username);
            TextView tv1=(TextView)header.findViewById(R.id.nav_email);
            tv.setText("");
            tv1.setText("");
            manager.popBackStack();
            manager.beginTransaction().addToBackStack(null).commit();
            manager.beginTransaction().replace(R.id.frame, new HomeFragment()).commit();



        } else if (id == R.id.nav_manage) {

            if(ParseUser.getCurrentUser()!=null) {
                ConnectivityManager cm =
                        (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                if (isConnected) {
                    Intent bookMovie = new Intent(MainActivity.this, SelectCityActivity.class);
                    startActivity(bookMovie);
                } else {
                    Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
                }

            }
            else {
                Toast.makeText(this, "Please login first", Toast.LENGTH_LONG).show();
            }

        } else if (id == R.id.nav_share) {

            if(ParseUser.getCurrentUser()!=null) {
                manager.popBackStack();
                manager.beginTransaction().addToBackStack(null).commit();
                manager.beginTransaction().replace(R.id.frame, new QRFragment()).commit();
            }
            else
            {
                Toast.makeText(this, "Please login first", Toast.LENGTH_LONG).show();
            }

        } else if (id == R.id.nav_send) {

            if(ParseUser.getCurrentUser()!=null) {
                manager.popBackStack();
                manager.beginTransaction().addToBackStack(null).commit();
                manager.beginTransaction().replace(R.id.frame, new TransferFragment()).commit();
            }
            else{
                Toast.makeText(this, "Please login first", Toast.LENGTH_LONG).show();

            }


        }
        else if (id == R.id.order_history) {

            if(ParseUser.getCurrentUser()!=null) {
                manager.popBackStack();
                manager.beginTransaction().addToBackStack(null).commit();
                manager.beginTransaction().replace(R.id.frame, new OrderFragment()).commit();
            }
            else {
                Toast.makeText(this, "Please login first", Toast.LENGTH_LONG).show();

            }


        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}


