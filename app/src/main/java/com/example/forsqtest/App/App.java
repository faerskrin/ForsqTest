package com.example.forsqtest.App;

import android.app.Application;

import com.example.forsqtest.Data.ApiHelper;
import com.example.forsqtest.Data.DataManager;

public class App extends Application {
    public static ApiHelper apiHelper;
    public static DataManager dmManager;

    @Override
    public void onCreate() {
        super.onCreate();
        apiHelper = ApiHelper.Creator.newApi();
        dmManager = new DataManager();
    }
}
