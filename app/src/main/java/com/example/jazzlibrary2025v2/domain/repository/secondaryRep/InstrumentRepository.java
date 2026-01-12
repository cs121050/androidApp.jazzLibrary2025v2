package com.example.jazzlibrary2025v2.domain.repository.secondaryRep;

import static com.example.jazzlibrary2025v2.utils.GlobalConfig.APIName;
import static com.example.jazzlibrary2025v2.utils.GlobalConfig.publicServersIP;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.jazzlibrary2025v2.data.remote.ApiClient;
import com.example.jazzlibrary2025v2.domain.model.Instrument;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InstrumentRepository {

    private static final String BASE_URI = "http://"+publicServersIP+":8080/"+ APIName ;
    private static final String INSTRUMENT_SERVICE_URI = "instrumentService/";

    private static final String INSTRUMENT_BY_TYPE_ID_AND_DURATION_ID_URI = "byTypeIdAndDurationId/search";
    private static final String INSTRUMENT_BY_TYPE_ID_URI = "byTypeId/search";
    private static final String INSTRUMENT_BY_INSTRUMENT_NAME_URI = "byName/search";
    private static final String INSTRUMENT_BY_INSTRUMENT_ID_URI = "byId/search";
    private static final String INSTRUMENT_BY_DURATION_ID_URI = "byDurationId/search";
    private static final String ALL_INSTRUMENTS_URI = "all";

    private final Context context;

    public InstrumentRepository(Context context) {
        this.context = context;
    }

    public interface InstrumentResponseListener  {
        void onResponse(List<Instrument> listOfInstrument);

        void onError(String message);
    }


    public void retrieveInstrumentByTypeIdAndDurationId(int typeId, int durationId, final InstrumentResponseListener responseListener) {
        String url = BASE_URI + INSTRUMENT_SERVICE_URI + INSTRUMENT_BY_TYPE_ID_AND_DURATION_ID_URI + "?type_id=" + typeId
                                                                                                   + "&duration_id  =" + durationId;

        requestBody(url,responseListener);

    }

    public void retrieveInstrumentByTypeId(int typeId, final InstrumentResponseListener responseListener) {
        String url = BASE_URI + INSTRUMENT_SERVICE_URI + INSTRUMENT_BY_TYPE_ID_URI + "?type_id=" + typeId;

        requestBody(url,responseListener);

    }

    public void retrieveInstrumentByInstrumentName(int instrumentName,final InstrumentResponseListener responseListener) {
        String url = BASE_URI + INSTRUMENT_SERVICE_URI + INSTRUMENT_BY_INSTRUMENT_NAME_URI + "?instrument_name =" + instrumentName;

        requestBody(url,responseListener);

    }

    public void retrieveInstrumentByInstrumentId(int instrumentId, final InstrumentResponseListener responseListener) {
        String url = BASE_URI + INSTRUMENT_SERVICE_URI + INSTRUMENT_BY_INSTRUMENT_ID_URI + "?instrument_id =" + instrumentId;

        requestBody(url,responseListener);

    }

    public void retrieveInstrumentByDurationId(int durationId, final InstrumentResponseListener responseListener) {
        String url = BASE_URI + INSTRUMENT_SERVICE_URI + INSTRUMENT_BY_DURATION_ID_URI + "?durartion_id =" + durationId;

        requestBody(url,responseListener);

    }

    public void retrieveAllInstruments(final InstrumentResponseListener responseListener) {
        String url = BASE_URI + INSTRUMENT_SERVICE_URI + ALL_INSTRUMENTS_URI;

        requestBody(url,responseListener);

    }

    public void requestBody(String url, final InstrumentResponseListener responseListener){

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    List<Instrument> instrumentList = new ArrayList<>();
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject instrumentJson = response.getJSONObject(i);
                            Instrument instrument = new Instrument();
                            instrument.setInstrumentId(instrumentJson.getInt("instrument_id")); // Use consistent naming
                            instrument.setInstrumentName(instrumentJson.getString("instrument_name"));
                            instrument.setInstrumentArtistCount(instrumentJson.getInt("instrument_artist_count"));
                            instrument.setInstrumentVideoCount(instrumentJson.getInt("instrument_video_count"));

                            instrumentList.add(instrument);
                        }
                        responseListener.onResponse(instrumentList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        responseListener.onError("Parsing error: " + e.getMessage());
                    }
                },
                error -> responseListener.onError("Network error: " + error.getMessage()));

        ApiClient.getInstance(context).addToRequestQueue(request);

    }

}
