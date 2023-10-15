package com.example.mmm3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.IOException;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    // creating a variable for
    // button and media player


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView mBottomNav = findViewById(R.id.bottomNavigationView);
        mBottomNav.setItemIconTintList(null);
        startActivity(new Intent(MainActivity.this, MapsActivity.class));

    }

}