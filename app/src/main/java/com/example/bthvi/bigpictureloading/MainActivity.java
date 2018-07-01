package com.example.bthvi.bigpictureloading;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * create by bthvi on 2018/06/29
 */
public class MainActivity extends AppCompatActivity {

    LargeImageView largeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        largeImageView = findViewById(R.id.largeImageView);
        try {
            InputStream stream = getAssets().open("world.jpg");
            largeImageView.setInputStream(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
