package com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.parser;

import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.model.WeatherResponse;

public class WeatherParser {

    public String parseTemperature(WeatherResponse weatherResponse) {
        if (weatherResponse != null && weatherResponse.getCurrent() != null) {
            double tempC = weatherResponse.getCurrent().getTempC();
            return tempC + " °C";
        }
        return "N/A";
    }
}
