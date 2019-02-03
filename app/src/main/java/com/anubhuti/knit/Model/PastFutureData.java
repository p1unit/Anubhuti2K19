package com.anubhuti.knit.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;


public class PastFutureData implements Serializable {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }


    public PastFutureData(String id, String name, String imageUrl, Integer v) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.v = v;
    }

    public PastFutureData(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public PastFutureData() {
    }
}