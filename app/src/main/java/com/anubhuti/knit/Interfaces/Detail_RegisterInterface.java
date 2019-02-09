package com.anubhuti.knit.Interfaces;

import com.anubhuti.knit.Response.EventDescResponse;

public interface Detail_RegisterInterface {

    void registerEvent(String url);
    void detailEvent(String id);
    void getEvent(EventDescResponse response);
    void errorResponse();
}
