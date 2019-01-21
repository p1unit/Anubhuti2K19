package com.anubhuti.knit.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anubhuti.knit.Activities.HomeActivity;
import com.anubhuti.knit.Adapter.EventListAdapter;
import com.anubhuti.knit.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(this,HomeActivity.class));
    }
}
