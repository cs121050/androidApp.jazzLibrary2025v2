package com.example.jazzlibrary2025v2.domain.model;


import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Quote {

    @SerializedName("quote_id")
    private int quoteId;
    @SerializedName("quote_text")
    private String quoteText;
    @SerializedName("artist_id")
    private int artistId;

    // Constructors
    public Quote() {
    }

    public Quote(int quoteId, String quoteText, int artistId) {
        this.quoteId = quoteId;
        this.quoteText = quoteText;
        this.artistId = artistId;
    }

    public Quote(String quoteText, int artistId) {
        this.quoteText = quoteText;
        this.artistId = artistId;
    }

    public Quote(String quoteText) {
        this.quoteText = quoteText;
        this.artistId = 0;
    }

    // Getters and Setters
    public int getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(int quoteId) {
        this.quoteId = quoteId;
    }

    public String getQuoteText() {
        return quoteText;
    }

    public void setQuoteText(String quoteText) {
        this.quoteText = quoteText;
    }

    public void setQuoteText(String quoteText, int artistId) {
        this.quoteText = quoteText;
        this.artistId = artistId;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    @NotNull
    @Override
    public String toString() {
        return "Quote{" +
                "quoteId=" + quoteId +
                ", quoteText='" + quoteText + '\'' +
                ", artistId=" + artistId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quote quote = (Quote) o;
        return quoteId == quote.quoteId && artistId == quote.artistId && Objects.equals(quoteText, quote.quoteText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quoteId, quoteText, artistId);
    }
}