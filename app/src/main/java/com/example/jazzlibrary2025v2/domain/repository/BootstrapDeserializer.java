package com.example.jazzlibrary2025v2.domain.repository;


import android.util.Log;

import com.example.jazzlibrary2025v2.domain.model.Artist;
import com.example.jazzlibrary2025v2.domain.model.BootStrap;
import com.example.jazzlibrary2025v2.domain.model.Duration;
import com.example.jazzlibrary2025v2.domain.model.Instrument;
import com.example.jazzlibrary2025v2.domain.model.Quote;
import com.example.jazzlibrary2025v2.domain.model.Video;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class BootstrapDeserializer implements JsonDeserializer<BootStrap> {

    @Override
    public BootStrap deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();
        BootStrap bootstrap = new BootStrap();




        // Parse simple lists
        // bootstrap.setQuoteList(parseList(context, jsonObject.getAsJsonArray("quoteList"), Quote.class));
        bootstrap.setArtistList(parseList(context, jsonObject.getAsJsonArray("artistList"), Artist.class));
        bootstrap.setTypeList(parseList(context, jsonObject.getAsJsonArray("typeList"), com.example.jazzlibrary2025v2.domain.model.Type.class));
        bootstrap.setDurationList(parseList(context, jsonObject.getAsJsonArray("durationList"), Duration.class));
        bootstrap.setInstrumentList(parseList(context, jsonObject.getAsJsonArray("instrumentList"), Instrument.class));
        bootstrap.setVideoList(parseList(context, jsonObject.getAsJsonArray("videoList"), Video.class));        //bootstrap.setInstrumentList(parseList(jsonObject, "instrumentList", context));


        // Parse complex maps
        bootstrap.setArtistFilters( parseNestedMap( context, getJsonObjectSafe(jsonObject, "artistFilters"), Artist.class));
        bootstrap.setVideoFilters(  parseNestedMap( context, getJsonObjectSafe(jsonObject, "videoFilters"), Video.class));




        // 1. Deserialize 'counts'
        if (jsonObject.has("counts")) {
            Map<String, Integer> counts = context.deserialize(
                    jsonObject.get("counts"),
                    new TypeToken<Map<String, Integer>>(){}.getType()
            );
            bootstrap.setCounts(counts);
        }


        return bootstrap;
    }

    private <T> List<T> parseList(JsonDeserializationContext context, JsonArray jsonArray, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        for (JsonElement element : jsonArray) {
            list.add(context.deserialize(element, clazz));
        }
        return list;
    }










    private <T> Map<String, Map<String, List<T>>> parseNestedMap(
            JsonDeserializationContext context,
            JsonObject filtersJson,
            Class<T> itemClass
    ) {
        Map<String, Map<String, List<T>>> resultMap = new HashMap<>();

        for (Map.Entry<String, JsonElement> filterTypeEntry : filtersJson.entrySet()) {
            String filterType = filterTypeEntry.getKey(); // e.g., "byArtist"
            JsonObject filterGroups = filterTypeEntry.getValue().getAsJsonObject();

            Map<String, List<T>> groupMap = new HashMap<>();

            for (Map.Entry<String, JsonElement> groupEntry : filterGroups.entrySet()) {
                String groupKey = groupEntry.getKey(); // e.g., "1" (artist ID)
                JsonArray itemsArray = groupEntry.getValue().getAsJsonArray();

                List<T> items = parseList(context, itemsArray, itemClass);
                groupMap.put(groupKey, items);
            }

            resultMap.put(filterType, groupMap);
        }

        return resultMap;
    }

    private JsonObject getJsonObjectSafe(JsonObject jsonObject, String key) {
        //IT WILL NOT CRASS THE PROGRAM if u dont call a key
        if (jsonObject.has(key)) {
            return jsonObject.getAsJsonObject(key);
        } else {
            return new JsonObject(); // Return empty JSON object if key doesn't exist
        }
    }

    private Class<?> getClassForField(String fieldName) {
        switch (fieldName) {
            case "quoteList": return Quote.class;
            case "artistList": return Artist.class;
            case "instrumentList": return Instrument.class;
            case "videoList": return Video.class;
            case "typeList": return Type.class;
            case "durationList": return Duration.class;
            default: throw new IllegalArgumentException("Unknown field: " + fieldName);
        }
    }
}