package com.anubhuti.knit.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.anubhuti.knit.R;
import com.bumptech.glide.Glide;


public class SplashActivity extends AppCompatActivity {
      ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageView = (ImageView)findViewById(R.id.gif_imageview);


        Glide.with(this).asGif().load(R.raw.gif).into(imageView);
    }
}
