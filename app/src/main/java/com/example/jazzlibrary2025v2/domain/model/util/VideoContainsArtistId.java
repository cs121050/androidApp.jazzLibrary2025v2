package com.example.jazzlibrary2025v2.domain.model.util;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

public class VideoContainsArtistId implements Serializable {

    private int artistId;  // Artist ID
    private int videoId;   // Video ID

    // Default constructor
    public VideoContainsArtistId() {}

    // Constructor with parameters
    public VideoContainsArtistId(int artistId, int videoId) {
        this.artistId = artistId;
        this.videoId = videoId;
    }

    // Getter and setter for artistId
    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    // Getter and setter for videoId
    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    @NonNull
    @Override
    public String toString() {
        return "VideoContainsArtistId{" +
                "artistId=" + artistId +
                ", videoId=" + videoId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VideoContainsArtistId that = (VideoContainsArtistId) o;
        return artistId == that.artistId && videoId == that.videoId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(artistId, videoId);
    }
}
