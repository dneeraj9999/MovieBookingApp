package com.example.drenu.testdrawer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.drenu.testdrawer.model.MovieCollection;
import com.example.drenu.testdrawer.web.adapter.MovieAdapter;
import com.example.drenu.testdrawer.web.service.MovieService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SelectMovieActivity extends AppCompatActivity {
    private MovieGridAdapter adapter;
    MovieService movieService;
    GridView gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_movie);

        final String city=getIntent().getExtras().getString("cid");

        gv=(GridView)findViewById(R.id.movieGrid);
        movieService=new MovieAdapter().getMovieService();

        movieService.getMovieList(new Callback<MovieCollection>() {
            @Override
            public void success(final MovieCollection movieCollection, Response response) {

                Log.i("Movie: ",movieCollection.movies[0].title);
                adapter=new MovieGridAdapter(SelectMovieActivity.this,movieCollection);

                gv.setAdapter(adapter);
                gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        SharedPreferences sharedPreferences=getSharedPreferences("com.example.drenu.testdrawer", Context.MODE_PRIVATE);
                        String selectedMovie=movieCollection.movies[i].title.toString();
                        String movieslug=movieCollection.movies[i].slug;
                        sharedPreferences.edit().putString("movie",selectedMovie).apply();
                        sharedPreferences.edit().putString("movieslug",movieslug).apply();

                        Intent intent=new Intent(SelectMovieActivity.this,SelectCinemaActivity.class);
                        intent.putExtra("cid",city);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("Err: ",error.toString());
            }
        });


    }
}
