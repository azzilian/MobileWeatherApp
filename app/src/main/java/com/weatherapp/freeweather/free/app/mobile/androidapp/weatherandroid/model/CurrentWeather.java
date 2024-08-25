package com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.model;

public class CurrentWeather {
    private double tempC;
    private String conditionText;
    private String conditionIcon;

    public CurrentWeather(double tempC, String conditionText, String conditionIcon) {
        this.tempC = tempC;
        this.conditionText = conditionText;
        this.conditionIcon = conditionIcon;
    }

    public double getTempC() {
        return tempC;
    }

    public String getConditionText() {
        return conditionText;
    }

    public String getConditionIcon() {
        return conditionIcon;
    }
}
