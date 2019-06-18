package com.example.forsqtest.UI.nextActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.forsqtest.Data.model.Item;
import com.example.forsqtest.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NextActivity extends AppCompatActivity implements OnMapReadyCallback {


    @BindView(R.id.next_adress)
    TextView mAdress;
    @BindView(R.id.next_city)
    TextView mCity;
    @BindView(R.id.next_distance)
    TextView mDistance;
    @BindView(R.id.next_name)
    TextView mName;
    @BindView(R.id.next_image)
    ImageView mImage;
    private GoogleMap mMap;
    private Item mData;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        ButterKnife.bind(this);


        Intent intent = getIntent();
        String recipe = intent.getStringExtra("gson");
        mData =new Gson().fromJson(recipe,Item.class);

        if (!(mData.getVenue().getLocation().getAddress().isEmpty()))
        {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }

        mName.setText(mData.getVenue().getName());
        mAdress.setText(mData.getVenue().getLocation().getAddress());
        mCity.setText(mData.getVenue().getLocation().getCity());
        mDistance.setText(mData.getVenue().getLocation().getDistance()+" метра");
        Glide.with(mImage)
                .load(mData.getVenue().getCategories().get(0).getIcon().getPrefix()+"100.png")
                .apply(RequestOptions
                        .errorOf(R.drawable.ic_launcher_foreground)
                        .placeholder(R.drawable.ic_launcher_foreground))
                .into(mImage);


        //getSupportFragmentManager().beginTransaction().replace(R.id.frgmCount, MapsActivity(mData)).commit();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(mData.getVenue().getLocation().getLat(), mData.getVenue().getLocation().getLng());
        mMap.addMarker(new MarkerOptions().position(sydney).title(mData.getVenue().getName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
    }
}
