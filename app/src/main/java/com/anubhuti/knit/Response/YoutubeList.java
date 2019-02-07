package com.anubhuti.knit.Response;

import com.anubhuti.knit.Model.YoutubeId;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class YoutubeList implements Serializable {

    @SerializedName("youtubeVideos")
    @Expose
    private List<YoutubeId> youtubeVideos=null;

    public List<YoutubeId> getYoutubeVideos() {
        return youtubeVideos;
    }

    public void setYoutubeVideos(List<YoutubeId> youtubeVideos) {
        this.youtubeVideos = youtubeVideos;
    }

    public YoutubeList(List<YoutubeId> youtubeVideos) {
        this.youtubeVideos = youtubeVideos;
    }

    public YoutubeList() {
    }
}
