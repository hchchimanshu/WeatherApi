package com.example.weather.applicationfile;

import android.app.Application;

import com.example.weather.preferences.Preferences;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Preferences.getInstance().init(getApplicationContext());
        Preferences.getInstance().getSharedPreferences(getApplicationContext());
    }
}
