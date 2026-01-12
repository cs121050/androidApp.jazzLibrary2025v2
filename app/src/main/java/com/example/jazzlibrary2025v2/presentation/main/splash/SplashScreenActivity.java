package com.example.jazzlibrary2025v2.presentation.main.splash;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import java.util.List;
import java.util.Map;
import java.util.Random;

import com.example.jazzlibrary2025v2.R;

import com.example.jazzlibrary2025v2.domain.model.Artist;
import com.example.jazzlibrary2025v2.domain.model.BootStrap;
import com.example.jazzlibrary2025v2.domain.model.DataCache;
import com.example.jazzlibrary2025v2.domain.model.Duration;
import com.example.jazzlibrary2025v2.domain.model.Instrument;
import com.example.jazzlibrary2025v2.domain.model.Quote;
import com.example.jazzlibrary2025v2.domain.model.Type;
import com.example.jazzlibrary2025v2.domain.model.Video;
import com.example.jazzlibrary2025v2.domain.repository.BootstrapRepository;
import com.example.jazzlibrary2025v2.domain.repository.CacheHelper;
import com.example.jazzlibrary2025v2.domain.repository.QuoteRepository;
import com.example.jazzlibrary2025v2.presentation.main.MainActivity;


public class SplashScreenActivity extends AppCompatActivity {

    private QuoteRepository quoteRepository;

    public Boolean randomSplashScreenQuoteIsRetrieved = false;
    public Boolean strapBootDataIsRetrieved = false;
    int triesToConnect = 0;

    public RelativeLayout splashScreenLayout;

    public TextView splashScreenQuoteTextView;

    public ImageView warningIcon;

    public View splashScreenProgressBar;
    public static View splash_screen_progress_bar_for_video_list;

