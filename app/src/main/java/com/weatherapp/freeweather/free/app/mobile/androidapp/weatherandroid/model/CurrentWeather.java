package com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.model;

public class CurrentWeather {
    private double tempC;
    private double tempF;
    private String conditionText;
    private String conditionIcon;
    private int conditionCode;

    public CurrentWeather(double tempC, double tempF, String conditionText, String conditionIcon, int conditionCode) {
        this.tempC = tempC;
        this.tempF = tempF;
        this.conditionText = conditionText;
        this.conditionIcon = conditionIcon;
        this.conditionCode = conditionCode;
    }

    public double getTempC() {
        return tempC;
    }

    public double getTempF() {
        return tempF;
    }

    public String getConditionText() {
        return conditionText;
    }

    public String getConditionIcon() {
        return conditionIcon;
    }

    public int getConditionCode() {
        return conditionCode;
    }
}
