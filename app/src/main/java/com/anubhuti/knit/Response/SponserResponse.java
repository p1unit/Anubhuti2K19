package com.anubhuti.knit.Response;

import com.anubhuti.knit.Model.SponserDetail;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SponserResponse implements Serializable {

    @SerializedName("SponserDetail")
    @Expose
    private List<SponserDetail> sponserDetail = null;

    public List<SponserDetail> getSponserDetail() {
        return sponserDetail;
    }

    public void setSponserDetail(List<SponserDetail> sponserDetail) {
        this.sponserDetail = sponserDetail;
    }

    public SponserResponse(List<SponserDetail> sponserDetail) {
        this.sponserDetail = sponserDetail;
    }

    public SponserResponse() {
    }
}
