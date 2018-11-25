package com.anubhuti.knit.Response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.anubhuti.knit.Model.TeamDetail;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeamResponse implements Serializable {

    @SerializedName("TeamDetails")
    @Expose
    public List<TeamDetail> teamDetails = new ArrayList<>();

    public List<TeamDetail> getTeamDetails() {
        return teamDetails;
    }

    public void setTeamDetails(List<TeamDetail> teamDetails) {
        this.teamDetails = teamDetails;
    }

    public TeamResponse(List<TeamDetail> teamDetails) {
        this.teamDetails = teamDetails;
    }

    public TeamResponse() {
    }
}