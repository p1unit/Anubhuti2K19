package com.anubhuti.knit.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EventTypeModel implements Serializable {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("descId")
    @Expose
    private String descId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image1")
    @Expose
    private String image1;
    @SerializedName("image2")
    @Expose
    private String image2;
    @SerializedName("image3")
    @Expose
    private String image3;
//    @SerializedName("__v")
//    @Expose
//    private Integer v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescId() {
        return descId;
    }

    public void setDescId(String descId) {
        this.descId = descId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

//    public Integer getV() {
//        return v;
//    }
//
//    public void setV(Integer v) {
//        this.v = v;
//    }


    public EventTypeModel() {
    }

    public EventTypeModel(String id, String descId, String name, String image1, String image2, String image3) {
        this.id = id;
        this.descId = descId;
        this.name = name;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
    }
}