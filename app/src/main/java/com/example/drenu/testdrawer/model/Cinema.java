package com.example.drenu.testdrawer.model;

import android.location.Location;

/**
 * Created by drenu on 9/8/2018.
 */

public class Cinema {

    public String id;
    public String slug;
    public String name;
    public String chainId;
    public String telephone;
    public String website;
    public Location location;
    public Object bookingType;

    public Cinema(String id,String slug,String name,String chainId,String telephone,String website,Location location,Object bookingType)
    {
        this.id=id;
        this.slug=slug;
        this.name=name;
        this.chainId=chainId;
        this.telephone=telephone;
        this.website=website;
        this.location=location;
        this.bookingType=bookingType;

    }



}
