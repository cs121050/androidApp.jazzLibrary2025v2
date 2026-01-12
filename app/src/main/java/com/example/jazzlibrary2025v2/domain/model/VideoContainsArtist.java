package com.example.jazzlibrary2025v2.domain.model;


import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import com.example.jazzlibrary2025v2.domain.model.util.VideoContainsArtistId;

public class VideoContainsArtist {

    private VideoContainsArtistId id;

    private Artist artist;

    private Video video;

    // Constructors
    public VideoContainsArtist() {}

    public VideoContainsArtist(Artist artist, Video video) {
        this.artist = artist;
        this.video = video;
        this.id = new VideoContainsArtistId(artist.getArtistId(), video.getVideoId());
    }

    // Getters and Setters
    public VideoContainsArtistId getId() {
        return id;
    }

    public void setId(VideoContainsArtistId id) {
        this.id = id;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }
}

