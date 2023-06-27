package com.example.api_sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Bundle extras = getIntent().getExtras();
        String lat = "0.0";
        String lng = "0.0";
        String query = "";
        if (extras != null) {
            lat = extras.getString("lat");
            lng = extras.getString("lng");
            query = extras.getString("query");
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        String finalLat = lat;
        String finalLng = lng;
        String finalQuery = query;
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                mMap = googleMap;
                LatLng pos = new LatLng(Float.parseFloat(finalLat), Float.parseFloat(finalLng));
                mMap.addMarker(new MarkerOptions().position(pos).title(finalQuery));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos,15f));
            }
        });
    }
}