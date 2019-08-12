package com.example.drenu.testdrawer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drenu.testdrawer.model.CityCollection;
import com.example.drenu.testdrawer.web.adapter.CityAdapter;
import com.example.drenu.testdrawer.web.service.CityService;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class SelectCityActivity extends AppCompatActivity {

    public AutoCompleteTextView cityList;
    public CityCollection cities;
    Button proceed;

    Map<String,Integer> cids=new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_movie);

        CityService cityService=new CityAdapter().getCityService();

        cityList=(AutoCompleteTextView)findViewById(R.id.cities);
        proceed=(Button)findViewById(R.id.proceed);

        cityService.getCityList(new Callback<CityCollection>() {
            @Override

            public void success(CityCollection cityCollection, Response response) {
                List<String> dropdown = new ArrayList<String>();
                dropdown.add("None");
                Log.i("City:",cityCollection.cities[16].lat.toString());
                for(int i=0;i<cityCollection.cities.length;i++) {
                    dropdown.add(cityCollection.cities[i].name);
                    cids.put(cityCollection.cities[i].name,cityCollection.cities[i].id);
                }
                cities=cityCollection;

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(),
                        R.layout.custom_spinner, dropdown);
                // Drop down layout style - list view with radio button
                dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                // attaching data adapter to spinner

                //citylist.setAdapter(dataAdapter);
                cityList.setAdapter(dataAdapter);

            }

            @Override
            public void failure(RetrofitError error) {
                    error.printStackTrace();
            }
        });

        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv=(TextView)view;
                String selectedCity=tv.getText().toString();
                SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("com.example.drenu.testdrawer", Context.MODE_PRIVATE);
                sharedPreferences.edit().putString("city",selectedCity).apply();
                Toast.makeText(getApplicationContext(),cids.get(tv.getText().toString()).toString(),Toast.LENGTH_LONG).show();

                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                View v = getCurrentFocus();
                //If no view currently has focus, create a new one, just so we can grab a window token from it
                if (v == null) {
                    v = new View(SelectCityActivity.this);
                }
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

                Intent i=new Intent(SelectCityActivity.this,SelectMovieActivity.class);
                i.putExtra("cid",cids.get(cityList.getText().toString()).toString());
                startActivity(i);

            }

        });

    }



}
