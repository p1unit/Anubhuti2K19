package com.anubhuti.knit.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.anubhuti.knit.R;

public class EventDiscription extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {
        private static final int PERCENTAGE_TO_SHOW_IMAGE = 20;
        private View mFab;
        private int mMaxScrollSize;
        private boolean mIsImageHidden;
        TextView descText;

        String str="<p>comprises of four different courses, offering you everything needed to become a JavaScript Pro! Through guidance from best instructors and tutors of the industry, we provide our specially curat</p><h2>Rules</h2><li>Aihih</li>	<li>Bjbb</li><li>Cknkn</li><h3>Judging Criteria</h>	<li>D</li>";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_event_discription);


            descText=findViewById(R.id.desc_text);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                descText.setText(Html.fromHtml(str, Html.FROM_HTML_MODE_COMPACT));
            } else {
                descText.setText(Html.fromHtml(str));
            }

            Toolbar toolbar = (Toolbar) findViewById(R.id.flexible_example_toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    onBackPressed();
                }
            });

            AppBarLayout appbar = (AppBarLayout) findViewById(R.id.flexible_example_appbar);
            appbar.addOnOffsetChangedListener(this);
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
}
