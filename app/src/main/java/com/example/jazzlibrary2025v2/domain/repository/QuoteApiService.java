package com.example.jazzlibrary2025v2.domain.repository;

import com.example.jazzlibrary2025v2.domain.model.Quote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface QuoteApiService {


    @GET("quoteService/randomQuote")
    Call<Quote> getRandomQuote();  // Changed from Call<List<Quote>>

    @GET("quoteService/byId/search")
    Call<Quote> getQuoteById(@Path("quoteId") int quoteId);

    @GET("quoteService/byText/search")
    Call<List<Quote>> getQuotesByText(@Query("quote_text") String quoteText);

    @GET("quoteService/byArtistId/search")
    Call<List<Quote>> getQuotesByArtistId(@Query("artist_id") int artistId);

    @GET("quoteService/all")
    Call<List<Quote>> getAllQuotes();}