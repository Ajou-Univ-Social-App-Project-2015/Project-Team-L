package com.example.gwangrok.gallery;

import android.app.Application;
import com.parse.Parse;
import com.parse.ParseACL;

import com.parse.ParseUser;

/**
 * Created by taerin on 2015. 5. 24..
 */
public class ParseApplication extends Application {

    @Override

    public void onCreate() {
        super.onCreate();



        Parse.initialize(this, "EFjb0LegkvxPZihtopAZwCGkh2r2SB25nHYYFW2w","vJGsrojhHDC180MfZBqmf3isXXTm8FLVxIQQEHO6");

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);
    }
}
