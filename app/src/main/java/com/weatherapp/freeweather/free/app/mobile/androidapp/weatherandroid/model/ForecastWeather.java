package com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.model;

import java.time.LocalDate;

public class ForecastWeather {
    private LocalDate date;
    private double avgtemp_c;
    private double avgtemp_f;
    private String conditionText;
    private String conditionIcon;
    private int conditionCode;

    public LocalDate getDate() {
        return date;
    }

    public double getAvgtemp_c() {
        return avgtemp_c;
    }

    public double getAvgtemp_f() {
        return avgtemp_f;
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

    public ForecastWeather(LocalDate date, double avgtemp_c, double avgtemp_f, String conditionText, String conditionIcon, int conditionCode) {
        this.date = date;
        this.avgtemp_c = avgtemp_c;
        this.avgtemp_f = avgtemp_f;
        this.conditionText = conditionText;
        this.conditionIcon = conditionIcon;
        this.conditionCode = conditionCode;
    }
}
