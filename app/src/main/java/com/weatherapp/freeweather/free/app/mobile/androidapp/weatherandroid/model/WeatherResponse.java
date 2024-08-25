package com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.model;

public class WeatherResponse {
    private Location location;
    private Current current;

    // Getters and setters
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    // Nested class for Location
    public static class Location {
        private String name;
        private String region;
        private String country;
        private double lat;
        private double lon;
        private String tz_id;
        private long localtime_epoch;
        private String localtime;

        // Getters and setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }

        public String getTzId() {
            return tz_id;
        }

        public void setTzId(String tz_id) {
            this.tz_id = tz_id;
        }

        public long getLocaltimeEpoch() {
            return localtime_epoch;
        }

        public void setLocaltimeEpoch(long localtime_epoch) {
            this.localtime_epoch = localtime_epoch;
        }

        public String getLocaltime() {
            return localtime;
        }

        public void setLocaltime(String localtime) {
            this.localtime = localtime;
        }
    }

    // Nested class for Current
    public static class Current {
        private long last_updated_epoch;
        private String last_updated;
        private double temp_c;
        private double temp_f;
        private int is_day;
        private Condition condition;

        // Getters and setters
        public long getLastUpdatedEpoch() {
            return last_updated_epoch;
        }

        public void setLastUpdatedEpoch(long last_updated_epoch) {
            this.last_updated_epoch = last_updated_epoch;
        }

        public String getLastUpdated() {
            return last_updated;
        }

        public void setLastUpdated(String last_updated) {
            this.last_updated = last_updated;
        }

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

        public int getIsDay() {
            return is_day;
        }

        public void setIsDay(int is_day) {
            this.is_day = is_day;
        }

        public Condition getCondition() {
            return condition;
        }

        public void setCondition(Condition condition) {
            this.condition = condition;
        }

        // Nested class for Condition
        public static class Condition {
            private String text;
            private String icon;
            private int code;

            // Getters and setters
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
