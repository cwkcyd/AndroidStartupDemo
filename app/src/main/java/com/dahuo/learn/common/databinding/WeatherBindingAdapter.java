package com.dahuo.learn.common.databinding;

import android.databinding.BindingAdapter;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dahuo.learn.model.HeWeatherInfo;
import com.dahuo.learn.startup.R;
import com.dahuo.learn.utils.Util;
import com.dahuo.learn.weather.WeatherUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * @author YanLu
 * @since 16/6/7
 */
public class WeatherBindingAdapter {
    private static final String TAG = "WeatherBindingAdapter";

    @BindingAdapter({"forecastWeather"})
    public static void showForecastWeatherView(LinearLayout viewGroup, List<HeWeatherInfo.DailyForecastEntity> dailyForecast) {
        viewGroup.removeAllViews();
        int count = dailyForecast != null ? dailyForecast.size() : 0;
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                HeWeatherInfo.DailyForecastEntity entity = dailyForecast.get(i);
                View view = View.inflate(viewGroup.getContext(), R.layout.item_forecast_line, null);
                TextView forecastDate = (TextView) view.findViewById(R.id.forecast_date);
                TextView forecastTemp = (TextView) view.findViewById(R.id.forecast_temp);
                TextView forecastTxt = (TextView) view.findViewById(R.id.forecast_txt);
                ImageView forecastIcon = (ImageView) view.findViewById(R.id.forecast_icon);
                try {
                    //今日 明日
                    if (i == 0) {
                        forecastDate.setText("今日");
                    } else if (i == 1) {
                        forecastDate.setText("明日");
                    }

                    if (i > 1) {
                        try {
                            forecastDate.setText(Util.dayForWeek(entity.date));
                        } catch (Exception e) {
                            Log.e(TAG, e.toString());
                        }
                    }
                    Picasso.with(viewGroup.getContext())
                            .load(WeatherUtil.getWeatherIcon(entity.cond.txtD))
                            .into(forecastIcon);
                    forecastTemp.setText(
                            String.format("%s° %s°",
                                    entity.tmp.min,
                                    entity.tmp.max));
                    forecastTxt.setText(
                            String.format("%s。 最高%s℃。 %s %s %s km/h。 降水几率 %s%%。",
                                    entity.cond.txtD,
                                    entity.tmp.max,
                                    entity.wind.sc,
                                    entity.wind.dir,
                                    entity.wind.spd,
                                    entity.pop));
                    viewGroup.addView(view);
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            }
        }
    }
    @BindingAdapter({"hoursWeather"})
    public static void showHoursWeatherView(LinearLayout viewGroup, List<HeWeatherInfo.HourlyForecastEntity> hourlyForecast){
        viewGroup.removeAllViews();
        int count = hourlyForecast != null ? hourlyForecast.size() : 0;
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                View view = View.inflate(viewGroup.getContext(), R.layout.item_hour_info_line, null);
                TextView mClock = (TextView) view.findViewById(R.id.one_clock);
                TextView mTemp = (TextView) view.findViewById(R.id.one_temp);
                TextView mHumidity = (TextView) view.findViewById(R.id.one_humidity);
                TextView mWind = (TextView) view.findViewById(R.id.one_wind);

                try {
                    //第一个参数是开始截取的位置，第二个是结束位置。
                    HeWeatherInfo.HourlyForecastEntity entity = hourlyForecast.get(i);
                    String mDate = hourlyForecast.get(i).date;
                    mClock.setText(mDate.substring(mDate.length() - 5, mDate.length()));
                    mTemp.setText(String.format("%s°", entity.tmp));
                    mHumidity.setText(String.format("%s%%", entity.hum));
                    mWind.setText(String.format("%sKm", entity.wind.spd));
                    viewGroup.addView(view);
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            }
        }
    }
}
