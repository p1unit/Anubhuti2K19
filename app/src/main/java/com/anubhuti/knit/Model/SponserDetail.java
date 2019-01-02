package com.anubhuti.knit.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SponserDetail implements Serializable {

    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("webLink")
    @Expose
    private String webLink;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("name")
    @Expose
    private String name;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SponserDetail(String desc, String webLink, String imageUrl, String name) {
        this.desc = desc;
        this.webLink = webLink;
        this.imageUrl = imageUrl;
        this.name = name;
    }

    public SponserDetail() {
    }
}
