package com.anubhuti.knit.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anubhuti.knit.Activities.HomeActivity;
import com.anubhuti.knit.Adapter.EventListAdapter;
import com.anubhuti.knit.Migration.UserMigration;
import com.anubhuti.knit.R;

public class MainActivity extends AppCompatActivity {

    UserMigration userMigration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
