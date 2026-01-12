package com.example.jazzlibrary2025v2.domain.repository.secondaryRep;

import static com.example.jazzlibrary2025v2.utils.GlobalConfig.APIName;
import static com.example.jazzlibrary2025v2.utils.GlobalConfig.publicServersIP;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.jazzlibrary2025v2.data.remote.ApiClient;
import com.example.jazzlibrary2025v2.domain.model.Duration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DurationRepository {

    private static final String BASE_URI = "http://"+publicServersIP+":8080/"+ APIName ;
    private static final String DURATION_SERVICE_URI = "durationService/";

    private static final String DURATION_BY_TYPE_ID_AND_INSTRUMENT_ID_URI = "byTypeIdAndInstrumentId/search";
    private static final String DURATION_BY_TYPE_ID_AND_ARTIST_ID_URI = "byTypeIdAndArtistId/search";
    private static final String DURATION_BY_TYPE_ID_URI = "byTypeId/search";
    private static final String DURATION_BY_DURATION_NAME_URI = "byName/search";
    private static final String DURATION_BY_INSTRUMENT_ID_URI = "byInstrumentId/search";
    private static final String DURATION_BY_DURATION_ID_URI = "byId/search";
    private static final String DURATION_BY_ARTIST_ID_URI = "byArtistId/search";

    private static final String ALL_DURATION_URI = "all";

    private final Context context;

    public DurationRepository(Context context) {
        this.context = context;
    }

    public interface DurationResponseListener  {
        void onResponse(List<Duration> listOfDuration);

        void onError(String message);
    }


    public void retrieveDurationByTypeIdAndInstrumentId(int typeId, int instrumentId, final DurationResponseListener responseListener) {
        String url = BASE_URI + DURATION_SERVICE_URI + DURATION_BY_TYPE_ID_AND_INSTRUMENT_ID_URI + "?type_id=" + typeId
                                                                                                 + "&instrument_id  =" + instrumentId;

        requestBody(url,responseListener);
    }

    public void retrieveDurationByTypeIdAndArtistId(int typeId, int artistId, final DurationResponseListener responseListener) {
        String url = BASE_URI + DURATION_SERVICE_URI + DURATION_BY_TYPE_ID_AND_ARTIST_ID_URI + "?type_id=" + typeId
                                                                                             + "&artist_id  =" + artistId;

        requestBody(url,responseListener);
    }

    public void retrieveDurationByTypeId(int typeId, final DurationResponseListener responseListener) {
        String url = BASE_URI + DURATION_SERVICE_URI + DURATION_BY_TYPE_ID_URI + "?type_id  =" + typeId;

                requestBody(url,responseListener);
    }

    public void retrieveDurationByDurationName(String durationName, final DurationResponseListener responseListener) {
        String url = BASE_URI + DURATION_SERVICE_URI + DURATION_BY_DURATION_NAME_URI + "?duration_name =" + durationName;

        requestBody(url,responseListener);
    }

    public void retrieveDurationByInstrumentId(int instrumentId, final DurationResponseListener responseListener) {
        String url = BASE_URI + DURATION_SERVICE_URI + DURATION_BY_INSTRUMENT_ID_URI + "?instrument_id=" + instrumentId;

        requestBody(url,responseListener);
    }

    public void retrieveDurationByDurationId(int durationId,final DurationResponseListener responseListener) {
        String url = BASE_URI + DURATION_SERVICE_URI + DURATION_BY_DURATION_ID_URI + "?duration_id=" + durationId;

        requestBody(url,responseListener);
    }

    public void retrieveDurationByArtistId(int artistId, final DurationResponseListener responseListener) {
        String url = BASE_URI + DURATION_SERVICE_URI + DURATION_BY_ARTIST_ID_URI + "?artist_id=" + artistId;

        requestBody(url,responseListener);
    }

    public void retrieveAllDurations(final DurationResponseListener responseListener) {
        String url = BASE_URI + DURATION_SERVICE_URI + ALL_DURATION_URI;

        requestBody(url,responseListener);
    }



    public void requestBody(String url, final DurationResponseListener responseListener){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    List<Duration> durationList = new ArrayList<>();
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject durationJson = response.getJSONObject(i);
                            Duration duration = new Duration();
                            duration.setDurationId(durationJson.getInt("duration_id")); // Use consistent naming
                            duration.setDurationName(durationJson.getString("duration_name"));
                            duration.setDurationVideoCount(durationJson.getInt("duration_video_count"));

                            durationList.add(duration);
                        }
                        responseListener.onResponse(durationList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        responseListener.onError("Parsing error: " + e.getMessage());
                    }
                },
                error -> responseListener.onError("Network error: " + error.getMessage()));

        ApiClient.getInstance(context).addToRequestQueue(request);
    }















}
