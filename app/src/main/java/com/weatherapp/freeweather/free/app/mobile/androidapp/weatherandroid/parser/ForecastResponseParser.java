package com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.parser;

import com.google.gson.Gson;
import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.api.ForecastResponse;
import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.model.ForecastDay;
import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.model.ForecastWeather;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ForecastResponseParser {
    private final Gson gson = new Gson();

    public List<ForecastWeather> parseForecastWeather(String json) {
        ForecastResponse response = gson.fromJson(json, ForecastResponse.class);
        List<ForecastWeather> forecastWeatherList = new ArrayList<>();
        if (response != null && response.getForecast() != null &&
                response.getForecast().getForecastday() != null) {

            for (ForecastDay forecastDay : response.getForecast().getForecastday()) {
                ForecastDay.Day day = forecastDay.getDay();
                ForecastWeather forecastWeather = new ForecastWeather(
                        LocalDate.parse(forecastDay.getDate()),
                        day.getAvgtemp_c(),
                        day.getAvgtemp_f(),
                        day.getConditionText(),
                        day.getConditionIcon(),
                        day.getConditionCode()
                );
                forecastWeatherList.add(forecastWeather);
            }
        }
        return forecastWeatherList;
    }
}
