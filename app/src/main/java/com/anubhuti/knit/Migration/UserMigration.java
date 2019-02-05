package com.anubhuti.knit.Migration;

import android.content.Context;
import android.content.SharedPreferences;

import com.anubhuti.knit.Utils.ApplicationContextProvider;
import com.anubhuti.knit.Utils.Config;

public class UserMigration {

    public Context context = ApplicationContextProvider.getContext();
    private SharedPreferences sharedPreferences = context.getSharedPreferences(Config.SHARED_PREFERENCE_USER_DATA, Context.MODE_PRIVATE);

    public boolean isUserLogin(){

        return sharedPreferences.getInt("isLogin", -1) == 1;
    }

    public void setuserLogin(){

        sharedPreferences.edit().putInt("isLogin",1).apply();
    }

    public void setuserLogout(){

        sharedPreferences.edit().putInt("isLogin",-1).apply();
    }
}
