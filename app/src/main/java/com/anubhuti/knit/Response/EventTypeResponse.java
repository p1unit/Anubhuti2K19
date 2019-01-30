package com.anubhuti.knit.Response;

import java.io.Serializable;
import java.util.List;

import com.anubhuti.knit.Model.EventTypeModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventTypeResponse implements Serializable {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("allData")
    @Expose
    private List<EventTypeModel> allData = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<EventTypeModel> getAllData() {
        return allData;
    }

    public void setAllData(List<EventTypeModel> allData) {
        this.allData = allData;
    }

    public EventTypeResponse(String id, List<EventTypeModel> allData) {
        this.id = id;
        this.allData = allData;
    }

    public EventTypeResponse() {
    }
}
