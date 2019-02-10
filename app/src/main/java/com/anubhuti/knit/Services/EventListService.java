package com.anubhuti.knit.Services;

import android.content.Context;
import android.util.Log;

import com.anubhuti.knit.Interfaces.Detail_RegisterInterface;
import com.anubhuti.knit.Interfaces.EventListInterface;
import com.anubhuti.knit.Interfaces.StarInterface;
import com.anubhuti.knit.Response.EventDescResponse;
import com.anubhuti.knit.Response.EventTypeResponse;
import com.anubhuti.knit.Utils.ApplicationContextProvider;

import java.io.IOException;
import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventListService implements Serializable {


    private ApiClient apiClient=new ApiClient();
    private Context  context=ApplicationContextProvider.getContext();
    private EventListInterface listInterface;
    private Detail_RegisterInterface detail_registerInterface;
    private StarInterface starInterface;

    public EventListService(StarInterface starInterface) {
        this.starInterface = starInterface;
    }

    public EventListService(Detail_RegisterInterface detail_registerInterface) {
        this.detail_registerInterface = detail_registerInterface;
    }

    public EventListService(EventListInterface listInterface) {
        this.listInterface = listInterface;
    }

    public void getEventList(String id){

        getList(id);
    }

    private void getList(String id) {

        ApiService apiService = apiClient.createService(ApiService.class);
        Call<EventTypeResponse> call = apiService.getList(id);
        call.enqueue(new Callback<EventTypeResponse>() {
            @Override
            public void onResponse(Call<EventTypeResponse> call, Response<EventTypeResponse> response) {
                if(response.isSuccessful() && response.body()!=null){
                    listInterface.setEventList(response.body());
                }else{
                    apiClient.apiErrorAsync(context, response);
                    try {
                        Log.e("list Error", response.errorBody().string());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    listInterface.errorResponse();
                }
            }
            @Override
            public void onFailure(Call<EventTypeResponse> call, Throwable t) {
                Log.e("list Error","Failed");
                apiClient.failureErrorAsync(context, t);
                listInterface.errorResponse();
            }
        });
    }

    public void getEventDesd(String id) {

        ApiService apiService = apiClient.createService(ApiService.class);
        Call<EventDescResponse> call = apiService.getDesc(id);
        call.enqueue(new Callback<EventDescResponse>() {
            @Override
            public void onResponse(Call<EventDescResponse> call, Response<EventDescResponse> response) {
                if(response.isSuccessful() && response.body()!=null){
                    detail_registerInterface.getEvent(response.body());
                }else{
                    apiClient.apiErrorAsync(context, response);
                    try {
                        Log.e("list Error", response.errorBody().string());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    detail_registerInterface.errorResponse();
                }
            }
            @Override
            public void onFailure(Call<EventDescResponse> call, Throwable t) {
                Log.e("list Error","Failed");
                apiClient.failureErrorAsync(context, t);
                detail_registerInterface.errorResponse();
            }
        });
    }


    public void getStarDes(String id) {

        ApiService apiService = apiClient.createService(ApiService.class);
        Call<EventDescResponse> call = apiService.getDesc(id);
        call.enqueue(new Callback<EventDescResponse>() {
            @Override
            public void onResponse(Call<EventDescResponse> call, Response<EventDescResponse> response) {
                if(response.isSuccessful() && response.body()!=null){
                    starInterface.getDetail(response.body());
                }else{
                    apiClient.apiErrorAsync(context, response);
                    try {
                        Log.e("list Error", response.errorBody().string());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    starInterface.errorResponse();
                }
            }
            @Override
            public void onFailure(Call<EventDescResponse> call, Throwable t) {
                Log.e("list Error","Failed");
                apiClient.failureErrorAsync(context, t);
                starInterface.errorResponse();
            }
        });
    }

}
