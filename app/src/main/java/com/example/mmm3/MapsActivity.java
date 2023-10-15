package com.example.mmm3;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.mmm3.databinding.ActivityMainBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.mmm3.databinding.ActivityMapsBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView mBottomNav = findViewById(R.id.bottomNavigationView);
        mBottomNav.setItemIconTintList(null);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        CreateFragment createFragment = (CreateFragment) getSupportFragmentManager()
                .findFragmentById(R.id.create);

        getSupportFragmentManager().beginTransaction().hide(createFragment).commit();


        mapFragment.setRetainInstance(true);
        mapFragment.getMapAsync(this);

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction;
            switch (item.getItemId()){
                case R.id.Map:
                    transaction = fragmentManager.beginTransaction();
                    transaction.setReorderingAllowed(true);
                    transaction.hide(createFragment);
                    transaction.show(mapFragment);
                    transaction.commit();
                    break;

                case R.id.memorycreate:
                    transaction = fragmentManager.beginTransaction();
                    transaction.setReorderingAllowed(true);
                    transaction.hide(mapFragment);
                    transaction.show(createFragment);
                    transaction.commit();
                    break;
            }
            return true;
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng atlanta = new LatLng(33.7488, -84.386330);
        mMap.addMarker(new MarkerOptions().position(atlanta).title("ATL"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(atlanta));
    }

    private void mapInit() {
        if (mMap != null) {
        LatLng atlanta = new LatLng(33.7488, -84.386330);
        mMap.addMarker(new MarkerOptions().position(atlanta).title("ATL"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(atlanta));}
    }
}