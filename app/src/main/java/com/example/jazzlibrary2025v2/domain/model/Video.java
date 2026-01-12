package com.example.jazzlibrary2025v2.domain.model;


import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Video {

    @SerializedName("video_id")
    private int videoId;  // Changed to camelCase

    @SerializedName("duration_id")
    private int durationId;

    @SerializedName("video_name")
    private String videoName;

    @SerializedName("video_duration")
    private String videoDuration;

    @SerializedName("video_path")
    private String videoPath;

    @SerializedName("type_id")
    private int typeId;

    @SerializedName("location_id")
    private String locationId;

    @SerializedName("video_availability")
    private String videoAvailability;

    public Video() {

    }

    public Video(String s, String LOCiD) {

        this.locationId= LOCiD;
        this.videoName= s;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public int getDurationId() {
        return durationId;
    }

    public void setDurationId(int durationId) {
        this.durationId = durationId;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(String videoDuration) {
        this.videoDuration = videoDuration;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getVideoAvailability() {
        return videoAvailability;
    }

    public void setVideoAvailability(String videoAvailability) {
        this.videoAvailability = videoAvailability;
    }


    @Override
    public String toString() {
        return "Video{" +
                "videoId=" + videoId +
                ", durationId=" + durationId +
                ", videoName='" + videoName + '\'' +
                ", videoDuration='" + videoDuration + '\'' +
                ", videoPath='" + videoPath + '\'' +
                ", typeId=" + typeId +
                ", locationId='" + locationId + '\'' +
                ", videoAvailability='" + videoAvailability + '\'' +
                '}';
    }


}
