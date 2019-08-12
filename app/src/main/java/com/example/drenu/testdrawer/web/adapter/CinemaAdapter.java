package com.example.drenu.testdrawer.web.adapter;

import com.example.drenu.testdrawer.web.service.CinemaService;
import com.example.drenu.testdrawer.web.service.CityService;

import retrofit.RestAdapter;

/**
 * Created by drenu on 9/8/2018.
 */

public class CinemaAdapter {

    public CinemaService getCinemaService()
    {
        RestAdapter retrofit=new RestAdapter.Builder()
                .setEndpoint("https://api.internationalshowtimes.com/v4/cinemas")
                .build();
        return retrofit.create(CinemaService.class);
    }

}
