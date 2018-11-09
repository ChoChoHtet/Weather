package com.android.ict.weather.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ConditionList {
    @SerializedName("main")
    @Expose
    private CurrentLocation currentLocation;
    @SerializedName("weather")
    @Expose
    private List<Weather> weather = null;
    @SerializedName("wind")
    @Expose
    private Wind wind;
    @SerializedName("dt_txt")
    @Expose
    private String dateTime;


    public CurrentLocation getCurrentLocation(){
        return currentLocation;
    }

    public void setCurrentLocation(CurrentLocation currentLocation) {
        this.currentLocation= currentLocation;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }


    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
