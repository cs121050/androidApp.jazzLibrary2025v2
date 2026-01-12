package com.example.jazzlibrary2025v2.domain.repository;


import android.content.Context;

import com.example.jazzlibrary2025v2.data.remote.RetrofitAPIClient;
import com.example.jazzlibrary2025v2.domain.model.Quote;
import com.example.jazzlibrary2025v2.domain.repository.QuoteApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class QuoteRepository {
    private final QuoteApiService apiService;
    private final Context context;


    public QuoteRepository(Context context) {
        this.context = context.getApplicationContext();
        // Initialize Retrofit
        apiService = RetrofitAPIClient.getClient().create(QuoteApiService.class);
    }

    public interface QuoteResponseListener {
        void onResponse(List<Quote> listOfQuote);
        void onError(String message);
    }

    public void retrieveRandomQuote(QuoteResponseListener responseListener) {
        apiService.getRandomQuote().enqueue(new Callback<Quote>() {  // Changed to Quote
            @Override
            public void onResponse(Call<Quote> call, Response<Quote> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Wrap single quote in a list
                    List<Quote> quotes = new ArrayList<>();
                    quotes.add(response.body());
                    responseListener.onResponse(quotes);
                } else {
                    responseListener.onError("Server error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Quote> call, Throwable t) {
                responseListener.onError("Network error: " + t.getMessage());
            }
        });
    }

    public void retrieveAllQuotes(QuoteResponseListener responseListener) {

        List<Quote> cachedQuotes = CacheHelper.loadQuotes(context);
        if (cachedQuotes != null && !cachedQuotes.isEmpty()) {
            responseListener.onResponse(cachedQuotes);
        }

        // Then fetch from API
        apiService.getAllQuotes().enqueue(new Callback<List<Quote>>() {
            @Override
            public void onResponse(Call<List<Quote>> call, Response<List<Quote>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Quote> quotes = response.body();
                    CacheHelper.saveQuotes(context, quotes);
                    responseListener.onResponse(quotes);
                } else {
                    if (cachedQuotes == null) {
                        responseListener.onError("Server error: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Quote>> call, Throwable t) {
                if (cachedQuotes == null) {
                    responseListener.onError("Network error: " + t.getMessage());
                }
            }
        });
    }

    // Similarly add the other methods for retrieveQuoteById, retrieveQuoteByText, etc.
}
