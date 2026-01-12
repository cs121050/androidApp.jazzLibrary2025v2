package com.example.jazzlibrary2025v2.domain.model;


import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Duration {

    @SerializedName("duration_id")
    private int durationId;
    @SerializedName("duration_name")
    private String durationName;
    @SerializedName("duration_video_count")
    private int durationVideoCount;

    public int getDurationId() {
        return durationId;
    }

    public void setDurationId(int durationId) {
        this.durationId = durationId;
    }

    public String getDurationName() {
        return durationName;
    }

    public void setDurationName(String durationName) {
        this.durationName = durationName;
    }

    public int getDurationVideoCount() {
        return durationVideoCount;
    }

    public void setDurationVideoCount(int durationVideoCount) {
        this.durationVideoCount = durationVideoCount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Duration duration = (Duration) o;
        return durationId == duration.durationId && durationVideoCount == duration.durationVideoCount && Objects.equals(durationName, duration.durationName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(durationId, durationName, durationVideoCount);
    }


    @Override
    public String toString() {
        return "Duration{" +
                "durationId=" + durationId +
                ", durationName='" + durationName + '\'' +
                ", durationVideoCount=" + durationVideoCount +
                '}';
    }
}

