package com.example.jazzlibrary2025v2.domain.repository;

import com.example.jazzlibrary2025v2.domain.model.BootStrap;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BootStrapApiService {

    @GET("bootStrapService/all")
    Call<BootStrap> getAllBootstrapData();

}
