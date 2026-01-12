package com.example.jazzlibrary2025v2.domain.repository.secondaryRep;


import static com.example.jazzlibrary2025v2.utils.GlobalConfig.APIName;
import static com.example.jazzlibrary2025v2.utils.GlobalConfig.publicServersIP;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.jazzlibrary2025v2.data.remote.ApiClient;
import com.example.jazzlibrary2025v2.domain.model.Type;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TypeRepository {
    private static final String BASE_URI = "http://"+publicServersIP+":8080/"+ APIName ;
    private static final String TYPE_SERVICE_URI = "typeService/";

    private static final String TYPE_SERVICE_BY_ARTIST_ID_URI = "byArtistId/search";
    private static final String TYPE_SERVICE_BY_ARTIST_ID_AND_DURATION_ID_URI = "byArtistIdAndDurationId/search";
    private static final String TYPE_SERVICE_BY_DURATION_ID_URI = "byDurationId/search";
    private static final String TYPE_SERVICE_BY_TYPE_ID_URI = "byId/search";
    private static final String TYPE_SERVICE_BY_INSTRUMENT_ID_URI = "byInstrumentId/search";
    private static final String TYPE_SERVICE_BY_INSTRUMENT_ID_AND_DURATION_ID_URI = "byInstrumentIdAndDurationId/search";
    private static final String TYPE_SERVICE_BY_TYPE_NAME_URI = "byName/search";
    private static final String TYPE_SERVICE_BY_TYPE_ID_AND_DURATION_ID_URI = "byTypeIdAndDurationId/search";

    private static final String ALL_TYPE_URI = "all";

    private final Context context;

    public TypeRepository(Context context) {
        this.context = context;
    }

    public interface TypeResponseListener  {
        void onResponse(List<Type> listOfType);

        void onError(String message);
    }

    public void retrieveVideoByArtistId(int artistId, final TypeResponseListener responseListener) {
        String url = BASE_URI + TYPE_SERVICE_URI + TYPE_SERVICE_BY_ARTIST_ID_URI + "?artist_id=" + artistId;

        requestBody(url,responseListener);
    }

    public void retrieveVideoByArtistIdAndDurationId(int artistId, int durationId, final TypeResponseListener responseListener) {
        String url = BASE_URI + TYPE_SERVICE_URI + TYPE_SERVICE_BY_ARTIST_ID_AND_DURATION_ID_URI + "?artist_id=" + artistId
                                                                                                 + "&duration_id  =" + durationId;

        requestBody(url,responseListener);
    }

    public void retrieveVideoByDurationId(int durationId, final TypeResponseListener responseListener) {
        String url = BASE_URI + TYPE_SERVICE_URI + TYPE_SERVICE_BY_DURATION_ID_URI + "?duration_id=" + durationId;

        requestBody(url,responseListener);
    }

    public void retrieveVideoByTypeId(int typeId, final TypeResponseListener responseListener) {
        String url = BASE_URI + TYPE_SERVICE_URI + TYPE_SERVICE_BY_TYPE_ID_URI + "?type_id=" + typeId;

        requestBody(url,responseListener);
    }

    public void retrieveVideoByInstrumentId(int instrumentId, final TypeResponseListener responseListener) {
        String url = BASE_URI + TYPE_SERVICE_URI + TYPE_SERVICE_BY_INSTRUMENT_ID_URI + "?instrument_id=" + instrumentId;

        requestBody(url,responseListener);
    }

    public void retrieveVideoByInstrumentIdAndDurationId(int instrumentId, int durationId, final TypeResponseListener responseListener) {
        String url = BASE_URI + TYPE_SERVICE_URI + TYPE_SERVICE_BY_INSTRUMENT_ID_AND_DURATION_ID_URI + "?instrument_id=" + instrumentId
                                                                                                       + "&duration_id  =" + durationId;

        requestBody(url,responseListener);
    }

    public void retrieveVideoByTypeName(String typeName , final TypeResponseListener responseListener) {
        String url = BASE_URI + TYPE_SERVICE_URI + TYPE_SERVICE_BY_TYPE_NAME_URI + "?type_name =" + typeName;

        requestBody(url,responseListener);
    }

    public void retrieveVideoByTypeIdAndDurationId(int typeId, int durationId, final TypeResponseListener responseListener) {
        String url = BASE_URI + TYPE_SERVICE_URI + TYPE_SERVICE_BY_TYPE_ID_AND_DURATION_ID_URI + "?type_id=" + typeId
                                                                                               + "&duration_id  =" + durationId;

        requestBody(url,responseListener);
    }

    public void retrieveAllVideos(final TypeResponseListener responseListener) {
        String url = BASE_URI + TYPE_SERVICE_URI + ALL_TYPE_URI;

        requestBody(url,responseListener);
    }


    public void requestBody(String url, final TypeResponseListener responseListener){

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    List<Type> typeList = new ArrayList<>();
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject typeJson = response.getJSONObject(i);
                            Type type = new Type();
                            type.setTypeId(typeJson.getInt("type_id")); // Use consistent naming
                            type.setTypeName(typeJson.getString("type_name"));
                            type.setTypeVideoCount(typeJson.getInt("type_video_count"));

                            typeList.add(type);
                        }
                        responseListener.onResponse(typeList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        responseListener.onError("Parsing error: " + e.getMessage());
                    }
                },
                error -> responseListener.onError("Network error: " + error.getMessage()));

        ApiClient.getInstance(context).addToRequestQueue(request);
    }


}
