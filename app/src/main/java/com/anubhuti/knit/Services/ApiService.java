package com.anubhuti.knit.Services;

import com.anubhuti.knit.Response.EventDescResponse;
import com.anubhuti.knit.Response.EventTypeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("eventlist/{eventListId}")
    Call<EventTypeResponse> getList(@Path("eventListId") String eventListId);

    @GET("events/{eventId}")
    Call<EventDescResponse> getDesc(@Path("eventId") String id);
}
