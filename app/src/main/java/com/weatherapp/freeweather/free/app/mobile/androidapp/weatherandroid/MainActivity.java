package com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.adapter.CustomAdapter;
import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.api.CityDataloader;
import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.api.CountryDataLoader;
import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.api.ForeCastDataLoader;
import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.api.WeatherDataLoader;
import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.model.CurrentWeather;
import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.model.ForecastWeather;

import java.time.LocalDate;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner countriesSpinner;
    private Spinner citiesSpinner;
    private CountryDataLoader countryDataLoader;
    private CityDataloader cityDataloader;
    private WeatherDataLoader weatherDataLoader;
    private ForeCastDataLoader foreCastDataLoader;
    private TextView temperatureTextViewC;
    private TextView temperatureTextViewF;
    private TextView temperatureTextViewCondition;
    private TextView temperatureForecastTextViewC;
    private TextView temperatureForecastTextViewF;
    private TextView temperatureForecastTextViewCondition;
    private ImageView weatherTodayIcon;
    private ImageView weatherForeCastIcon;
    private LocalDate date;
    private Button button;
    private ForecastWeather forecastWeather;

    private RecyclerView forecastRecyclerView;
    private CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        date = LocalDate.now();
        countriesSpinner = findViewById(R.id.countries);
        citiesSpinner = findViewById(R.id.cities);
        temperatureTextViewC = findViewById(R.id.temperatureC);
        temperatureTextViewF = findViewById(R.id.temperatureF);
        temperatureTextViewCondition = findViewById(R.id.condition);
        temperatureForecastTextViewC = findViewById(R.id.forecast_temperatureC);
        temperatureForecastTextViewF = findViewById(R.id.forecast_temperatureF);
        temperatureForecastTextViewCondition = findViewById(R.id.forecast_condition);

        weatherTodayIcon = findViewById(R.id.weather_today_img);
        weatherForeCastIcon = findViewById(R.id.weather_forecast_img);

        countryDataLoader = new CountryDataLoader(this, countriesSpinner);
        countryDataLoader.fetchCountries();

        cityDataloader = new CityDataloader(this, countriesSpinner, citiesSpinner);

        countriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                cityDataloader.fetchCities();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No action needed
            }
        });

        weatherDataLoader = new WeatherDataLoader(this, citiesSpinner, weatherTodayIcon);

        citiesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                weatherDataLoader.getWeather(new WeatherDataLoader.WeatherCallback() {
                    @Override
                    public void onSuccess(CurrentWeather currentWeather) {
                        temperatureTextViewC.setText("Temperature: " + currentWeather.getTempC() + "°C");
                        temperatureTextViewF.setText("Temperature: " + currentWeather.getTempF() + "°F");
                        temperatureTextViewCondition.setText("Condition: " + currentWeather.getConditionText());
                    }

                    @Override
                    public void onFailure(Exception e) {
                        e.printStackTrace();
                        temperatureTextViewC.setText("Error loading weather");
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No action needed
            }
        });

        button = findViewById(R.id.button);
        foreCastDataLoader = new ForeCastDataLoader(this, citiesSpinner, weatherForeCastIcon);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("WeatherApp", "Button clicked, sending API request...");

                if (forecastWeather != null) {
                    button.setText(forecastWeather.getConditionText());
                } else {
                    Log.e("WeatherApp", "Forecast weather is null.");
                }

                foreCastDataLoader.getWeatherForecast(new ForeCastDataLoader.WeatherCallback() {
                    @Override
                    public void onSuccess(List<ForecastWeather> forecastWeatherList) {
                        Log.d("WeatherApp", "API request successful. Weather data received.");

                        // Инициализация RecyclerView
                        forecastRecyclerView = findViewById(R.id.forecast_recycler_view);

                        // Установка горизонтального LayoutManager
                        forecastRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));

                        // Инициализация адаптера с полученными данными
                        customAdapter = new CustomAdapter(forecastWeatherList);

                        // Установка адаптера для RecyclerView
                        forecastRecyclerView.setAdapter(customAdapter);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.e("WeatherApp", "API request failed: " + e.getMessage());
                        Toast.makeText(MainActivity.this, "Failed to load forecast: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
