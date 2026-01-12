package com.example.jazzlibrary2025v2.domain.repository.secondaryRep;

import static com.example.jazzlibrary2025v2.utils.GlobalConfig.APIName;
import static com.example.jazzlibrary2025v2.utils.GlobalConfig.publicServersIP;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.jazzlibrary2025v2.data.remote.ApiClient;
import com.example.jazzlibrary2025v2.domain.model.Artist;
import com.example.jazzlibrary2025v2.domain.model.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VideoRepository {
    private static final String BASE_URI = "http://"+publicServersIP+":8080/"+ APIName ;
    private static final String VIDEO_SERVICE_URI = "videoService/";

    private static final String ALL_VIDEO_URI = "all";
    private static final String ALL_VIDEO_NAMES_URI = "allVideoNames";
    private static final String VIDEO_BY_ARTIST_ID_URI = "byArtistId/search";
    private static final String VIDEO_BY_ARTIST_ID_AND_DURATION_ID_URI = "byArtistIdAndDurationId/search";
    private static final String VIDEO_BY_ARTIST_ID_AND_TYPE_ID_URI = "byArtistIdAndTypeId/search";
    private static final String VIDEO_BY_ARTIST_ID_AND_TYPE_ID_AND_DURATION_ID_URI = "byArtistIdAndTypeIdAndDurationId/search";
    private static final String VIDEO_BY_ARTIST_NAME_AND_ARTIST_SURNAME_URI = "byArtistNameAndArtistSurname/search";
    private static final String VIDEO_BY_DURATION_ID_URI = "byDurationId/search";
    private static final String VIDEO_BY_VIDEO_ID_URI = "byId/search";
    private static final String VIDEO_BY_INSTRUMENT_ID_URI = "byInstrumentId/search";
    private static final String VIDEO_BY_INSTRUMENT_ID_AND_DURATION_ID_URI = "byInstrumentIdAndDurationId/search";
    private static final String VIDEO_BY_INSTRUMENT_ID_AND_TYPE_ID_URI = "byInstrumentIdAndTypeId/search";
    private static final String VIDEO_BY_INSTRUMENT_ID_AND_TYPE_ID_AND_DURATION_ID_URI = "byInstrumentIdAndTypeIdAndDurationId/search";
    private static final String VIDEO_BY_VIDEO_NAME_URI = "byName/search";
    private static final String VIDEO_BY_VIDEO_NAME_AND_ARTIST_ID_URI = "byNameAndArtistId/search";
    private static final String VIDEO_BY_VIDEO_NAME_AND_ARTIST_ID_AND_DURATION_ID_URI = "byNameAndArtistIdAndDurationId/search";
    private static final String VIDEO_BY_VIDEO_NAME_AND_DURATION_ID_URI = "byNameAndDurationId/search";
    private static final String VIDEO_BY_VIDEO_NAME_AND_INSTRUMENT_ID_URI = "byNameAndInstrumentId/search";
    private static final String VIDEO_BY_VIDEO_NAME_AND_INSTRUMENT_ID_AND_DURATION_ID_URI = "byNameAndInstrumentIdAndDurationId/search";
    private static final String VIDEO_BY_VIDEO_NAME_AND_TYPE_ID_URI = "byNameAndTypeId/search";
    private static final String VIDEO_BY_TYPE_ID_URI = "byTypeId/search";
    private static final String VIDEO_BY_TYPE_ID_AND_DURATION_ID_URI = "byTypeIdAndDurationId/search";
    private static final String VIDEO_BY_VIDEO_PATH_URI = "byVideoPath/search";
    private static final String VIDEO_BY_VIDEO_NAME_AND_INSTRUMENT_ID_AND_TYPE_ID_URI = "byNameAndInstrumentIdAndTypeId/search";
    private static final String VIDEO_BY_VIDEO_NAME_AND_INSTRUMENT_ID_AND_TYPE_ID_AND_DURATION_ID_URI = "byNameAndInstrumentIdAndTypeIdAndDurationId";
    private static final String VIDEO_BY_VIDEO_NAME_AND_ARTIST_ID_AND_TYPE_ID_URI = "byNameAndArtistIdAndTypeId/search";
    private static final String VIDEO_BY_VIDEO_NAME_AND_ARTIST_ID_AND_TYPE_ID_AND_DURATION_ID_URI = "byNameAndArtistIdAndTypeIdAndDurationId/search";

    private static final String RANDOM_N_VIDEOS_URI = "random5Video/search";

    private final Context context;

    public VideoRepository(Context context) {
        this.context = context;
    }

    public interface VideoResponseListener  {
        void onResponse(List<Video> listOfVideo);

        void onError(String message);
    }

    public void retrieveAllVideos(final VideoResponseListener responseListener, int typeOfResponceFlag) {
        String url = BASE_URI + VIDEO_SERVICE_URI + ALL_VIDEO_URI;

        requestBodySelection(typeOfResponceFlag, url, responseListener);

    }

    public void retrieveVideoNames(final VideoResponseListener responseListener, int typeOfResponceFlag) {
        String url = BASE_URI + VIDEO_SERVICE_URI + ALL_VIDEO_NAMES_URI;

        requestBodySelection(typeOfResponceFlag, url, responseListener);
    }

    public void retrieveVideoByArtistId(int artistId, final VideoResponseListener responseListener, int typeOfResponceFlag) {
        String url = BASE_URI + VIDEO_SERVICE_URI + VIDEO_BY_ARTIST_ID_URI+ "?artist_id  =" + artistId;

        requestBodySelection(typeOfResponceFlag, url, responseListener);
    }

    public void retrieveVideoByArtistIdAndDurationId(int artistId, int durationId,final VideoResponseListener responseListener, int typeOfResponceFlag) {
        String url = BASE_URI + VIDEO_SERVICE_URI + VIDEO_BY_ARTIST_ID_AND_DURATION_ID_URI+ "?artist_id  =" + artistId
                                                                                          + "&duration_id  =" + durationId ;

        requestBodySelection(typeOfResponceFlag, url, responseListener);
    }

    public void retrieveVideoByArtistIdAndTypeId(int artistId, int typeId, final VideoResponseListener responseListener, int typeOfResponceFlag) {
        String url = BASE_URI + VIDEO_SERVICE_URI + VIDEO_BY_ARTIST_ID_AND_TYPE_ID_URI+ "?artist_id  =" + artistId
                                                                                      + "&type_id =" + typeId;

        requestBodySelection(typeOfResponceFlag, url, responseListener);
    }

    public void retrieveVideoByArtistIdAndTypeIdAndDurationId(int artistId, int typeId, int durationId, final VideoResponseListener responseListener, int typeOfResponceFlag) {
        String url = BASE_URI + VIDEO_SERVICE_URI + VIDEO_BY_ARTIST_ID_AND_TYPE_ID_AND_DURATION_ID_URI+ "?artist_id  =" + artistId
                                                                                                      + "&type_id =" + typeId
                                                                                                      + "&duration_id =" + durationId;

        requestBodySelection(typeOfResponceFlag, url, responseListener);
    }

    public void retrieveVideoByArtistNameAndArtistSurname(String artistName, String artistSurname, final VideoResponseListener responseListener, int typeOfResponceFlag) {
        String url = BASE_URI + VIDEO_SERVICE_URI + VIDEO_BY_ARTIST_NAME_AND_ARTIST_SURNAME_URI+ "?artist_name  =" + artistName
                                                                                               + "&artist_surname  =" + artistSurname;

        requestBodySelection(typeOfResponceFlag, url, responseListener);
    }

    public void retrieveVideoByDurationId(int durationId, final VideoResponseListener responseListener, int typeOfResponceFlag) {
        String url = BASE_URI + VIDEO_SERVICE_URI + VIDEO_BY_DURATION_ID_URI+ "?duration_id   =" + durationId;

        requestBodySelection(typeOfResponceFlag, url, responseListener);
    }

    public void retrieveVideoByVideoId(int videoId, final VideoResponseListener responseListener, int typeOfResponceFlag) {
        String url = BASE_URI + VIDEO_SERVICE_URI + VIDEO_BY_VIDEO_ID_URI+ "?video_id  =" + videoId;

        requestBodySelection(typeOfResponceFlag, url, responseListener);
    }

    public void retrieveVideoByInstrumentId(int instrumentId, final VideoResponseListener responseListener, int typeOfResponceFlag) {
        String url = BASE_URI + VIDEO_SERVICE_URI + VIDEO_BY_INSTRUMENT_ID_URI+ "?instrument_id =" + instrumentId;

        requestBodySelection(typeOfResponceFlag, url, responseListener);
    }

    public void retrieveVideoByInstrumentIdAndDurationId(int instrumentId,int durationId, final VideoResponseListener responseListener, int typeOfResponceFlag) {
        String url = BASE_URI + VIDEO_SERVICE_URI + VIDEO_BY_INSTRUMENT_ID_AND_DURATION_ID_URI+ "?instrument_id =" + instrumentId
                                                                                              + "&duration_id =" + durationId;

        requestBodySelection(typeOfResponceFlag, url, responseListener);
    }

    public void retrieveVideoByInstrumentIdAndTypeId(int instrumentId,int typeId, final VideoResponseListener responseListener, int typeOfResponceFlag) {
        String url = BASE_URI + VIDEO_SERVICE_URI + VIDEO_BY_INSTRUMENT_ID_AND_TYPE_ID_URI+ "?instrument_id =" + instrumentId
                                                                                          + "&type_id =" + typeId;

        requestBodySelection(typeOfResponceFlag, url, responseListener);
    }

    public void retrieveVideoByInstrumentIdAndTypeIdAndDurationId(int instrumentId,int typeId, int durationId, final VideoResponseListener responseListener, int typeOfResponceFlag) {
        String url = BASE_URI + VIDEO_SERVICE_URI + VIDEO_BY_INSTRUMENT_ID_AND_TYPE_ID_AND_DURATION_ID_URI+ "?instrument_id =" + instrumentId
                                                                                                          + "&type_id =" + typeId
                                                                                                          + "&duration_id =" + durationId;
        requestBodySelection(typeOfResponceFlag, url, responseListener);
    }

    public void retrieveVideoByVideoName(String videoName, final VideoResponseListener responseListener, int typeOfResponceFlag) {
        String url = BASE_URI + VIDEO_SERVICE_URI + VIDEO_BY_VIDEO_NAME_URI + "?video_name   =" + videoName;

        requestBodySelection(typeOfResponceFlag, url, responseListener);
    }

    public void retrieveVideoByVideoNameAndArtistId(String videoName, int artistId, final VideoResponseListener responseListener, int typeOfResponceFlag) {
        String url = BASE_URI + VIDEO_SERVICE_URI + VIDEO_BY_VIDEO_NAME_AND_ARTIST_ID_URI + "?video_name   =" + videoName
                                                                                          + "&artist_id  =" + artistId;

        requestBodySelection(typeOfResponceFlag, url, responseListener);
    }

    public void retrieveVideoByVideoNameAndArtistIdAndDurationId(String videoName, int artistId, int durationId, final VideoResponseListener responseListener, int typeOfResponceFlag) {
        String url = BASE_URI + VIDEO_SERVICE_URI + VIDEO_BY_VIDEO_NAME_AND_ARTIST_ID_AND_DURATION_ID_URI+ "?video_name=" + videoName
                                                                                                         + "&artist_id=" + artistId
                                                                                                         + "&duration_id=" + durationId;

        requestBodySelection(typeOfResponceFlag, url, responseListener);
    }

    public void retrieveVideoByVideoNameAndDurationId(String videoName, int durationId, final VideoResponseListener responseListener, int typeOfResponceFlag) {
        String url = BASE_URI + VIDEO_SERVICE_URI + VIDEO_BY_VIDEO_NAME_AND_DURATION_ID_URI + "?video_name   =" + videoName
                                                                                            + "&duration_id =" + durationId;

        requestBodySelection(typeOfResponceFlag, url, responseListener);
    }

    public void retrieveVideoByVideoNameAndInstrumentId(String videoName, int instrumentId, final VideoResponseListener responseListener, int typeOfResponceFlag) {
        String url = BASE_URI + VIDEO_SERVICE_URI + VIDEO_BY_VIDEO_NAME_AND_INSTRUMENT_ID_URI+ "?video_name   =" + videoName
                                                                                             + "&instrument_id=" + instrumentId;

        requestBodySelection(typeOfResponceFlag, url, responseListener);
    }

    public void retrieveVideoByVideoNameAndInstrumentIdAndDurationId(String videoName, int instrumentId, int durationId, final VideoResponseListener responseListener, int typeOfResponceFlag) {
        String url = BASE_URI + VIDEO_SERVICE_URI + VIDEO_BY_VIDEO_NAME_AND_INSTRUMENT_ID_AND_DURATION_ID_URI+ "?video_name   =" + videoName
                                                                                                             + "&instrument_id=" + instrumentId
                                                                                                             + "&duration_id      =" + durationId;

        requestBodySelection(typeOfResponceFlag, url, responseListener);
    }

    public void retrieveVideoByVideoNameAndTypeId(String videoName, int typeId, final VideoResponseListener responseListener, int typeOfResponceFlag) {
        String url = BASE_URI + VIDEO_SERVICE_URI + VIDEO_BY_VIDEO_NAME_AND_TYPE_ID_URI + "?video_name     =" + videoName
                                                                                        + "&type_id  =" + typeId;

        requestBodySelection(typeOfResponceFlag, url, responseListener);
    }

    public void retrieveVideoByTypeId(int typeId, final VideoResponseListener responseListener, int typeOfResponceFlag) {
        String url = BASE_URI + VIDEO_SERVICE_URI + VIDEO_BY_TYPE_ID_URI+ "?type_id    =" + typeId;

        requestBodySelection(typeOfResponceFlag, url, responseListener);
    }

    public void retrieveVideoByTypeIdAndDurationId(int typeId, int durationId, final VideoResponseListener responseListener, int typeOfResponceFlag) {
        String url = BASE_URI + VIDEO_SERVICE_URI + VIDEO_BY_TYPE_ID_AND_DURATION_ID_URI+ "?type_id    =" + typeId
                                                                                        + "&duration_id =" + durationId;

        requestBodySelection(typeOfResponceFlag, url, responseListener);
    }


    public void retrieveVideoByVideoPath(String videoPath, final VideoResponseListener responseListener, int typeOfResponceFlag) {
        String url = BASE_URI + VIDEO_SERVICE_URI + VIDEO_BY_VIDEO_PATH_URI+ "?video_path   =" + videoPath;

        requestBodySelection(typeOfResponceFlag, url, responseListener);
    }

    public void retrieveVideoByVideoNameAndInstrumentIdAndTypeId(String videoName, int instrumentId, int typeId, final VideoResponseListener responseListener, int typeOfResponceFlag) {
        String url = BASE_URI + VIDEO_SERVICE_URI + VIDEO_BY_VIDEO_NAME_AND_INSTRUMENT_ID_AND_TYPE_ID_URI+ "?video_name   =" + videoName
                                                                                                         + "&instrument_id=" + instrumentId
                                                                                                         + "&type_id     =" + typeId;

        requestBodySelection(typeOfResponceFlag, url, responseListener);
    }

    public void retrieveVideoByVideoNameAndInstrumentIdAndTypeIdAndDurationId(String videoName, int instrumentId, int typeId, int durationId, final VideoResponseListener responseListener, int typeOfResponceFlag) {
        String url = BASE_URI + VIDEO_SERVICE_URI + VIDEO_BY_VIDEO_NAME_AND_INSTRUMENT_ID_AND_TYPE_ID_AND_DURATION_ID_URI+ "?video_name   =" + videoName
                                                                                                                         + "&instrument_id      =" + instrumentId
                                                                                                                         + "&type_id     =" + typeId
                                                                                                                         + "&duration_id     =" + durationId;
        requestBodySelection(typeOfResponceFlag, url, responseListener);
    }

    public void retrieveVideoByVideoNameAndArtistIdAndTypeId(String videoName, int artistId, int typeId, final VideoResponseListener responseListener, int typeOfResponceFlag) {
        String url = BASE_URI + VIDEO_SERVICE_URI + VIDEO_BY_VIDEO_NAME_AND_ARTIST_ID_AND_TYPE_ID_URI+ "?video_name   =" + videoName
                                                                                                     + "&artist_id     =" + artistId
                                                                                                     + "&type_id     =" + typeId;

        requestBodySelection(typeOfResponceFlag, url, responseListener);
    }

    public void retrieveVideoByVideoNameAndArtistIdAndTypeIdAndDurationId(String videoName, int artistId, int typeId, int durationId, final VideoResponseListener responseListener, int typeOfResponceFlag) {
        String url = BASE_URI + VIDEO_SERVICE_URI + VIDEO_BY_VIDEO_NAME_AND_ARTIST_ID_AND_TYPE_ID_AND_DURATION_ID_URI+ "?video_name   =" + videoName
                                                                                                                     + "&artist_id     =" + artistId
                                                                                                                     + "&type_id     =" + typeId
                                                                                                                     + "&duration_id     =" + durationId;

        requestBodySelection(typeOfResponceFlag, url, responseListener);
    }

    public void retrieveRandomVideos(int count, final VideoResponseListener responseListener, int typeOfResponceFlag) {
        String url = BASE_URI + VIDEO_SERVICE_URI + RANDOM_N_VIDEOS_URI + "?count=" + count ;

        requestBodySelection(typeOfResponceFlag, url, responseListener);
    }

    public void requestBody(String url, final VideoResponseListener responseListener) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    List<Video> videoList = new ArrayList<>();
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject videoJson = response.getJSONObject(i);
                            Video video = new Video();
                            video.setVideoId(videoJson.getInt("video_id"));
                            video.setDurationId(videoJson.getInt("duration_id"));
                            video.setVideoName(videoJson.getString("video_name"));
                            video.setVideoDuration(videoJson.getString("video_duration"));
                            video.setVideoPath(videoJson.getString("video_path"));
                            video.setTypeId(videoJson.getInt("type_id"));
                            video.setLocationId(videoJson.getString("location_id"));
                            video.setVideoAvailability(videoJson.getString("video_availability"));

                            videoList.add(video);
                        }
                        responseListener.onResponse(videoList);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        responseListener.onError("Parsing error: " + e.getMessage());
                    }
                },
                error -> responseListener.onError("Network error: " + error.getMessage()));

        ApiClient.getInstance(context).addToRequestQueue(request);
    }

    public void hibernateRequestBody(String url, final VideoResponseListener responseListener) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    List<Video> videoList = new ArrayList<>();
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject videoJson = response.getJSONObject(i);
                            Video video = new Video();
                            video.setVideoId(videoJson.getInt("video_id"));
                            video.setDurationId(videoJson.getInt("duration_id"));
                            video.setVideoName(videoJson.getString("video_name"));
                            video.setVideoDuration(videoJson.getString("video_duration"));
                            video.setVideoPath(videoJson.getString("video_path"));
                            video.setTypeId(videoJson.getInt("type_id"));
                            video.setLocationId(videoJson.getString("location_id"));
                            video.setVideoAvailability(videoJson.getString("video_availability"));

                            // Parse artist list inside video
                            JSONArray artistArray = videoJson.getJSONArray("artistList");
                            List<Artist> artistList = new ArrayList<>();
                            for (int j = 0; j < artistArray.length(); j++) {
                                JSONObject artistJson = artistArray.getJSONObject(j);
                                Artist artist = new Artist();
                                artist.setArtistId(artistJson.getInt("artist_id"));
                                artist.setArtistName(artistJson.getString("artist_name"));
                                artist.setArtistSurname(artistJson.getString("artist_surname"));
                                artist.setInstrumentId(artistJson.getInt("instrument_id"));
                                artist.setArtistVideoCount(artistJson.getInt("artist_video_count"));
                                artist.setArtistRank(artistJson.getInt("artist_rank"));

                                artistList.add(artist);
                            }
                            //video.setArtistList(artistList);

                            videoList.add(video);
                        }
                        responseListener.onResponse(videoList);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        responseListener.onError("Parsing error: " + e.getMessage());
                    }
                },
                error -> responseListener.onError("Network error: " + error.getMessage()));

        ApiClient.getInstance(context).addToRequestQueue(request);
    }


    public void requestBodySelection(int typeOfResponceFlag, String url, final VideoResponseListener responseListener){
        if(typeOfResponceFlag==0) {
            requestBody(url, responseListener);
        } else if(typeOfResponceFlag==1){
            hibernateRequestBody(url, responseListener);
        }
    }



}
