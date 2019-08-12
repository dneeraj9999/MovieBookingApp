package com.example.drenu.testdrawer;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drenu.testdrawer.model.CinemaCollection;
import com.example.drenu.testdrawer.web.adapter.CinemaAdapter;
import com.example.drenu.testdrawer.web.adapter.CityAdapter;
import com.example.drenu.testdrawer.web.service.CinemaService;
import com.example.drenu.testdrawer.web.service.CityService;
import com.google.android.gms.vision.text.Line;

import java.lang.reflect.Field;
import java.util.Calendar;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SelectCinemaActivity extends AppCompatActivity {

    CinemaService cinemaService;
    ListView cinemaList;
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    public ImageView imageView;
    private Button a;
    private Button b;
    private Button c;
    private Button d;
    private TextView tv;
    private TextView tv1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_cinema);

        CinemaService cinemaService=new CinemaAdapter().getCinemaService();

        String cinemaId=getIntent().getExtras().getString("cid");
        cinemaList=(ListView)findViewById(R.id.cinemaList);

        cinemaService.getCinemaList(cinemaId, new Callback<CinemaCollection>() {
            @Override
            public void success(final CinemaCollection cinemaCollection, Response response) {

                System.out.println(cinemaCollection.cinemas[0].name);
                CustomAdapter adapter=new CustomAdapter(SelectCinemaActivity.this,cinemaCollection);
                cinemaList.setAdapter(adapter);
      /*          cinemaList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        String selectedCinema=cinemaCollection.cinemas[i].name;
                        String slug=cinemaCollection.cinemas[i].slug;
                        a=(Button)view.findViewById(R.id.t1);
                        b=(Button)view.findViewById(R.id.t2);
                        c=(Button)view.findViewById(R.id.t3);
                        d=(Button)view.findViewById(R.id.t4);

                        Log.i("I value",i+"");

                        a.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Toast.makeText(getApplicationContext(),tv1.getText()+tv.getText().toString(),Toast.LENGTH_LONG).show();

                                SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("com.example.drenu.testdrawer", Context.MODE_PRIVATE);
                                sharedPreferences.edit().putString("cinema",tv.getText().toString()).apply();
                                sharedPreferences.edit().putString("cinemaslug",tv1.getText().toString()).apply();

                                Button btn = (Button)view;
                                String buttonText = btn.getText().toString();

                                Intent intent=new Intent(getApplicationContext(),SelectSeatActivity.class);
                                intent.putExtra("time",buttonText);
                                getApplicationContext().startActivity(intent);


                            }
                        });

                        Intent intent=new Intent(SelectCinemaActivity.this,SelectMovieActivity.class);
                        startActivity(intent);
                    }
                });
      */      }

            @Override
            public void failure(RetrofitError error) {
                Log.e("Retrofit Error", error.getMessage());
            }
        });


        dateView = (TextView) findViewById(R.id.date);
        imageView=(ImageView)findViewById(R.id.opendatepicker);

        // datePicker=new DatePicker(this);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setDate(v);

            }
        });

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);


    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {

            DatePickerDialog dialog = new DatePickerDialog(this, myDateListener, year, month, day);
            Field mDatePickerField;
            try {
                mDatePickerField = dialog.getClass().getDeclaredField("mDatePicker");
                mDatePickerField.setAccessible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            dialog.getDatePicker().setMaxDate(System.currentTimeMillis() -1000 + (1000*60*60*24*7));


            return dialog;
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    arg0.setMinDate(System.currentTimeMillis() - 1000);

                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("-")
                .append(month).append("-").append(year));
    }


    public void myclick(View v) {

        LinearLayout vwParentRow = (LinearLayout)v.getParent();

        LinearLayout ll=(LinearLayout)vwParentRow.getParent();

        TextView child = (TextView)ll.getChildAt(0);
        TextView child1 = (TextView)ll.getChildAt(1);

        Toast.makeText(getApplicationContext(),child.getText().toString()+child1.getText().toString(),Toast.LENGTH_LONG).show();

        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("com.example.drenu.testdrawer", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("cinema",child.getText().toString()).apply();
        sharedPreferences.edit().putString("cinemaslug",child1.getText().toString()).apply();

        Button btn = (Button)v;
        String buttonText = btn.getText().toString();

        Toast.makeText(getApplicationContext(),buttonText,Toast.LENGTH_LONG).show();

        Intent intent=new Intent(getApplicationContext(),SelectSeatActivity.class);
        intent.putExtra("time",buttonText);
        intent.putExtra("date",dateView.getText());
        startActivity(intent);


    }
}
