package com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.api.CityDataloader;
import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.api.CountryDataLoader;
import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.api.WeatherDataLoader;
import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.model.CurrentWeather;

public class MainActivity extends AppCompatActivity {

    private Spinner countriesSpinner;
    private Spinner citiesSpinner;
    private CountryDataLoader countryDataLoader;
    private CityDataloader cityDataloader;
    private WeatherDataLoader weatherDataLoader;
    private TextView temperatureTextViewC;
    private TextView temperatureTextViewF;
    private TextView temperatureTextViewCondition;
    private ImageView weatherTodayIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countriesSpinner = findViewById(R.id.countries);
        citiesSpinner = findViewById(R.id.cities);
        temperatureTextViewC = findViewById(R.id.temperatureC);
        temperatureTextViewF = findViewById(R.id.temperatureF);
        temperatureTextViewCondition = findViewById(R.id.condition);
        weatherTodayIcon = findViewById(R.id.weather_today_img);

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

            }
        });

        weatherDataLoader = new WeatherDataLoader(this, citiesSpinner, weatherTodayIcon);
        citiesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                weatherDataLoader.getWeather(new WeatherDataLoader.WeatherCallback() {
                    @Override
                    public void onSuccess(CurrentWeather currentWeather) {
                        temperatureTextViewC.setText(currentWeather.getTempC() + " C");
                        temperatureTextViewF.setText(currentWeather.getTempC() + " F");
                        temperatureTextViewCondition.setText(currentWeather.getConditionText());
                    }

                    @Override
                    public void onFailure(Exception e) {
                        e.printStackTrace();
//                        temperatureTextView.setText("Error loading weather");
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
