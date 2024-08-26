package com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.api;

import android.content.Context;
import android.util.Log;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ImageView;

import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.MainActivity;
import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.R;
import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.mapper.WeatherImageLoader;
import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.model.CurrentWeather;
import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.parser.WeatherResponseParser;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class WeatherDataLoader {

    private static final String GET_WEATHER_URL = "https://api.weatherapi.com/v1/current.json";
    private final Context context;
    private final Spinner citiesSpinner;
    private final ImageView weatherIcon;
    private final OkHttpClient client = new OkHttpClient();
    private final WeatherResponseParser parser = new WeatherResponseParser();

    public WeatherDataLoader(Context context, Spinner citiesSpinner, ImageView weatherIcon) {
        this.context = context;
        this.citiesSpinner = citiesSpinner;
        this.weatherIcon = weatherIcon;
    }

    public void getWeather(WeatherCallback callback) {
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

        String url = GET_WEATHER_URL + "?Key=" + apiKey + "&q=" + selectedCity;

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("WeatherDataLoader", "Request failed", e);
                ((MainActivity) context).runOnUiThread(() -> callback.onFailure(e));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    Log.d("WeatherDataLoader", "Response: " + responseBody);

                    try {
                        CurrentWeather currentWeather = parser.parseCurrentWeather(responseBody);
                        ((MainActivity) context).runOnUiThread(() -> {
                                String iconUrl = "https:" + currentWeather.getConditionIcon();
                                WeatherImageLoader.loadImage(context, weatherIcon, iconUrl, R.drawable.weather_default);
                                callback.onSuccess(currentWeather);
                       });
                    } catch (Exception e) {
                        Log.e("WeatherDataLoader", "Failed to parse JSON", e);
                        callback.onFailure(new Exception("Failed to parse JSON"));
                    }
                } else {
                    Log.e("WeatherDataLoader", "Response not successful: " + response.code());
                    callback.onFailure(new Exception("Response not successful"));
                }
            }
        });
    }

    public interface WeatherCallback {
        void onSuccess(CurrentWeather currentWeather);
        void onFailure(Exception e);
    }
}
