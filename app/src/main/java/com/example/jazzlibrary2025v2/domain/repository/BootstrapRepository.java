package com.example.jazzlibrary2025v2.domain.repository;

import android.content.Context;

import com.example.jazzlibrary2025v2.data.remote.RetrofitAPIClient;
import com.example.jazzlibrary2025v2.domain.model.BootStrap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;


public class BootstrapRepository {
    private final BootStrapApiService apiService;


    public BootstrapRepository(Context context) {
        // Always use application context to prevent leaks
        apiService = RetrofitAPIClient.getClient().create(BootStrapApiService.class);

    }

    public interface BootstrapResponseListener {
        void onResponse(BootStrap bootstrapData);
        void onError(String message);
    }

    public void retrieveAllBootstrap(BootstrapResponseListener listener) {
        apiService.getAllBootstrapData().enqueue(new Callback<BootStrap>() {
            @Override
            public void onResponse(Call<BootStrap> call, Response<BootStrap> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.onResponse(response.body());
                } else {
                    listener.onError("Server error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<BootStrap> call, Throwable t) {
                listener.onError("Network error: " + t.getMessage());
            }
        });
    }


}
