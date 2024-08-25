package com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.api;

import android.content.Context;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.MainActivity;
import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.R;
import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.parser.WeatherParser;
import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.model.WeatherResponse;

import java.io.IOException;
import java.util.List;
import android.util.Log;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherDataLoader {

    private static final String GET_WEATHER_URL = "https://api.weatherapi.com/v1/current.json";
    private final Context context;
    private final Spinner citiesSpinner;
    private final TextView temperatureTextView;
    private ImageView weatherIcon;
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();

    public WeatherDataLoader(Context context, Spinner citiesSpinner, TextView temperatureTextView, ImageView weatherIcon) {
        this.context = context;
        this.citiesSpinner = citiesSpinner;
        this.temperatureTextView = temperatureTextView;
        this.weatherIcon = weatherIcon;
    }

    public void getWeather() {
        String selectedCity = citiesSpinner.getSelectedItem().toString();
        Log.d("WeatherDataLoader", "Selected city: " + selectedCity);

        if (selectedCity == null || selectedCity.isEmpty()) {
            Log.e("WeatherDataLoader", "Selected city is null or empty");
            return;
        }

        String apiKey = ApiKeyLoader.getApiKey(context);
        if (apiKey == null) {
            // Handle missing API key
            Log.e("WeatherDataLoader", "API key is missing");
            return;
        }

        // Include API key in URL as a parameter
        String url = GET_WEATHER_URL + "?q=" + selectedCity + "&key=" + apiKey;
        Log.d("WeatherDataLoader", "Request URL: " + url);

        Request request = new Request.Builder()
                .url(url)
                .build();

        Log.d("WeatherDataLoader", "Fetching weather data");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("WeatherDataLoader", "Request failed", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    Log.d("WeatherDataLoader", "Weather data received: " + responseBody);
                    WeatherResponse weatherResponse = gson.fromJson(responseBody, WeatherResponse.class);

                    ((MainActivity) context).runOnUiThread(() -> {
                        WeatherParser parser = new WeatherParser();
                        String temperature = parser.parseTemperature(weatherResponse);
                        temperatureTextView.setText(temperature);
                        Log.d("WeatherDataLoader", "Temperature text set: " + temperature);
                    });
                } else {
                    Log.e("WeatherDataLoader", "Failed to fetch weather data");
                }
            }
        });
    }
}
