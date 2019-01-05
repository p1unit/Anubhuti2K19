package com.anubhuti.knit.Response;

import com.anubhuti.knit.Model.ContactUs;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ContactUsResponse implements Serializable {

    @SerializedName("ContactUs")
    @Expose
    private List<ContactUs> contactUs = null;

    public List<ContactUs> getContactUs() {
        return contactUs;
    }

    public void setContactUs(List<ContactUs> contactUs) {
        this.contactUs = contactUs;
    }

    public ContactUsResponse(List<ContactUs> contactUs) {
        this.contactUs = contactUs;
    }

    public ContactUsResponse() {
    }
}
