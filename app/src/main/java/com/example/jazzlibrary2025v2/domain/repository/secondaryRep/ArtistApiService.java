package com.example.jazzlibrary2025v2.domain.repository.secondaryRep;

import com.example.jazzlibrary2025v2.domain.model.Artist;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ArtistApiService {
    @GET("artistService/all")
    Call<List<Artist>> getAllArtists();

}