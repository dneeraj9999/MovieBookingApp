package com.example.drenu.testdrawer.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by drenu on 9/4/2018.
 */

public class CityCollection {

    public final City[] cities;
    public final Map<String,Integer> m=new HashMap<>();

    public CityCollection(City[] cities)
    {
        this.cities=cities;
    }



}
