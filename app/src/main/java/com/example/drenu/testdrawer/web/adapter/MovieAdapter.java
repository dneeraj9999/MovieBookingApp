package com.example.drenu.testdrawer.web.adapter;

import com.example.drenu.testdrawer.web.service.CityService;
import com.example.drenu.testdrawer.web.service.MovieService;

import retrofit.RestAdapter;

/**
 * Created by drenu on 9/15/2018.
 */

public class MovieAdapter {

    public MovieService getMovieService()
    {
        RestAdapter retrofit=new RestAdapter.Builder()
                .setEndpoint("https://api.internationalshowtimes.com/v4/movies")
                .build();
        return retrofit.create(MovieService.class);
    }


}
