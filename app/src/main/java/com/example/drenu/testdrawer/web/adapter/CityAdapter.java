package com.example.drenu.testdrawer.web.adapter;

import com.example.drenu.testdrawer.web.service.CityService;

import retrofit.RestAdapter;


/**
 * Created by drenu on 9/4/2018.
 */

public class CityAdapter {

    public CityService getCityService()
    {
        RestAdapter retrofit=new RestAdapter.Builder()
                .setEndpoint("https://api.internationalshowtimes.com/v4/cities")
                .build();
        return retrofit.create(CityService.class);
    }


}
