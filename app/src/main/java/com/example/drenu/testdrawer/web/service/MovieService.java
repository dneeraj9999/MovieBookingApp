package com.example.drenu.testdrawer.web.service;

import com.example.drenu.testdrawer.model.CinemaCollection;
import com.example.drenu.testdrawer.model.MovieCollection;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by drenu on 9/15/2018.
 */

public interface MovieService {

        @GET("/?city_ids=15635&apikey=C1bDaps6SoUn7jIrz44NRgD1JLw8XEiR")
        void getMovieList(Callback<MovieCollection> callback);

}