    public List<Quote> listOfQuoteSplash;
    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);

        quoteRepository = new QuoteRepository(this);

        loadUILayoutItems();

        retrieveQuoteFromAPIInitTextAndCashIt();


        //retrieveBootstrapData();
        retrieveBootstrapAndCache();








        final Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void run() {
                final GestureDetector gestureDetector = new GestureDetector(
                        SplashScreenActivity.this, new GestureListener());

                splashScreenLayout.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        gestureDetector.onTouchEvent(event);
                        //i have to return false for the gestruredetector to not consume my longclicklistener
                        return false;
                    }
                });
                // (Optional) Direct long-click listener on the layout
                splashScreenLayout.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        handleLongPress();
                        return true;  // consume the event
                    }
                });


            }
        }, 3000);

    }












    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            handleSingleTap();
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return handleSwipe(e1, e2, velocityX);
        }
    }
    private void handleSingleTap() {

        if (!(strapBootDataIsRetrieved && randomSplashScreenQuoteIsRetrieved)) {
            if (triesToConnect % 3 == 0) {
                Toast.makeText(SplashScreenActivity.this,
                        "wait for content or\nhold-press to reconnect",
                        Toast.LENGTH_LONG).show();
                triesToConnect = 0;
            }
            triesToConnect++;
        } else {
            navigateToMainActivity();
        }
    }
    // Modified swipe handler
    private boolean handleSwipe(MotionEvent e1, MotionEvent e2, float velocityX) {
        if (e1 == null || e2 == null) return false;

        float deltaX = e2.getX() - e1.getX();
        float deltaY = e2.getY() - e1.getY();

        // Check horizontal swipe
        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            // Check swipe thresholds
            if (Math.abs(deltaX) > SWIPE_THRESHOLD &&
                    Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {

                if (deltaX > 0) { // Right swipe
                    handleSwipeLeft();
                    return true;
                } else { // Left swipe
                    handleSwipeLeft();
                    return true;
                }
            }
        }
        return false;
    }
    private void handleLongPress() {
        splash_screen_progress_bar_for_video_list.setVisibility(View.VISIBLE);
        warningIcon.setVisibility(View.INVISIBLE);
        splashScreenQuoteTextView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);

        ToastManager.showToast(SplashScreenActivity.this,"loading...", Toast.LENGTH_SHORT);
        if(!randomSplashScreenQuoteIsRetrieved) {
            Toast.makeText(SplashScreenActivity.this,"qotes & bootstrap ...", Toast.LENGTH_SHORT).show();

            retrieveQuoteFromAPIInitTextAndCashIt();
            retrieveBootstrapAndCache();

            ToastManager.cancelCurrentToast();
        } else {
            //fortose to retrieveBootstrapAndCashIt()
            Toast.makeText(SplashScreenActivity.this,"bootstrap only...", Toast.LENGTH_SHORT).show();

            retrieveBootstrapAndCache();

            ToastManager.cancelCurrentToast();

        }
    }
    // Add these new methods
    private void handleSwipeLeft() {
        // Handle swipe left
        if(randomSplashScreenQuoteIsRetrieved)
            showRandomQuote();
    }
    private void handleSwipeRight() {
//        Toast.makeText(this, "Swiped Right!", Toast.LENGTH_SHORT).show();
    }
    private void navigateToMainActivity() {
        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

//
//    public void retrieveBootstrapData() {
//        BootstrapRepository bootstrapRepo = new BootstrapRepository(getApplicationContext());
//        //splashScreenProgressBar.setVisibility(View.VISIBLE);
//        warningIcon.setVisibility(View.GONE);
//
//        bootstrapRepo.retrieveAllBootstrap(new BootstrapRepository.BootstrapResponseListener() {
//            @Override
//            public void onResponse(BootStrap bootstrapData) {
//                runOnUiThread(() -> {
//                    splashScreenProgressBar.setVisibility(View.GONE);
//                    if (bootstrapData != null) {
//                        handleBootstrapData(bootstrapData);
//                    } else {
//                        handleEmptyBootstrapData();
//                    }
//
//                });
//            }
//
//            @Override
//            public void onError(String message) {
//                splashScreenProgressBar.setVisibility(View.VISIBLE);
//                Toast.makeText(SplashScreenActivity.this, "Bootstrap Error: " + message, Toast.LENGTH_SHORT).show();
//                connectionErrorHandler();
//            }
//        });
//
//
//    }








    public void retrieveQuoteFromAPIInitTextAndCashIt() {

        // Show cached data immediately if available
        if (CacheHelper.isCacheValid(this)) {
            List<Quote> cachedQuotes = CacheHelper.loadQuotes(this);
            if (cachedQuotes != null && !cachedQuotes.isEmpty()) {
                handleCachedOrFreshQuotes(cachedQuotes);

                Toast.makeText(SplashScreenActivity.this, "Data from cashe.", Toast.LENGTH_SHORT).show();
                return; // Exit after showing cached data
            }
        }
        fetchFreshQuotes();
    }
    private void fetchFreshQuotes() {
        // Then fetch fresh data
        quoteRepository.retrieveAllQuotes(new QuoteRepository.QuoteResponseListener() {
            @Override
            public void onResponse(List<Quote> listOfQuote) {
                if (listOfQuote != null && !listOfQuote.isEmpty()) {
                    Toast.makeText(SplashScreenActivity.this, "Data from api.", Toast.LENGTH_SHORT).show();

                    handleCachedOrFreshQuotes(listOfQuote);

                }
            }
            @Override
            public void onError(String message) {
                // Only show error if no cached data
                if (listOfQuoteSplash == null || listOfQuoteSplash.isEmpty()) {
                splashScreenProgressBar.setVisibility(View.VISIBLE);

                Toast.makeText(SplashScreenActivity.this,  message, Toast.LENGTH_SHORT).show();
                connectionErrorHandler();
               }
            }
        });
    }
    private void handleCachedOrFreshQuotes(List<Quote> cachedQuotes) {
        runOnUiThread(() -> {
            listOfQuoteSplash = cachedQuotes;
            randomSplashScreenQuoteIsRetrieved = true;

            showRandomQuote();
        });
    }
    private void showRandomQuote() {
        if (listOfQuoteSplash != null && !listOfQuoteSplash.isEmpty()) {

            splashScreenProgressBar.setVisibility(View.GONE);
            warningIcon.setVisibility(View.GONE);

            int randomQuoteId = randomIdGenerator(listOfQuoteSplash.size());
            Quote randomQuote = listOfQuoteSplash.get(randomQuoteId);
            splashScreenQuoteTextView.setText(randomQuote.getQuoteText());
        }
        else{
            Toast.makeText(SplashScreenActivity.this, "no quotes : longpress", Toast.LENGTH_SHORT).show();
        }
    }











    private void retrieveBootstrapAndCache() {
        // Show loading state
        splashScreenProgressBar.setVisibility(View.VISIBLE);
        warningIcon.setVisibility(View.GONE);

        // 1. Check valid cache first
        if (CacheHelper.isBootstrapCacheValid(this)) {
            BootStrap cachedBootstrap = CacheHelper.loadBootstrap(this);
            if (cachedBootstrap != null) {
                runOnUiThread(() -> {
                    splashScreenProgressBar.setVisibility(View.GONE);
                    Toast.makeText(this, "Using cached data", Toast.LENGTH_SHORT).show();
                    handleBootstrapData(cachedBootstrap);
                });
                return;
            }
        }

        // 2. No valid cache - fetch fresh data
        BootstrapRepository bootstrapRepo = new BootstrapRepository(this);
        bootstrapRepo.retrieveAllBootstrap(new BootstrapRepository.BootstrapResponseListener() {
            @Override
            public void onResponse(BootStrap bootstrap) {
                // Save to cache
                CacheHelper.saveBootstrap(SplashScreenActivity.this, bootstrap);

                runOnUiThread(() -> {
                    splashScreenProgressBar.setVisibility(View.GONE);
                    Toast.makeText(SplashScreenActivity.this,
                            "Fresh data loaded", Toast.LENGTH_SHORT).show();
                    handleBootstrapData(bootstrap);
                });
            }

            @Override
            public void onError(String message) {
                runOnUiThread(() -> {
                    splashScreenProgressBar.setVisibility(View.GONE);

                    // Fallback to stale cache if available
                    BootStrap staleData = CacheHelper.loadBootstrap(SplashScreenActivity.this);
                    if (staleData != null) {
                        Toast.makeText(SplashScreenActivity.this,
                                "Using outdated data", Toast.LENGTH_SHORT).show();
                        handleBootstrapData(staleData);
                    } else {
                        // No data at all
                        connectionErrorHandler();
                        Toast.makeText(SplashScreenActivity.this,
                                "Error: " + message, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }



    private void handleBootstrapData(BootStrap data) {

        // Update UI
        TESTupdateUiWithBootstrapInfo(data.getCounts(), data.getArtistList(), data.getInstrumentList(), data.getTypeList(), data.getDurationList(), data.getVideoList(), data.getVideoFilters(), data.getArtistFilters());

        DataCache.getInstance().setBootstrapData(data);
        //or retrieve data from cashe this way : "BootStrap cachedData = DataCache.getInstance().getBootstrapData();"


        strapBootDataIsRetrieved = true;
        splashScreenProgressBar.setVisibility(View.INVISIBLE);
        splash_screen_progress_bar_for_video_list.setVisibility(View.INVISIBLE);

    }



    //util
    private void TESTupdateUiWithBootstrapInfo(Map<String, Integer> counts, List<Artist> artistList, List<Instrument> instrumentList, List<Type> typeList, List<Duration> durationList, List<Video> videoList, Map<String, Map<String, List<Video>>> videoMap, Map<String, Map<String, List<Artist>>> artistMap) {

        //splashScreenQuoteTextView.setText(videoList.get(2).toString());
        //splashScreenQuoteTextView.setText(durationList.get(2).toString());
        //splashScreenQuoteTextView.setText(instrumentList.get(6).toString());
        //splashScreenQuoteTextView.setText(typeList.get(2).toString());
        //splashScreenQuoteTextView.setText(artistList.get(6).toString());

        //splashScreenQuoteTextView.setText(artistMap.toString());
        //splashScreenQuoteTextView.setText(videoMap.toString());


        //Toast.makeText(SplashScreenActivity.this, message, Toast.LENGTH_SHORT).show();

    }







    public void connectionErrorHandler(){

        splashScreenProgressBar.setVisibility(View.GONE);
        splash_screen_progress_bar_for_video_list.setVisibility(View.GONE);
        warningIcon.setVisibility(View.VISIBLE);
        splashScreenQuoteTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        splashScreenQuoteTextView.setText("Server can't be reached\npress-hold to refresh");

    }

    private void loadUILayoutItems(){
        Log.d(TAG,"initViews: Started");

        splashScreenLayout = findViewById(R.id.splash_screen);
        splashScreenQuoteTextView = findViewById(R.id.splash_screen_quote_TextView);
        warningIcon = findViewById(R.id.warning_icon);
        splashScreenProgressBar = findViewById(R.id.splash_screen_progress_bar);
        splash_screen_progress_bar_for_video_list  = findViewById(R.id.splash_screen_progress_bar_for_video_list);


    }
    //util
    public static int randomIdGenerator(int maxID){

        Random r = new Random();
        int low = 1;
        int high = maxID;
        int randomID = r.nextInt(high-low) + low;

        return randomID;
    }


    //util
    public class ToastManager {

        //        // Instead of:
//Toast.makeText(context, message, duration).show();
//
//// Use:
//ToastManager.showToast(context, message, duration);
        private static Toast currentToast;

        public static void showToast(Context context, String message, int duration) {
            // Cancel previous toast if exists
            if (currentToast != null) {
                currentToast.cancel();
            }

            // Create and show new toast
            currentToast = Toast.makeText(context.getApplicationContext(), message, duration);
            currentToast.show();
        }

        public static void cancelCurrentToast() {
            if (currentToast != null) {
                currentToast.cancel();
                currentToast = null;
            }
        }
    }



}



