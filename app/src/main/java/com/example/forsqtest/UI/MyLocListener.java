package com.example.forsqtest.UI;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MyLocListener implements LocationListener {

    public static String loc="";


    @Override
    public void onLocationChanged(Location location) {
        if (location != null)
            Log.v("LocationCheck", location.getLatitude() + "," + location.getLongitude());
            loc=location.getLatitude() + "," + location.getLongitude();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
