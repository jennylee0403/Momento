package com.example.mmm3;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;

import com.example.mmm3.databinding.ActivityMainBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.mmm3.databinding.ActivityMapsBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    private boolean locationPermissionGranted;

    static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION=2000;

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

        Menu menu1 = (Menu) binding.bottomNavigationView.getMenu();
        menu1.findItem(R.id.memorycreate).getIcon().setTint(100);
        menu1.findItem(R.id.AddFriend).getIcon().setTint(100);
        menu1.findItem(R.id.Settings).getIcon().setTint(100);

        mapFragment.setRetainInstance(true);
        mapFragment.getMapAsync(this);

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction;
            Menu menu = (Menu) binding.bottomNavigationView.getMenu();
            menu.findItem(R.id.Map).getIcon().setAlpha(130);
            menu.findItem(R.id.memorycreate).getIcon().setAlpha(130);
            menu.findItem(R.id.AddFriend).getIcon().setAlpha(130);
            menu.findItem(R.id.Settings).getIcon().setAlpha(130);
            item.getIcon().setAlpha(255);
            switch (item.getItemId()) {
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
        updateLocationUI();

        // Add a marker in Sydney and move the camera
        LatLng you = new LatLng(33.7756, -84.3963);
        LatLng klaus = new LatLng(	33.7772515, -84.3961846);
        LatLng hub = new LatLng(33.7809, -84.3892);
        LatLng adventure = new LatLng(33.7962, -84.4033);



        mMap.addMarker(new MarkerOptions().position(you).title("You").icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.momentocharacter_02, 250, 250)));
        mMap.addMarker(new MarkerOptions().position(klaus).title("Locked Memory").icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.instantfilm_02, 150, 150)));
        mMap.addMarker(new MarkerOptions().position(adventure).title("Locked Memory").icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.instantfilm_02, 150, 150)));
        mMap.addMarker(new MarkerOptions().position(hub).title("10/15/2022").icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.instantfilm_02__2_, 150, 125)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(you, 15.0f));
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        if (requestCode
                == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        updateLocationUI();
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                //lastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId, int height, int width) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        int w = vectorDrawable.getIntrinsicWidth();
        int h = vectorDrawable.getIntrinsicHeight();
        //vectorDrawable.setBounds(-w / 2, -h, w / 2, 0);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        Bitmap smallMarker = Bitmap.createScaledBitmap(bitmap, width, height, false);
        return BitmapDescriptorFactory.fromBitmap(smallMarker);
    }

    private void setMarkerBounce(final Marker marker) {
        final Handler handler = new Handler();
        final long startTime = SystemClock.uptimeMillis();
        final long duration = 2000;
        final Interpolator interpolator = new BounceInterpolator();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - startTime;
                float t = Math.max(1 - interpolator.getInterpolation((float) elapsed/duration), 0);
                marker.setAnchor(0.5f, 1.0f +  t);

                if (t > 0.0) {
                    handler.postDelayed(this, 16);
                } else {
                    setMarkerBounce(marker);
                }
            }
        });
    }




}