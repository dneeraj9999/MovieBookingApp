package com.example.drenu.testdrawer;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

/**
 * Created by drenu on 9/9/2018.
 */

public class ConnectToParse extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("1f72f936a562a165f3f978a3cc8946457c071b4a")
                .clientKey("f88ddf5134663c4ca8f21fe036e03f8633301a9a")
                .server("http://18.220.234.212:80/parse/")
                .build()
        );

       // ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

    }



}
