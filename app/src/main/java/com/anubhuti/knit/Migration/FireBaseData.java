package com.anubhuti.knit.Migration;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.anubhuti.knit.Model.ContactUs;
import com.anubhuti.knit.Model.SponserDetail;
import com.anubhuti.knit.Model.TeamDetail;
import com.anubhuti.knit.Response.ContactUsResponse;
import com.anubhuti.knit.Response.SponserResponse;
import com.anubhuti.knit.Response.TeamResponse;
import com.anubhuti.knit.Utils.ApplicationContextProvider;
import com.anubhuti.knit.Utils.Config;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

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

    public void setSponserData(SponserResponse sponserData){

        Gson gson=new Gson();
        String str=gson.toJson(sponserData);
        sharedPreferences.edit().putString("sponserDetails",str).apply();
    }

    public List<SponserDetail> getSponserData() {

        Gson gson=new Gson();
        String str=sharedPreferences.getString("sponserDetails","");
        SponserResponse response=gson.fromJson(str,SponserResponse.class);
        return (ArrayList<SponserDetail>) response.getSponserDetail();
    }

    public void setContactUsData(ContactUsResponse response){

        Gson gson=new Gson();
        String str=gson.toJson(response);
        sharedPreferences.edit().putString("conatctUsdetails",str).apply();
    }

    public List<ContactUs> getContactUsData() {


        Gson gson=new Gson();
        String str=sharedPreferences.getString("conatctUsdetails","");
        ContactUsResponse response=gson.fromJson(str,ContactUsResponse.class);
        return response.getContactUs();
    }
}
