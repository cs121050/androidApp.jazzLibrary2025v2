package com.example.jazzlibrary2025v2.domain.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Instrument {

    @SerializedName("instrument_id")
    private int instrumentId;
    @SerializedName("instrument_name")
    private String instrumentName;
    @SerializedName("instrument_artist_count")
    private int instrumentArtistCount;
    @SerializedName("instrument_video_count")
    private int instrumentVideoCount;

    // Constructors
    public Instrument() {}

    public Instrument(int instrumentId, String instrumentName, int instrumentArtistCount, int instrumentVideoCount) {
        this.instrumentId = instrumentId;
        this.instrumentName = instrumentName;
        this.instrumentArtistCount = instrumentArtistCount;
        this.instrumentVideoCount = instrumentVideoCount;
    }

    public Instrument(int instrumentId, String instrumentName, int instrumentArtistCount) {
        this.instrumentId = instrumentId;
        this.instrumentName = instrumentName;
        this.instrumentArtistCount = instrumentArtistCount;
        this.instrumentVideoCount = 0;
    }

    public Instrument(String instrumentName, int instrumentArtistCount, int instrumentVideoCount) {
        this.instrumentName = instrumentName;
        this.instrumentArtistCount = instrumentArtistCount;
        this.instrumentVideoCount = instrumentVideoCount;
    }

    public Instrument(String instrumentName, int instrumentArtistCount) {
        this.instrumentName = instrumentName;
        this.instrumentArtistCount = instrumentArtistCount;
        this.instrumentVideoCount = 0;
    }

    public Instrument(String instrumentName) {
        this.instrumentName = instrumentName;
        this.instrumentArtistCount = 0;
        this.instrumentVideoCount = 0;
    }

    // Getters and Setters
    public int getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(int instrumentId) {
        this.instrumentId = instrumentId;
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }

    public int getInstrumentArtistCount() {
        return instrumentArtistCount;
    }

    public void setInstrumentArtistCount(int instrumentArtistCount) {
        this.instrumentArtistCount = instrumentArtistCount;
    }

    public int getInstrumentVideoCount() {
        return instrumentVideoCount;
    }

    public void setInstrumentVideoCount(int instrumentVideoCount) {
        this.instrumentVideoCount = instrumentVideoCount;
    }

    @NonNull
    @Override
    public String toString() {
        return "Instrument{" +
                "instrumentId=" + instrumentId +
                ", instrumentName='" + instrumentName + '\'' +
                ", instrumentArtistCount=" + instrumentArtistCount +
                ", instrumentVideoCount=" + instrumentVideoCount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Instrument that = (Instrument) o;
        return instrumentId == that.instrumentId && instrumentArtistCount == that.instrumentArtistCount && instrumentVideoCount == that.instrumentVideoCount && Objects.equals(instrumentName, that.instrumentName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instrumentId, instrumentName, instrumentArtistCount, instrumentVideoCount);
    }
}