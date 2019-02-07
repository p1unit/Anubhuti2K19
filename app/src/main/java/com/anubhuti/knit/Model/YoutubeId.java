package com.anubhuti.knit.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class YoutubeId implements Serializable {
    @SerializedName("code")
    @Expose
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public YoutubeId(String code) {
        this.code = code;
    }

    public YoutubeId() {
    }
}
