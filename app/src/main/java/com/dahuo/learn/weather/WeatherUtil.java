package com.dahuo.learn.weather;

import com.dahuo.learn.startup.R;

/**
 * @author YanLu
 * @since 16/6/6
 */

public class WeatherUtil {
    
    public static int getWeatherIcon(String weather) {
        if("未知".equals(weather)){
            return R.drawable.none;
        } else if("晴".equals(weather)){
            return R.drawable.type_two_sunny;
        }else if("阴".equals(weather)){
            return R.drawable.type_two_cloudy;
        }else if("多云".equals(weather)){
            return R.drawable.type_one_cloudy;
        }else if("少云".equals(weather)){
            return R.drawable.type_two_cloudy;
        }else if("晴间多云".equals(weather)){
            return R.drawable.type_two_cloudytosunny;
        }else if("小雨".equals(weather)){
            return R.drawable.type_two_light_rain;
        }else if("中雨".equals(weather)){
            return R.drawable.type_two_rain;
        }else if("大雨".equals(weather)){
            return R.drawable.type_two_rain;
        }else if("阵雨".equals(weather)){
            return R.drawable.type_two_rain;
        }else if("雷阵雨".equals(weather)){
            return R.drawable.type_two_thunderstorm;
        }else if("霾".equals(weather)){
            return R.drawable.type_two_haze;
        }else if("雾".equals(weather)){
            return R.drawable.type_one_fog;
        }else if("雨夹雪".equals(weather)){
            return R.drawable.type_two_snowrain;
        }
        return R.drawable.none;
//        if (mSetting.getIconType() == 0) {
//            mSetting.putInt("未知", R.drawable.none);
//            mSetting.putInt("晴", R.drawable.type_one_sunny);
//            mSetting.putInt("阴", R.drawable.type_one_cloudy);
//            mSetting.putInt("多云", R.drawable.type_one_cloudy);
//            mSetting.putInt("少云", R.drawable.type_one_cloudy);
//            mSetting.putInt("晴间多云", R.drawable.type_one_cloudytosunny);
//            mSetting.putInt("小雨", R.drawable.type_one_light_rain);
//            mSetting.putInt("中雨", R.drawable.type_one_light_rain);
//            mSetting.putInt("大雨", R.drawable.type_one_heavy_rain);
//            mSetting.putInt("阵雨", R.drawable.type_one_thunderstorm);
//            mSetting.putInt("雷阵雨", R.drawable.type_one_thunder_rain);
//            mSetting.putInt("霾", R.drawable.type_one_fog);
//            mSetting.putInt("雾", R.drawable.type_one_fog);
//        } else {
//            mSetting.putInt("未知", R.drawable.none);
//            mSetting.putInt("晴", R.drawable.type_two_sunny);
//            mSetting.putInt("阴", R.drawable.type_two_cloudy);
//            mSetting.putInt("多云", R.drawable.type_two_cloudy);
//            mSetting.putInt("少云", R.drawable.type_two_cloudy);
//            mSetting.putInt("晴间多云", R.drawable.type_two_cloudytosunny);
//            mSetting.putInt("小雨", R.drawable.type_two_light_rain);
//            mSetting.putInt("中雨", R.drawable.type_two_rain);
//            mSetting.putInt("大雨", R.drawable.type_two_rain);
//            mSetting.putInt("阵雨", R.drawable.type_two_rain);
//            mSetting.putInt("雷阵雨", R.drawable.type_two_thunderstorm);
//            mSetting.putInt("霾", R.drawable.type_two_haze);
//            mSetting.putInt("雾", R.drawable.type_two_fog);
//            mSetting.putInt("雨夹雪", R.drawable.type_two_snowrain);
//        }
    }
}
