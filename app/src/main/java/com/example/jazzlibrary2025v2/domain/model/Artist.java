package com.example.jazzlibrary2025v2.domain.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Artist {

    @SerializedName("artist_id")
    private int artistId;

    @SerializedName("artist_name")
    private String artistName;

    @SerializedName("artist_surname")
    private String artistSurname;

    @SerializedName("instrument_id")
    private int instrumentId;

    @SerializedName("artist_video_count")
    private int artistVideoCount;

    @SerializedName("artist_rank")
    private int artistRank;

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistSurname() {
        return artistSurname;
    }

    public void setArtistSurname(String artistSurname) {
        this.artistSurname = artistSurname;
    }

    public int getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(int instrumentId) {
        this.instrumentId = instrumentId;
    }

    public int getArtistVideoCount() {
        return artistVideoCount;
    }

    public void setArtistVideoCount(int artistVideoCount) {
        this.artistVideoCount = artistVideoCount;
    }

    public int getArtistRank() {
        return artistRank;
    }

    public void setArtistRank(int artistRank) {
        this.artistRank = artistRank;
    }

    public String getArtistFullName() {
        return artistName + " " + artistSurname;
    }
    @Override
    public String toString() {
        return "Artist{" +
                "artistId=" + artistId +
                ", artistName='" + artistName + '\'' +
                ", artistSurname='" + artistSurname + '\'' +
                ", instrumentId=" + instrumentId +
                ", artistVideoCount=" + artistVideoCount +
                ", artistRank=" + artistRank +
                '}';
    }
}


