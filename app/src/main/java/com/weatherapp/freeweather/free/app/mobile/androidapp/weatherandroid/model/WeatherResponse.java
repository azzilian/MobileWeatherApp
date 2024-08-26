package com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.model;

public class WeatherResponse {
    private Current current;

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public static class Current {
        private double temp_c;
        private double temp_f;
        private Condition condition;

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

        public Condition getCondition() {
            return condition;
        }

        public void setCondition(Condition condition) {
            this.condition = condition;
        }

        public static class Condition {
            private String text;
            private String icon;
            private int code;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public int getCode() {
                return code;
            }

            public void setCode(int code) {
                this.code = code;
            }
        }
    }
}
