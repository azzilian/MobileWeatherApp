package com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.model;

public class WeatherResponse {
    private double temp_c;
    private double temp_f;
    private String conditionText;
    private String conditionIcon;
    private int conditionCode;


    public double getTempC() {
        return temp_c;
    }

    public void setTempC(double temp_c) {
        this.temp_c = temp_c;
    }

    public double getTempF() {
        return temp_f;
    }

    public void setTempF(double temp_f) {
        this.temp_f = temp_f;
    }

    public String getConditionText() {
        return conditionText;
    }

    public void setConditionText(String conditionText) {
        this.conditionText = conditionText;
    }

    public String getConditionIcon() {
        return conditionIcon;
    }

    public void setConditionIcon(String conditionIcon) {
        this.conditionIcon = conditionIcon;
    }

    public int getConditionCode() {
        return conditionCode;
    }

    public void setConditionCode(int conditionCode) {
        this.conditionCode = conditionCode;
    }
}
