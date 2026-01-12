package com.example.jazzlibrary2025v2.domain.repository.secondaryRep;


import android.content.Context;

import com.example.jazzlibrary2025v2.data.remote.RetrofitAPIClient;
import com.example.jazzlibrary2025v2.domain.model.Artist;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


// ArtistRepository.java
public class ArtistRepository {
    private final ArtistApiService apiService;

    public ArtistRepository(Context context) {
        apiService = RetrofitAPIClient.getClient().create(ArtistApiService.class);
    }

    public interface ArtistResponseListener {
        void onResponse(List<Artist> artists);
        void onError(String message);
    }

    public void retrieveAllArtists(ArtistResponseListener listener) {
        apiService.getAllArtists().enqueue(new Callback<List<Artist>>() {
            @Override
            public void onResponse(Call<List<Artist>> call, Response<List<Artist>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.onResponse(response.body());
                } else {
                    listener.onError("Server error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Artist>> call, Throwable t) {
                listener.onError("Network error: " + t.getMessage());
            }
        });
    }


}


