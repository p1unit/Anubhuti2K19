package com.anubhuti.knit.Activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.view.View;
import android.widget.ImageView;

import com.anubhuti.knit.Migration.FireBaseData;
import com.anubhuti.knit.Migration.UserMigration;
import com.anubhuti.knit.Model.PastFutureData;
import com.anubhuti.knit.R;
import com.anubhuti.knit.TinderSlider.CardStackAdapter;
import com.anubhuti.knit.TinderSlider.SpotDiffCallback;
import com.bumptech.glide.Glide;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    UserMigration userMigration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = (ImageView)findViewById(R.id.splash);

        Glide.with(this).asGif().load(R.raw.gif).into(imageView);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                        startApp();
                    }
                }, 2000);


    }

    private void startApp() {

        userMigration=new UserMigration();
        if(userMigration.isUserLogin()){
            Intent intent=new Intent(this,HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }else {
            Intent intent=new Intent(this,LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().freeMemory();
    }


}
