package com.example.itubeapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import fragments.VideoPlayerFragment;
import fragments.PlaylistFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load video player fragment initially
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new VideoPlayerFragment())
                .commit();
    }
}