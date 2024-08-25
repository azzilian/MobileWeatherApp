package com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.model;

import java.util.List;

public class CountryResponse {
    private boolean error;
    private String msg;
    private List<Country> data;

    public boolean isError() { return error; }
    public String getMsg() { return msg; }
    public List<Country> getData() { return data; }
}
