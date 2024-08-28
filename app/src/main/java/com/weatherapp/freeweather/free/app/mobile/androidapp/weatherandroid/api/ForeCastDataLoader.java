package com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.api;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Spinner;

import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.MainActivity;
import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.R;
import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.domain.ForecastApiService;
import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.mapper.WeatherImageLoader;
import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.model.ForecastWeather;
import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.model.ForecastDay;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForeCastDataLoader {
    private static final String BASE_URL = "https://api.weatherapi.com/v1/";
    private final Context context;
    private final Spinner citiesSpinner;
    private final ImageView weatherIcon;
    private final ForecastApiService apiService;

    public ForeCastDataLoader(Context context, Spinner citiesSpinner, ImageView weatherIcon) {
        this.context = context;
        this.citiesSpinner = citiesSpinner;
        this.weatherIcon = weatherIcon;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ForecastApiService.class);
    }

    public void getWeatherForecast(WeatherCallback callback) {
        String selectedCity = citiesSpinner.getSelectedItem().toString();
        if (selectedCity == null || selectedCity.isEmpty()) {
            callback.onFailure(new Exception("City is null or empty"));
            return;
        }

        String apiKey = ApiKeyLoader.getApiKey(context);
        if (apiKey == null) {
            callback.onFailure(new Exception("API Key is null"));
            return;
        }

        Call<ForecastResponse> call = apiService.getWeatherForecast(apiKey, selectedCity, 7);
        call.enqueue(new Callback<ForecastResponse>() {
            @Override
            public void onResponse(Call<ForecastResponse> call, Response<ForecastResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ForecastResponse forecastResponse = response.body();
                    List<ForecastWeather> forecastWeatherList = new ArrayList<>();

                    // Преобразуйте ForecastResponse в список ForecastWeather
                    for (ForecastDay forecastday : forecastResponse.getForecast().getForecastday()) {
                        ForecastWeather forecastWeather = new ForecastWeather(
                                LocalDate.parse(forecastday.getDate()),
                                forecastday.getDay().getAvgtemp_c(),
                                forecastday.getDay().getAvgtemp_f(),
                                forecastday.getDay().getConditionText(),
                                forecastday.getDay().getConditionIcon(),
                                forecastday.getDay().getConditionCode()
                        );
                        forecastWeatherList.add(forecastWeather);
                    }

                    if (!forecastWeatherList.isEmpty()) {
                        ((MainActivity) context).runOnUiThread(() -> {
                            // Используем первый элемент списка для отображения иконки
                            String iconUrl = "https:" + forecastWeatherList.get(0).getConditionIcon();
                            WeatherImageLoader.loadImage(context, weatherIcon, iconUrl, R.drawable.weather_default);
                            callback.onSuccess(forecastWeatherList);
                        });
                    } else {
                        ((MainActivity) context).runOnUiThread(() ->
                                callback.onFailure(new Exception("No forecast data available"))
                        );
                    }

                } else {
                    Log.e("ForeCastDataLoader", "Response not successful: " + response.code());
                    callback.onFailure(new Exception("Response not successful"));
                }
            }

            @Override
            public void onFailure(Call<ForecastResponse> call, Throwable t) {
                Log.e("ForeCastDataLoader", "Request failed", t);
                ((MainActivity) context).runOnUiThread(() -> callback.onFailure(new Exception("Request failed", t)));
            }
        });
    }

    public interface WeatherCallback {
        void onSuccess(List<ForecastWeather> forecastWeatherList);
        void onFailure(Exception e);
    }
}
