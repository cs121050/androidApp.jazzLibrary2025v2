package com.example.jazzlibrary2025v2.data.remote;

import static com.example.jazzlibrary2025v2.utils.GlobalConfig.APIName;
import static com.example.jazzlibrary2025v2.utils.GlobalConfig.publicServersIP;

import com.example.jazzlibrary2025v2.domain.model.BootStrap;
import com.example.jazzlibrary2025v2.domain.repository.BootstrapDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitAPIClient {

    private static final String BASE_URL = "http://"+publicServersIP+":8080/"+ APIName ;
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(BootStrap.class, new BootstrapDeserializer())
                    .create();

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client) // ← FIXED: Use the configured client //.client(new OkHttpClient.Builder().build())   //                     .client(client) // ← FIXED: Use the configured client
                    .build();


        }
        return retrofit;
    }
}
