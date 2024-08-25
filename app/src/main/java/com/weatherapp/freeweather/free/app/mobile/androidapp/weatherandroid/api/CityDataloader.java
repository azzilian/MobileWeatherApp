package com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.api;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.MainActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CityDataloader {
    private static String GET_CITY_URL = "https://countriesnow.space/api/v0.1/countries/cities";
    private Context context;
    private Spinner countriesSpinner;
    private Spinner citiesSpinner;
    private OkHttpClient client = new OkHttpClient();
    private Gson gson = new Gson();

    public CityDataloader(Context context, Spinner countriesSpinner, Spinner citiesSpinner) {
        this.context = context;
        this.countriesSpinner = countriesSpinner;
        this.citiesSpinner = citiesSpinner;
    }

    public void fetchCities() {
        String selectedCountry = countriesSpinner.getSelectedItem().toString();

        if (selectedCountry.isEmpty()) {
            return;
        }

        String jsonBody = "{\"country\": \"" + selectedCountry + "\"}";
        RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(GET_CITY_URL)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace(); // Handle the error
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    List<String> cityNames = parseCitiesFromResponse(responseBody);

                    ((MainActivity) context).runOnUiThread(() -> {
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                                android.R.layout.simple_spinner_item, cityNames);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        citiesSpinner.setAdapter(adapter);
                    });
                }
            }
        });
    }

    private List<String> parseCitiesFromResponse(String responseBody) {
        List<String> cityNames = new ArrayList<>();
        try {
            CityResponse cityResponse = gson.fromJson(responseBody, CityResponse.class);
            if (cityResponse != null && !cityResponse.data.isEmpty()) {
                cityNames = cityResponse.data;
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle parsing error
        }
        return cityNames;
    }

    private class CityResponse {
        boolean error;
        String msg;
        List<String> data;
    }
}
