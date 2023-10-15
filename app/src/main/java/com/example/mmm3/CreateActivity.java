package com.example.mmm3;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mmm3.databinding.ActivityMapsBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CreateActivity extends AppCompatActivity {

    // creating a variable for
    // button and media player

    private ActivityMapsBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);
        BottomNavigationView mBottomNav = findViewById(R.id.bottomNavigationView);
        mBottomNav.setItemIconTintList(null);
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Intent i;
            switch (item.getItemId()){
                case R.id.Map:
                    i = new Intent(CreateActivity.this, MapsActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    startActivity(i);
                    finish();
                    break;
            }
            return true;
        });

    }

}