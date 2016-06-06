package com.dahuo.learn.http;

import com.dahuo.learn.model.HeWeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {

    @GET("weather")
    Call<HeWeatherResponse> getWeather(@Query("city") String city, @Query("key") String key);

}
