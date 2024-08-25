package com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.mapper;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class WeatherConditionsLoader {

    private final Context context;
    private static List<WeatherCondition> conditions;

    public WeatherConditionsLoader(Context context) {
        this.context = context;
        // Загрузка условий погоды из ресурса или другого источника
        conditions = loadConditions();
    }

    public List<WeatherCondition> loadConditions() {
        // Загрузка данных (пример) из ресурсов или API
        // Здесь нужно реализовать загрузку данных
        return new ArrayList<>();
    }

    public static String getDescriptionForCode(int code) {
        for (WeatherCondition condition : conditions) {
            if (condition.code == code) {
                return condition.description;
            }
        }
        return "Unknown weather condition";
    }

    public static class WeatherCondition {
        private int code;
        private String description;

        public WeatherCondition(int code, String description) {
            this.code = code;
            this.description = description;
        }

        // Getters and setters
    }
}
