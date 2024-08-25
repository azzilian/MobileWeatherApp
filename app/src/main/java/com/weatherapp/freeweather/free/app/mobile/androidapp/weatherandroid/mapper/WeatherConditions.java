package com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.mapper;

import android.content.Context;

import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.R;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class WeatherConditions {
    private static Map<Integer, Integer> iconMap = new HashMap<>();

    public static void loadConditions(Context context) {
        try {
            InputStream is = context.getAssets().open("weather_conditions.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");

            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                int code = obj.getInt("code");
                int icon = obj.getInt("icon");
                int iconResId = context.getResources().getIdentifier("weather_" + icon, "drawable", context.getPackageName());

                if (iconResId == 0) {
                    System.out.println("Resource not found for icon code: " + icon);
                } else {
                    iconMap.put(code, iconResId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getIconForCode(int code) {
        return iconMap.getOrDefault(code, R.drawable.weather_default);
    }
}
