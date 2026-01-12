package com.example.jazzlibrary2025v2.domain.model;

import java.util.List;

public class ResultSetForUI {

    List<Artist> artistList;
    List<Instrument> instrumentList;
    List<Type> typeList;
    List<Duration> durationList;
    List<Video> videoList;

    public List<Artist> getArtistList() {
        return artistList;
    }

    public void setArtistList(List<Artist> artistList) {
        this.artistList = artistList;
    }

    public List<Instrument> getInstrumentList() {
        return instrumentList;
    }

    public void setInstrumentList(List<Instrument> instrumentList) {
        this.instrumentList = instrumentList;
    }

    public List<Type> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<Type> typeList) {
        this.typeList = typeList;
    }

    public List<Duration> getDurationList() {
        return durationList;
    }

    public void setDurationList(List<Duration> durationList) {
        this.durationList = durationList;
    }

    public List<Video> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<Video> videoList) {
        this.videoList = videoList;
    }
}
