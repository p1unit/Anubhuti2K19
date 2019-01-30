package com.anubhuti.knit.Services;

import android.content.Context;
import android.util.Log;

import com.anubhuti.knit.Interfaces.EventListInterface;
import com.anubhuti.knit.Response.EventTypeResponse;
import com.anubhuti.knit.Utils.ApplicationContextProvider;

import java.io.IOException;
import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventListService implements Serializable {


    ApiClient apiClient=new ApiClient();
    Context  context=ApplicationContextProvider.getContext();
    EventListInterface listInterface;

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
                }
            }
            @Override
            public void onFailure(Call<EventTypeResponse> call, Throwable t) {
                Log.e("list Error","Failed");
                apiClient.failureErrorAsync(context, t);
            }
        });
    }

}
