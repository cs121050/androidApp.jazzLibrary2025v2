package com.example.jazzlibrary2025v2.domain.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BootStrap {

//    @SerializedName("quoteList")
//    private List<Quote> quoteList;
    @SerializedName("artistList")
    private List<Artist> artistList;
    @SerializedName("instrumentList")
    private List<Instrument> instrumentList;
    @SerializedName("videoList")
    private List<Video> videoList;
    @SerializedName("typeList")
    private List<Type> typeList;
    @SerializedName("durationList")
    private List<Duration> durationList;
    @SerializedName("artistFilters")
    private Map<String, Map<String, List<Artist>>> artistFilters;
    //TODO// KANE TO HashSet<>,  ALLA3E KAI OLO TO CLIENT NA PERNEI HashSet<> APO TO API
    @SerializedName("videoFilters")
    private Map<String, Map<String, List<Video>>> videoFilters;
    //TODO// KANE TO HashSet<>,  ALLA3E KAI OLO TO CLIENT NA PERNEI HashSet<> APO TO API


    @SerializedName("counts")
    private Map<String, Integer> counts;

    public Map<String, Map<String, List<Video>>> getVideoFilters() {
        return videoFilters;
    }

    public void setVideoFilters(Map<String, Map<String, List<Video>>> videoFilters) {
        this.videoFilters = videoFilters;
    }

    public List<Duration> getDurationList() {
        return durationList;
    }

    public void setDurationList(List<Duration> durationList) {
        this.durationList = durationList;
    }

    public List<Type> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<Type> typeList) {
        this.typeList = typeList;
    }

    public List<Video> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<Video> videoList) {
        this.videoList = videoList;
    }

    public List<Instrument> getInstrumentList() {
        return instrumentList;
    }

    public void setInstrumentList(List<Instrument> instrumentList) {
        this.instrumentList = instrumentList;
    }

    public Map<String, Integer> getCounts() {
        return counts;
    }

    public void setCounts(Map<String, Integer> counts) {
        this.counts = counts;
    }

    public List<Artist> getArtistList() {
        return artistList;
    }

    public void setArtistList(List<Artist> artistList) {
        this.artistList = artistList;
    }

    public Map<String, Map<String, List<Artist>>> getArtistFilters() {
        return artistFilters;
    }

    public void setArtistFilters(Map<String, Map<String, List<Artist>>> artistFilters) {
        this.artistFilters = artistFilters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BootStrap bootStrap = (BootStrap) o;
        return Objects.equals(artistList, bootStrap.artistList) && Objects.equals(instrumentList, bootStrap.instrumentList) && Objects.equals(videoList, bootStrap.videoList) && Objects.equals(typeList, bootStrap.typeList) && Objects.equals(durationList, bootStrap.durationList) && Objects.equals(artistFilters, bootStrap.artistFilters) && Objects.equals(videoFilters, bootStrap.videoFilters) && Objects.equals(counts, bootStrap.counts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artistList, instrumentList, videoList, typeList, durationList, artistFilters, videoFilters, counts);
    }

    @Override
    public String toString() {
        return "BootStrap{" +
                "artistList=" + artistList +
                ", instrumentList=" + instrumentList +
                ", videoList=" + videoList +
                ", typeList=" + typeList +
                ", durationList=" + durationList +
                ", artistFilters=" + artistFilters +
                ", videoFilters=" + videoFilters +
                ", counts=" + counts +
                '}';
    }
}
