package com.dahuo.learn.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YanLu
 * @since 16/6/6
 */

public class HeWeatherResponse {
    @SerializedName("HeWeather data service 3.0") @Expose
    public List<HeWeatherInfo> weatherInfoList
            = new ArrayList<>();
}
