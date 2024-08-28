package com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.domain;
import android.os.AsyncTask;
import android.widget.Toast;

import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.MainActivity;
import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.api.ForeCastDataLoader;
import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.parser.ForecastResponseParser;

import java.util.List;
import java.util.Map;

public class FetchForecastTask extends AsyncTask<Void, Void, Map<String, Object>> {

    private ForeCastDataLoader foreCastDataLoader;
    private MainActivity activity;
    private Exception exception;

    public FetchForecastTask(ForeCastDataLoader foreCastDataLoader, MainActivity activity) {
        this.foreCastDataLoader = foreCastDataLoader;
        this.activity = activity;
    }

    @Override
    protected Map<String, Object> doInBackground(Void... voids) {
        final Map<String, Object>[] result = new Map[]{null};

        final Object[] lock = new Object[0];
        final boolean[] done = {false};

        // Выполняем запрос на получение прогноза
        foreCastDataLoader.getWeatherForecast(new ForeCastDataLoader.WeatherCallback() {
            @Override
            public void onSuccess(Map<String, Object> weatherData) {
                result[0] = weatherData;
                synchronized (lock) {
                    done[0] = true;
                    lock.notify();
                }
            }

            @Override
            public void onFailure(Exception e) {
                exception = e;
                synchronized (lock) {
                    done[0] = true;
                    lock.notify();
                }
            }
        });

        // Ожидание завершения коллбэка
        synchronized (lock) {
            while (!done[0]) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        return result[0];
    }

    @Override
    protected void onPostExecute(Map<String, Object> weatherData) {
        if (exception != null) {
            Toast.makeText(activity, "Failed to load forecast: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
        } else if (weatherData != null) {
            ForecastResponseParser parser = new ForecastResponseParser();
            Map<Integer, List<String>> forecastMap = parser.parseForecastWeather(weatherData);
            if (forecastMap.containsKey(1)) {
                List<String> day1Data = forecastMap.get(1);
                String text = day1Data.get(2);
                activity.getButton().setText(String.format(text));
            } else {
                Toast.makeText(activity, "No data found for day 1.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(activity, "Failed to load forecast.", Toast.LENGTH_SHORT).show();
        }
    }
}
