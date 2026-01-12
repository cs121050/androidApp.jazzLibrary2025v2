package com.example.jazzlibrary2025v2.domain.repository.secondaryRep;

import static com.example.jazzlibrary2025v2.utils.GlobalConfig.APIName;
import static com.example.jazzlibrary2025v2.utils.GlobalConfig.publicServersIP;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.jazzlibrary2025v2.data.remote.ApiClient;
import com.example.jazzlibrary2025v2.domain.model.TableRowCount;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TableRowCountRepository {
    private static final String BASE_URI = "http://"+publicServersIP+":8080/"+ APIName ;
    private static final String TABLE_ROW_COUNT_SERVICE_URI = "tableRowCountService/";

    private static final String TABLE_ROW_COUNT_SERVICE_ALL_URI = "all";
    private static final String TABLE_ROW_COUNT_SERVICE_BY_TABLE_ID_URI = "byTableId/search";

    private final Context context;

    public TableRowCountRepository(Context context) {
        this.context = context;
    }

    public interface TableRowCountResponseListener  {
        void onResponse(List<TableRowCount> listOfTableRowCount);

        void onError(String message);
    }

    public void retrieveTableRowCountByTableId(int tableId, final TableRowCountResponseListener responseListener) {
        String url = BASE_URI + TABLE_ROW_COUNT_SERVICE_URI + TABLE_ROW_COUNT_SERVICE_BY_TABLE_ID_URI + "?table_id =" + tableId;

        requestBody(url,responseListener);

    }

    public void retrieveAllTableRowCounts(final TableRowCountResponseListener responseListener) {
        String url = BASE_URI + TABLE_ROW_COUNT_SERVICE_URI + TABLE_ROW_COUNT_SERVICE_ALL_URI;

        requestBody(url,responseListener);

    }


    public void requestBody(String url, final TableRowCountResponseListener responseListener){

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    List<TableRowCount> tableRowCountList = new ArrayList<>();
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject tableRowCountJson = response.getJSONObject(i);
                            TableRowCount tableRowCount = new TableRowCount();
                            tableRowCount.setTableRowCountId(tableRowCountJson.getInt("table_row_count_id")); // Use consistent naming
                            tableRowCount.setTableId(tableRowCountJson.getInt("table_id"));
                            tableRowCount.setTableName(tableRowCountJson.getInt("table_name"));
                            tableRowCount.setCount(tableRowCountJson.getInt("count"));

                            tableRowCountList.add(tableRowCount);
                        }
                        responseListener.onResponse(tableRowCountList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        responseListener.onError("Parsing error: " + e.getMessage());
                    }
                },
                error -> responseListener.onError("Network error: " + error.getMessage()));

        ApiClient.getInstance(context).addToRequestQueue(request);
    }




}
