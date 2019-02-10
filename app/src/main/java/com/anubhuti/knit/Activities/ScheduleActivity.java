package com.anubhuti.knit.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;

import com.anubhuti.knit.R;

public class ScheduleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pop_up_contact);
        setContentView(R.layout.activity_schedule);

        WebView webView=findViewById(R.id.schedule);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://qfrat.co.in/anubhuti/schedule.html");
    }
}
