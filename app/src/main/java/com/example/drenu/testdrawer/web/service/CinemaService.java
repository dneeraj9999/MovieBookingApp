package com.example.drenu.testdrawer.web.service;

import com.example.drenu.testdrawer.model.CinemaCollection;
import com.example.drenu.testdrawer.model.CityCollection;

import retrofit.Callback;
import retrofit.http.EncodedPath;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by drenu on 9/8/2018.
 */

public interface CinemaService {

    @GET("/?apikey=C1bDaps6SoUn7jIrz44NRgD1JLw8XEiR")
    void getCinemaList(@Query("city_ids")String cityId, Callback<CinemaCollection> callback);


}
