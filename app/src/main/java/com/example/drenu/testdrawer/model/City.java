package com.example.drenu.testdrawer.model;

import android.content.Intent;

/**
 * Created by drenu on 9/4/2018.
 */

public class City {

    public Integer id;
    public String name;
    public String slug;
    public Double lat;
    public Double lon;
    public String country;

    public City(Integer id, String name, String slug, Double lat, Double lon, String country)
    {
        this.id=id;
        this.name=name;
        this.country=country;
        this.lat=lat;
        this.lon=lon;
        this.slug=slug;

    }



}
