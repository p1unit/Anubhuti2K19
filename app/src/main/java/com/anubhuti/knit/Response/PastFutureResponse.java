package com.anubhuti.knit.Response;

import java.io.Serializable;
import java.util.List;

import com.anubhuti.knit.Model.PastFutureData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PastFutureResponse implements Serializable {

    @SerializedName("data")
    @Expose
    private List<PastFutureData> data = null;
    @SerializedName("_id")
    @Expose
    private String id;

    public List<PastFutureData> getData() {
        return data;
    }

    public void setData(List<PastFutureData> data) {
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PastFutureResponse(List<PastFutureData> data, String id) {
        this.data = data;
        this.id = id;
    }

    public PastFutureResponse() {
    }
}