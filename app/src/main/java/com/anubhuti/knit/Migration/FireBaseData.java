package com.anubhuti.knit.Migration;

import android.content.Context;
import android.content.SharedPreferences;

import com.anubhuti.knit.Model.TeamDetail;
import com.anubhuti.knit.Response.TeamResponse;
import com.anubhuti.knit.Utils.ApplicationContextProvider;
import com.anubhuti.knit.Utils.Config;
import com.google.gson.Gson;

import java.util.ArrayList;

public class FireBaseData {

    public Context context = ApplicationContextProvider.getContext();
    private SharedPreferences sharedPreferences = context.getSharedPreferences(Config.SHARED_PREFERENCE_FIREBASE_DATA, Context.MODE_PRIVATE);

    public void setTeamData(TeamResponse teamData){

        Gson gson=new Gson();
        String str=gson.toJson(teamData);
        sharedPreferences.edit().putString("teamDetails",str).apply();
    }

    public ArrayList<TeamDetail> getTaemData(){
        Gson gson=new Gson();
        String str=sharedPreferences.getString("teamDetails","");
        TeamResponse teamResponse=gson.fromJson(str,TeamResponse.class);
        return (ArrayList<TeamDetail>) teamResponse.getTeamDetails();
    }
}
