package com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.api;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.MainActivity;
import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.R;
import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.mapper.WeatherImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ForeCastDataLoader {
    private static final String TAG = "ForeCastDataLoader";
    private static final String GET_FORECAST_URL = "https://api.weatherapi.com/v1/forecast.json";
    private final Context context;
    private final Spinner citiesSpinner;
    private final ImageView weatherIcon;
    private final OkHttpClient client = new OkHttpClient();

    public ForeCastDataLoader(Context context, Spinner citiesSpinner, ImageView weatherIcon) {
        this.context = context;
        this.citiesSpinner = citiesSpinner;
        this.weatherIcon = weatherIcon;
    }

    public void getWeatherForecast(WeatherCallback callback) {
        String selectedCity = citiesSpinner.getSelectedItem().toString();
        if (selectedCity == null || selectedCity.isEmpty()) {
            callback.onFailure(new Exception("City is null or empty"));
            return;
        }

        String apiKey = ApiKeyLoader.getApiKey(context);
        if (apiKey == null) {
            callback.onFailure(new Exception("API Key is null"));
            return;
        }

        String url = GET_FORECAST_URL + "?key=" + apiKey + "&q=" + selectedCity + "&days=7";

        Log.d(TAG, "Sending request to: " + url);

        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "Request failed", e);
                ((MainActivity) context).runOnUiThread(() -> callback.onFailure(e));
            }

            // In ForeCastDataLoader.java
            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String responseBody = response.body().string();
                    Log.d("ForeCastDataLoader", "Raw JSON response: " + responseBody);

                    // Assuming the responseBody is in JSON format, parse it to Map
                    Map<String, Object> weatherData = new Gson().fromJson(responseBody, new TypeToken<Map<String, Object>>(){}.getType());

                    // Notify success with the parsed data
                    if (callback != null) {
                        callback.onSuccess(weatherData);
                    }
                } catch (Exception e) {
                    Log.e("ForeCastDataLoader", "Failed to parse JSON", e);
                    if (callback != null) {
                        callback.onFailure(e);
                    }
                }
            }

        });
    }

    private Map<String, Object> parseJsonToMap(String json) throws JSONException {
        Map<String, Object> map = new HashMap<>();
        JSONObject jsonObject = new JSONObject(json);
        map = toMap(jsonObject);
        return map;
    }

    private Map<String, Object> toMap(JSONObject jsonObject) throws JSONException {
        Map<String, Object> map = new HashMap<>();
        JSONArray names = jsonObject.names();
        if (names != null) {
            for (int i = 0; i < names.length(); i++) {
                String key = names.getString(i);
                Object value = jsonObject.get(key);
                if (value instanceof JSONObject) {
                    value = toMap((JSONObject) value);
                } else if (value instanceof JSONArray) {
                    value = toList((JSONArray) value);
                }
                map.put(key, value);
            }
        }
        return map;
    }

    private Object toList(JSONArray jsonArray) throws JSONException {
        int length = jsonArray.length();
        Object[] list = new Object[length];
        for (int i = 0; i < length; i++) {
            Object value = jsonArray.get(i);
            if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            } else if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }
            list[i] = value;
        }
        return list;
    }

    public interface WeatherCallback {
        void onSuccess(Map<String, Object> weatherData);
        void onFailure(Exception e);
    }
}
