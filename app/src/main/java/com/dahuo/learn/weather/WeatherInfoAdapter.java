package com.dahuo.learn.weather;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dahuo.learn.model.HeWeatherInfo;
import com.dahuo.learn.startup.BR;
import com.dahuo.learn.startup.R;
import com.github.captain_miao.recyclerviewutils.BaseWrapperRecyclerAdapter;

import java.util.List;



/**
 * @author YanLu
 * @since 16/6/06
 */
public class WeatherInfoAdapter extends BaseWrapperRecyclerAdapter<HeWeatherInfo, RecyclerView.ViewHolder> {
    private static final String TAG = "WeatherInfoAdapter";

    public WeatherInfoAdapter() {
    }

    public WeatherInfoAdapter(List<HeWeatherInfo> items) {
        addAll(items);
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_ONE:
                return new NowWeatherViewHolder(inflater.inflate(R.layout.item_temperature, parent, false));
            case TYPE_TWO:
                return new HoursWeatherViewHolder(
                        inflater.inflate(R.layout.item_hour_info, parent, false));
            case TYPE_THREE:
                return new SuggestionViewHolder(
                        inflater.inflate(R.layout.item_suggestion, parent, false));
            case TYPE_FORE:
                return new ForecastViewHolder(inflater.inflate(R.layout.item_forecast, parent, false));
        }
        return null;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        HeWeatherInfo mWeatherData = getItem(0);
        int itemType = getItemViewType(position);
        switch (itemType) {
            case TYPE_ONE:
                ((NowWeatherViewHolder) holder).bind(mWeatherData);
                break;
            case TYPE_TWO:
                ((HoursWeatherViewHolder) holder).bind(mWeatherData);
                break;
            case TYPE_THREE:
                ((SuggestionViewHolder) holder).bind(mWeatherData);
                break;
            case TYPE_FORE:
                ((ForecastViewHolder) holder).bind(mWeatherData);
                break;
            default:
                break;
        }
//        final HeWeatherInfo info = getItem(position);
//        if(holder instanceof ViewHolder){
//            ViewDataBinding binding = ((ViewHolder) holder).getBinding();
//            binding.setVariable(BR.info, info);
//            binding.executePendingBindings();
//        }
    }


    private final int TYPE_ONE = 0;
    private final int TYPE_TWO = 1;
    private final int TYPE_THREE = 2;
    private final int TYPE_FORE = 3;
    @Override
    public int getContentViewType(int dataListIndex) {
        if (dataListIndex == TYPE_ONE) {
            return TYPE_ONE;
        }
        if (dataListIndex == TYPE_TWO) {
            return TYPE_TWO;
        }
        if (dataListIndex == TYPE_THREE) {
            return TYPE_THREE;
        }
        if (dataListIndex == TYPE_FORE) {
            return TYPE_FORE;
        }
        return super.getContentViewType(dataListIndex);
    }

    @Override
    protected int getBasicItemCount() {
        return (mItemList.size() > 0 && mItemList.get(0) != null && mItemList.get(0).isSuccess()) ?  4 : 0;
    }

    /**
     * 当前天气情况
     */
    class NowWeatherViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding mBinding;
        ImageView weatherIcon;
        TextView tempFlu;
        TextView tempMax;
        TextView tempMin;
        TextView tempPm;
        TextView tempQuality;
        CardView cardView;

        public NowWeatherViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }

        public void bind(HeWeatherInfo weather) {
            try {
                ViewDataBinding binding = getBinding();
                binding.setVariable(BR.weather, weather);
                binding.setVariable(BR.imgRes, WeatherUtil.getWeatherIcon(weather.now.cond.txt));
                binding.executePendingBindings();
                // tempFlu.setText(String.format("%s℃", weather.now.tmp));
                // tempMax.setText(String.format("↑ %s °", weather.dailyForecast.get(0).tmp.max));
                // tempMin.setText(String.format("↓ %s °", weather.dailyForecast.get(0).tmp.min));
                // tempPm.setText(Util.safeText("PM25： ", weather.aqi.city.pm25));
                // tempQuality.setText(Util.safeText("空气质量： ", weather.aqi.city.qlty));
                //ImageLoader.load(context, mSetting.getInt(weather.now.cond.txt, R.mipmap.none), weatherIcon);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }

        public ViewDataBinding getBinding() {
            return mBinding;
        }
    }

    /**
     * 当日小时预告
     */
    class HoursWeatherViewHolder extends RecyclerView.ViewHolder {
        //HeWeatherInfo mWeatherData = getItem(0);
        //private LinearLayout itemHourInfoLinearLayout;
        //private TextView[] mClock = new TextView[mWeatherData.hourlyForecast.size()];
        //private TextView[] mTemp = new TextView[mWeatherData.hourlyForecast.size()];
        //private TextView[] mHumidity = new TextView[mWeatherData.hourlyForecast.size()];
        //private TextView[] mWind = new TextView[mWeatherData.hourlyForecast.size()];

        public HoursWeatherViewHolder(View itemView) {
            super(itemView);
//            itemHourInfoLinearLayout = (LinearLayout) itemView.findViewById(R.id.item_hour_info_linearlayout);
//
//            for (int i = 0; i < mWeatherData.hourlyForecast.size(); i++) {
//                View view = View.inflate(itemView.getContext(), R.layout.item_hour_info_line, null);
//                mClock[i] = (TextView) view.findViewById(R.id.one_clock);
//                mTemp[i] = (TextView) view.findViewById(R.id.one_temp);
//                mHumidity[i] = (TextView) view.findViewById(R.id.one_humidity);
//                mWind[i] = (TextView) view.findViewById(R.id.one_wind);
//                itemHourInfoLinearLayout.addView(view);
//            }
        }

        public void bind(HeWeatherInfo weather) {

//            try {
//                for (int i = 0; i < weather.hourlyForecast.size(); i++) {
//                    //s.subString(s.length-3,s.length);
//                    //第一个参数是开始截取的位置，第二个是结束位置。
//                    String mDate = weather.hourlyForecast.get(i).date;
//                    mClock[i].setText(
//                        mDate.substring(mDate.length() - 5, mDate.length()));
//                    mTemp[i].setText(
//                        String.format("%s°", weather.hourlyForecast.get(i).tmp));
//                    mHumidity[i].setText(
//                        String.format("%s%%", weather.hourlyForecast.get(i).hum)
//                    );
//                    mWind[i].setText(
//                        String.format("%sKm", weather.hourlyForecast.get(i).wind.spd)
//                    );
//                }
//            } catch (Exception e) {
//                //Snackbar.make(holder.itemView, R.string.api_error, Snackbar.LENGTH_SHORT).show();
//                Log.e(TAG, e.toString());
//            }
        }
    }

    /**
     * 当日建议
     */
    class SuggestionViewHolder extends RecyclerView.ViewHolder {
        TextView clothBrief;
        TextView clothTxt;
        TextView sportBrief;
        TextView sportTxt;
        TextView travelBrief;
        TextView travelTxt;
        TextView fluBrief;
        TextView fluTxt;

        public SuggestionViewHolder(View itemView) {
            super(itemView);
        }

        public void bind(HeWeatherInfo weather) {
            try {

                clothBrief.setText(String.format("穿衣指数---%s", weather.suggestion.drsg.brf));
                clothTxt.setText(weather.suggestion.drsg.txt);

                sportBrief.setText(String.format("运动指数---%s", weather.suggestion.sport.brf));
                sportTxt.setText(weather.suggestion.sport.txt);

                travelBrief.setText(String.format("旅游指数---%s", weather.suggestion.trav.brf));
                travelTxt.setText(weather.suggestion.trav.txt);

                fluBrief.setText(String.format("感冒指数---%s", weather.suggestion.flu.brf));
                fluTxt.setText(weather.suggestion.flu.txt);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }
    }

    /**
     * 未来天气
     */
    class ForecastViewHolder extends RecyclerView.ViewHolder {
//        HeWeatherInfo mWeatherData = getItem(0);
//        private LinearLayout forecastLinear;
//        private TextView[] forecastDate = new TextView[mWeatherData.dailyForecast.size()];
//        private TextView[] forecastTemp = new TextView[mWeatherData.dailyForecast.size()];
//        private TextView[] forecastTxt = new TextView[mWeatherData.dailyForecast.size()];
//        private ImageView[] forecastIcon = new ImageView[mWeatherData.dailyForecast.size()];

        public ForecastViewHolder(View itemView) {
            super(itemView);
//            forecastLinear = (LinearLayout) itemView.findViewById(R.id.forecast_linear);
//            for (int i = 0; i < mWeatherData.dailyForecast.size(); i++) {
//                View view = View.inflate(itemView.getContext(), R.layout.item_forecast_line, null);
//                forecastDate[i] = (TextView) view.findViewById(R.id.forecast_date);
//                forecastTemp[i] = (TextView) view.findViewById(R.id.forecast_temp);
//                forecastTxt[i] = (TextView) view.findViewById(R.id.forecast_txt);
//                forecastIcon[i] = (ImageView) view.findViewById(R.id.forecast_icon);
//                forecastLinear.addView(view);
//            }
        }

        public void bind(HeWeatherInfo weather) {
//            try {
//                //今日 明日
//                forecastDate[0].setText("今日");
//                forecastDate[1].setText("明日");
//                for (int i = 0; i < weather.dailyForecast.size(); i++) {
//                    if (i > 1) {
//                        try {
//                            forecastDate[i].setText(
//                                Util.dayForWeek(weather.dailyForecast.get(i).date));
//                        } catch (Exception e) {
//                            Log.e(TAG, e.toString());
//                        }
//                    }
//                    //ImageLoader.load(mContext, mSetting.getInt(weather.dailyForecast.get(i).cond.txtD, R.mipmap.none), forecastIcon[i]);
//                    forecastTemp[i].setText(
//                        String.format("%s° %s°",
//                            weather.dailyForecast.get(i).tmp.min,
//                            weather.dailyForecast.get(i).tmp.max));
//                    forecastTxt[i].setText(
//                        String.format("%s。 最高%s℃。 %s %s %s km/h。 降水几率 %s%%。",
//                            weather.dailyForecast.get(i).cond.txtD,
//                            weather.dailyForecast.get(i).tmp.max,
//                            weather.dailyForecast.get(i).wind.sc,
//                            weather.dailyForecast.get(i).wind.dir,
//                            weather.dailyForecast.get(i).wind.spd,
//                            weather.dailyForecast.get(i).pop));
//                }
//            } catch (Exception e) {
//                Log.e(TAG, e.toString());
//            }
        }
    }

}
