package com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.domain;

import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.api.ForecastResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ForecastApiService {
    @GET("forecast.json")
    Call<ForecastResponse> getWeatherForecast(
            @Query("Key") String apiKey,
            @Query("q") String city,
            @Query("days") int days
    );
}
