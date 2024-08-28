package com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.model;

public class ForecastDay {
    private String date;
    private Day day;

    public String getDate() {
        return date;
    }

    public Day getDay() {
        return day;
    }

    public static class Day {
        private double avgtemp_c;
        private double avgtemp_f;
        private String conditionText;
        private String conditionIcon;
        private int conditionCode;

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
    }
}
