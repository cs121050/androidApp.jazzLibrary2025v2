package com.example.jazzlibrary2025v2.domain.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.jazzlibrary2025v2.domain.model.BootStrap;
import com.example.jazzlibrary2025v2.domain.model.Quote;
import com.example.jazzlibrary2025v2.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeUnit;


//TODO            // Chat GPT  Isues found with my client !!
/*

    1. Thread Safety for Caching (9/10)

    Problem: File I/O (CacheHelper.loadQuotes()) runs on the main thread → risk of ANR crashes.

    Fix: Use AsyncTask, RxJava, or Kotlin Coroutines for file/network operations.

    Saves You From: App freezes during caching, Play Store rejections for ANRs.



    2. Error Handling & Retry Logic (8/10)

    Problem: If API fails, users only see a toast. No way to retry beyond long-press.

            Fix: Add a "Retry" button/UI and exponential backoff for API retries.

    Saves You From: Frustrated users stuck on splash screen.



    3. Architecture (ViewModel + Repository) (8/10)

    Problem: Business logic in SplashScreenActivity → hard to test, breaks on rotation.

            Fix: Move logic to a ViewModel and Repository (MVVM architecture).

    Saves You From: Memory leaks, crashes during configuration changes.




    4. Cache Invalidation Strategy (7/10)

    Problem: Cache lasts 24 hours always. Users can’t force a refresh (except long-press).

    Fix: Add "pull to refresh" in the app and cache TTL (time-to-live) settings.

    Saves You From: Users seeing stale data indefinitely.




    5. Network Check Before API Calls (7/10)

    Problem: No check for internet → API calls fail immediately when offline.

    Fix: Add ConnectivityManager check before API calls.

    Saves You From: Wasted API attempts and battery drain.



    6. Replace SharedPreferences with Room (6/10)

    Problem: SharedPreferences for BootStrap data is inefficient for large/complex data.

    Fix: Use Room Database for structured caching.

    Saves You From: Data corruption, slow read/writes for large datasets.




    7. Dependency Injection (Hilt) (5/10)

    Problem: Manual new BootstrapRepository() → hard to test/modify.

            Fix: Use Hilt for dependency injection.

    Saves You From: Tight coupling, boilerplate code.




    8. Testing (Unit & Instrumented) (7/10)

    Problem: No tests → risky to make changes without breaking things.

            Fix: Add tests for:

    Cache validity checks

    API failure scenarios

    Bootstrap parsing

    Saves You From: Regression bugs during updates.




    9. Analytics for Caching (5/10)

    Problem: No insight into cache hit/miss rates or API failures.

            Fix: Log cache events to Firebase Analytics or similar.

    Saves You From: "Blind" debugging of caching issues.




    10. Edge Case Handling (6/10)

    Problem: Unhandled cases:

    Empty API responses

    Screen rotation during loading

    Partial cache corruption

    Fix: Add fallbacks and lifecycle-aware loading.

    Saves You From: Random crashes in production.


*/


public class CacheHelper {
    private static final String QUOTES_CACHE_FILE = "quotes_cache.json";
    private static final long CACHE_VALIDITY_MS = 24 * 60 * 60 * 1000; // 24 hours


        // Add this new method to check cache validity
        public static boolean isCacheValid(Context context) {
            File file = new File(context.getFilesDir(), QUOTES_CACHE_FILE);
            return file.exists() &&
                    (System.currentTimeMillis() - file.lastModified()) < CACHE_VALIDITY_MS;
        }

    public static void saveQuotes(Context context, List<Quote> quotes) {
        new Thread(() -> {
            File file = new File(context.getFilesDir(), QUOTES_CACHE_FILE);
            Gson gson = new Gson();
            try (FileWriter writer = new FileWriter(file)) {
                gson.toJson(quotes, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static List<Quote> loadQuotes(Context context) {
        File file = new File(context.getFilesDir(), QUOTES_CACHE_FILE);
        if (!file.exists()) return null;

        // Check cache validity
        if (System.currentTimeMillis() - file.lastModified() > CACHE_VALIDITY_MS) {
            file.delete();
            return null;
        }

        try (FileReader reader = new FileReader(file)) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Quote>>(){}.getType();
            return gson.fromJson(reader, type);
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Bootstrap cache methods
    public static void saveBootstrap(Context context, BootStrap bootstrap) {
        SharedPreferences prefs = context.getSharedPreferences(
                Constants.PREFS_NAME,
                Context.MODE_PRIVATE
        );

        Gson gson = new Gson();
        prefs.edit()
                .putString(Constants.KEY_BOOTSTRAP, gson.toJson(bootstrap))
                .putLong(Constants.KEY_BOOTSTRAP_TIME, System.currentTimeMillis())
                .apply();
    }

    public static BootStrap loadBootstrap(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(
                Constants.PREFS_NAME,
                Context.MODE_PRIVATE
        );
        String json = prefs.getString(Constants.KEY_BOOTSTRAP, null);
        return json != null ? new Gson().fromJson(json, BootStrap.class) : null;
    }

    public static boolean isBootstrapCacheValid(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(
                Constants.PREFS_NAME,
                Context.MODE_PRIVATE
        );
        long lastCacheTime = prefs.getLong(Constants.KEY_BOOTSTRAP_TIME, 0);
        return (System.currentTimeMillis() - lastCacheTime) < TimeUnit.HOURS.toMillis(24);
    }


}