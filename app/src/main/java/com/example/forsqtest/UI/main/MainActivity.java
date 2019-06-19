package com.example.forsqtest.UI.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.forsqtest.App.App;
import com.example.forsqtest.Data.model.Item;
import com.example.forsqtest.Data.model.Response;
import com.example.forsqtest.R;
import com.example.forsqtest.UI.MyLocListener;
import com.example.forsqtest.UI.nextActivity.NextActivity;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, AdapterResponceInfo.OnitemClick {

    @BindView(R.id.recyc)
    RecyclerView mRecyc;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mSwipeRefr;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    LocationManager mLocMan;
    MyLocListener mLockList;
    String mLocationPoint;
    SharedPreferences sr;
    private AdapterResponceInfo mAdapter = new AdapterResponceInfo();
    private String CLIEND_ID = "URR20YWEGRJLWTRGOCN0SGKOGLGZ20P1JCQ1Y5OQIWUQ3AUM";
    private String CLIEND_SECRET = "ZFI3F1GXPTH1JQTZALJ2YLYOO1V2M42SE5R1GS3XMQGRI3SR";
    private String V = "20190617";
    private String LL = "55.8019846166034,49.12149503824776";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mSwipeRefr.setOnRefreshListener(this);
        mSwipeRefr.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 10);
        getLocation();
        sr = getSharedPreferences("Somename", MODE_PRIVATE);
        String check = sr.getString("DataModel", "");
        if (check.isEmpty()) {
            App.apiHelper.getRoundData(CLIEND_ID, CLIEND_SECRET, V, LL)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(o -> {
                        App.dmManager.setResponceInfo(o.getResponse());
                        mRecyc.setLayoutManager(new LinearLayoutManager(this));
                        mAdapter.setItems(App.dmManager.getmResponce().getGroups().get(0).getItems());
                        mAdapter.setOnitemClick(this);
                        mRecyc.setAdapter(mAdapter);
                        sr.edit().putString("DataModel", new Gson().toJson(o.getResponse())).commit();


                    }, t -> {
                        Log.e("DannErr", t.toString());
                        Toast.makeText(this, "Connection Error", Toast.LENGTH_SHORT).show();

                    });
        } else {
            Response response = new Gson().fromJson(sr.getString("DataModel", ""), Response.class);
            App.dmManager.setResponceInfo(response);
            mRecyc.setLayoutManager(new LinearLayoutManager(this));
            mAdapter.setItems(App.dmManager.getmResponce().getGroups().get(0).getItems());
            mAdapter.setOnitemClick(this);
            mRecyc.setAdapter(mAdapter);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.deletecash,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_cash:{
                sr.edit().clear().apply();
                Toast.makeText(this, "Cash removed", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        if (!isGPSEnabled())
        {
            Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
        mLockList = new MyLocListener();
        mLocMan = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        mLocMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, mLockList);
        mLocationPoint = MyLocListener.loc;
    }

    @Override
    public void onRefresh() {

        getLocation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                App.apiHelper.getRoundData(CLIEND_ID, CLIEND_SECRET, V, mLocationPoint)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(o -> {
                            App.dmManager.setResponceInfo(o.getResponse());
                            mAdapter.setItems(App.dmManager.getmResponce().getGroups().get(0).getItems());
                            sr.edit().putString("DataModel", new Gson().toJson(o.getResponse())).commit();
                        }, t -> {
                            Log.e("DannErr", t.toString());
                            Toast.makeText(getBaseContext(), "Connection Error", Toast.LENGTH_SHORT).show();

                        });
                mSwipeRefr.setRefreshing(false);
            }
        }, 2000);
    }

    @Override
    public void start(Item item) {
        Intent intent = new Intent(MainActivity.this, NextActivity.class);
        String gson = new Gson().toJson(item);
        intent.putExtra("gson", gson);
        startActivity(intent);
    }



    public boolean isGPSEnabled() {

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager != null)
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        else
            return false;
    }
}
