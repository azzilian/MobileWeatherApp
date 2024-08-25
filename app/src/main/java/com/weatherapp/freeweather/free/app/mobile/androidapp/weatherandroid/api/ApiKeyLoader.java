package com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.api;

import android.content.Context;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApiKeyLoader {

    public static String getApiKey(Context context) {
        Properties properties = new Properties();
        try {
            InputStream inputStream = context.getAssets().open("weather_api_key.properties");
            properties.load(inputStream);
            return properties.getProperty("WEATHER_API_KEY");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
