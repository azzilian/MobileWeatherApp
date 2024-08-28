package com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.parser;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForecastResponseParser {

    public Map<Integer, List<String>> parseForecastWeather(Map<String, Object> weatherData) {
        Map<Integer, List<String>> forecastMap = new HashMap<>();

        // Логируем полученные данные
        Log.d("WeatherApp", "Parsing weather data: " + weatherData.toString());

        // Extract forecast data
        Map<String, Object> forecast = (Map<String, Object>) weatherData.get("forecast");
        if (forecast != null) {
            List<Map<String, Object>> forecastDayList = (List<Map<String, Object>>) forecast.get("forecastday");
            if (forecastDayList != null) {
                for (int i = 0; i < forecastDayList.size(); i++) {
                    // Use index i + 1 to start from 1
                    Map<String, Object> forecastDay = forecastDayList.get(i);
                    Log.d("WeatherApp", "Processing day " + (i + 1) + ": " + forecastDay.toString());

                    Map<String, Object> day = (Map<String, Object>) forecastDay.get("day");
                    Map<String, Object> condition = (Map<String, Object>) day.get("condition");

                    if (day != null && condition != null) {
                        double avgTempC = ((Number) day.get("avgtemp_c")).doubleValue();
                        double avgTempF = ((Number) day.get("avgtemp_f")).doubleValue();
                        String conditionText = (String) condition.get("text");
                        String conditionIcon = (String) condition.get("icon");
                        int conditionCode = ((Number) condition.get("code")).intValue();

                        // Create a list of temperature values
                        List<String> values = new ArrayList<>();
                        values.add(String.valueOf(avgTempC));
                        values.add(String.valueOf(avgTempF));
                        values.add(conditionText);
                        values.add(conditionIcon);
                        values.add(String.valueOf(conditionCode));

                        // Put the values in the map with the index as the key
                        forecastMap.put(i + 1, values); // i + 1 to start index from 1
                    }
                }
            }
        }

        Log.d("WeatherApp", "Forecast map after parsing: " + forecastMap.toString());

        return forecastMap;
    }


}
