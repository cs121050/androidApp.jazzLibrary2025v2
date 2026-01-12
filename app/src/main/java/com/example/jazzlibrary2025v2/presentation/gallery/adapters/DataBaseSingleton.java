package com.example.jazzlibrary2025v2.presentation.gallery.adapters;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class DataBaseSingleton {

    private static DataBaseSingleton instance;
    private RequestQueue requestQueue;
    private static Context ctx;

    private DataBaseSingleton(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized DataBaseSingleton getInstance(Context context){
        if(instance == null) {
            instance = new DataBaseSingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {getRequestQueue().add(req);    }
}
