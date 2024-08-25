package com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.parser;

import com.google.gson.Gson;
import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.model.CurrentWeather;
import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.model.WeatherResponse;

public class WeatherResponseParser {

    private final Gson gson = new Gson();

    public CurrentWeather parseCurrentWeather(String json) {
        WeatherResponse response = gson.fromJson(json, WeatherResponse.class);
        
        if (response != null && response.getCurrent() != null) {
            WeatherResponse.Current current = response.getCurrent();
            return new CurrentWeather(
                    current.getTempC(),
                    current.getTempF(),
                    current.getCondition().getText(),
                    current.getCondition().getIcon(),
                    current.getCondition().getCode()
            );
        }
        return null;
    }
}
