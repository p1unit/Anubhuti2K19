package com.anubhuti.knit.Response;


import com.anubhuti.knit.Model.EventCatogry;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class EventCategoryResponse implements Serializable {


    @SerializedName("EventCatogry")
    @Expose
    private List<EventCatogry> eventCatogry = null;

    public List<EventCatogry> getEventCatogry() {
        return eventCatogry;
    }

    public void setEventCatogry(List<EventCatogry> eventCatogry) {
        this.eventCatogry = eventCatogry;
    }

    public EventCategoryResponse(List<EventCatogry> eventCatogry) {
        this.eventCatogry = eventCatogry;
    }

    public EventCategoryResponse() {
    }
}
