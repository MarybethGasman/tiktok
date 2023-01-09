package org.tm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.List;

public class VideoListResponse{

    @JsonUnwrapped
    private Response response;

    @JsonProperty("next_time")
    private Integer nextTime;

    @JsonProperty("video_list")
    private List<VideoDTO> videoList;


    public VideoListResponse(Response response, Integer nextTime, List<VideoDTO> videoList) {
        this.response = response;
        this.nextTime = nextTime;
        this.videoList = videoList;
    }

    public Integer getNextTime() {
        return nextTime;
    }

    public void setNextTime(Integer nextTime) {
        this.nextTime = nextTime;
    }

    public List<VideoDTO> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<VideoDTO> videoList) {
        this.videoList = videoList;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
