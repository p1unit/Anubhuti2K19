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

    public void setScreenSize(int sz1,int sz2) {
        sharedPreferences.edit().putInt("sz1",dpToPx(sz1)).apply();
        sharedPreferences.edit().putInt("sz2",dpToPx(sz2)).apply();
    }

    public int getsz1(){
        return sharedPreferences.getInt("sz1",15);
    }

    public int getsz2(){
        return sharedPreferences.getInt("sz2",56);
    }

    public static int dpToPx(int dp) {
        float density = ApplicationContextProvider.getContext().getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }

    public void setVersion(String value) {
        sharedPreferences.edit().putString("version",value).apply();
    }

    public String getversion() {
        return sharedPreferences.getString("version","");
    }
}
