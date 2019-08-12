package com.example.drenu.testdrawer;

import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.example.drenu.testdrawer.model.MovieCollection;
import com.example.drenu.testdrawer.web.adapter.CinemaAdapter;
import com.example.drenu.testdrawer.web.adapter.MovieAdapter;
import com.example.drenu.testdrawer.web.service.CinemaService;
import com.example.drenu.testdrawer.web.service.MovieService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MovieActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieCardAdapter adapter;
    MovieService movieService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);

        movieService=new MovieAdapter().getMovieService();

        movieService.getMovieList(new Callback<MovieCollection>() {
            @Override
            public void success(MovieCollection movieCollection, Response response) {

                Log.i("Movie: ",movieCollection.movies[0].title);
                adapter=new MovieCardAdapter(MovieActivity.this,movieCollection);
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(MovieActivity.this, 2);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);



            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }

    /**
     * Converting dp to pixel
     */
    public int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }



}
