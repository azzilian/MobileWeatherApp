package com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.api;

import android.content.Context;
import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.R;
import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

public class ApiKeyLoader {

    public static String getApiKey(Context context) {
        Properties properties = new Properties();
        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.weather_api_key);
            properties.load(inputStream);
            return properties.getProperty("WEATHER_API_KEY");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
