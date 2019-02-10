package com.anubhuti.knit.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.anubhuti.knit.Model.EventCatogry;
import com.anubhuti.knit.R;
import com.anubhuti.knit.Response.EventDescResponse;
import com.anubhuti.knit.Utils.ApplicationContextProvider;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class EventDiscription extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {
        private static final int PERCENTAGE_TO_SHOW_IMAGE = 20;
        private View mFab;
        private int mMaxScrollSize;
        CollapsingToolbarLayout layout;
        private boolean mIsImageHidden;
        EventCatogry eventdata;
      //  TextView descText;
        ImageView img;
        WebView webView;
        FloatingActionButton fab;

        String str="";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_event_discription);

            Toolbar toolbar = (Toolbar) findViewById(R.id.flexible_example_toolbar);
            layout=findViewById(R.id.collapsing);
            img=this.findViewById(R.id.event_img);
            webView=this.findViewById(R.id.webview);
            fab = findViewById(R.id.fab);

            Intent intent=getIntent();
            EventDescResponse response=(EventDescResponse) intent.getSerializableExtra("eventData");
            eventdata= (EventCatogry) intent.getSerializableExtra("contact");
            if(!intent.getBooleanExtra("isShow",true)){
                fab.hide();
            }

            str=response.getDesc();
            layout.setTitle(response.getName());
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadDataWithBaseURL("", str, "text/html", "UTF-8", "");
;

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.temp);
            requestOptions.error(R.drawable.temp);



            Glide.with(ApplicationContextProvider.getContext()).load(response.getImageUrl())
                    .apply(requestOptions).thumbnail(0.5f).into(img);


            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    onBackPressed();
                }
            });

            AppBarLayout appbar = (AppBarLayout) findViewById(R.id.flexible_example_appbar);
            appbar.addOnOffsetChangedListener(this);


            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startPopUp();
                }
            });
        }

    private void startPopUp() {

            Intent intent =new Intent(this,PopUpContact.class);
            intent.putExtra("EventData",eventdata);
            startActivity(intent);
    }

    @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
            if (mMaxScrollSize == 0)
                mMaxScrollSize = appBarLayout.getTotalScrollRange();

            int currentScrollPercentage = (Math.abs(i)) * 100
                    / mMaxScrollSize;

            if (currentScrollPercentage >= PERCENTAGE_TO_SHOW_IMAGE) {
                if (!mIsImageHidden) {
                    mIsImageHidden = true;

                    ViewCompat.animate(mFab).scaleY(0).scaleX(0).start();
                }
            }

            if (currentScrollPercentage < PERCENTAGE_TO_SHOW_IMAGE) {
                if (mIsImageHidden) {
                    mIsImageHidden = false;
                    ViewCompat.animate(mFab).scaleY(1).scaleX(1).start();
                }
            }
        }

        public static void start(Context c) {
            c.startActivity(new Intent(c, EventDiscription.class));
        }



    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().freeMemory();
    }
}
