package com.jordan.sdawalib.kdaproject;

import android.app.Application;
import android.content.res.Configuration;

/**
 * Created by SamerGigaByte on 25/09/2015.
 */
public class MyApplication extends Application {

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}