package com.dahuo.learn.supplier;

import android.support.annotation.NonNull;

import com.dahuo.learn.constant.AppConstants;
import com.dahuo.learn.http.RetrofitServiceFactory;
import com.dahuo.learn.http.WeatherApiService;
import com.dahuo.learn.model.HeWeatherInfo;
import com.dahuo.learn.model.HeWeatherResponse;
import com.google.android.agera.Result;
import com.google.android.agera.Supplier;

import java.io.IOException;
import java.util.List;

/**
 * @author YanLu
 * @since 16/5/16
 */
public class WeatherSupplier implements Supplier<Result<List<HeWeatherInfo>>> {
    private String baseUrl = "https://api.heweather.com/x3/";
    private Supplier<String> mSupplierPagination;

    public WeatherSupplier(@NonNull Supplier<String> supplier ) {
        mSupplierPagination = supplier;
    }

    @NonNull
    @Override
    public Result<List<HeWeatherInfo>> get() {
        return fetchWeather();
    }

    private Result<List<HeWeatherInfo>> fetchWeather() {
        WeatherApiService apiService = RetrofitServiceFactory.createService(WeatherApiService.class, baseUrl);
        HeWeatherResponse weatherResponse = null;
        try {
            weatherResponse = (apiService.getWeather(mSupplierPagination.get(),
                    AppConstants.KEY).execute().body());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (weatherResponse != null && weatherResponse.weatherInfoList != null) {
            return weatherResponse.weatherInfoList.size() > 0 ? Result.success(weatherResponse.weatherInfoList) : Result.<List<HeWeatherInfo>>failure();
        } else {
            return Result.failure();
        }
    }


}
