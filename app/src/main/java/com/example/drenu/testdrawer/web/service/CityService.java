package com.example.drenu.testdrawer.web.service;

import com.example.drenu.testdrawer.model.CityCollection;


import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by drenu on 9/4/2018.
 */

public interface CityService {
    @GET("/?apikey=C1bDaps6SoUn7jIrz44NRgD1JLw8XEiR")
    void getCityList(Callback<CityCollection> callback);

}
