package com.anubhuti.knit.Model;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TeamDetail implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("post")
    @Expose
    private String post;
    @SerializedName("emailId")
    @Expose
    private String emailId;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("priority")
    @Expose
    private String priority;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public TeamDetail(String name, String post, String emailId, String imageUrl, String mobileNo, String priority) {
        this.name = name;
        this.post = post;
        this.emailId = emailId;
        this.imageUrl = imageUrl;
        this.mobileNo = mobileNo;
        this.priority = priority;
    }

    public TeamDetail() {
    }
}
