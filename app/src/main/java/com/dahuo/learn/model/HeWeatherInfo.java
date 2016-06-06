package com.dahuo.learn.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @author YanLu
 * @since 16/6/6
 */

public class HeWeatherInfo extends BaseModel {
    public static final String SUCCESS = "ok";

    public BasicEntity basic;
    public String status;
    public AqiEntity aqi;
    public AqiEntity alarms;
    public NowEntity now;
    public List<DailyForecastEntity> dailyForecast;
    public List<HourlyForecastEntity> hourlyForecast;
    public SuggestionEntity suggestion;


    public boolean isSuccess() {
        return SUCCESS.equalsIgnoreCase(status);
    }


    public static class AqiEntity implements Serializable {
        /**
         * aqi : 99
         * co : 1
         * no2 : 87
         * o3 : 36
         * pm10 : 106
         * pm25 : 74
         * qlty : 良
         * so2 : 16
         */

        public CityEntity city;

        public static class CityEntity implements Serializable {
            public String aqi;
            public String co;
            public String no2;
            public String o3;
            public String pm10;
            public String pm25;
            public String qlty;
            public String so2;
        }
    }

    public static class BasicEntity extends BaseModel {
        public String city;
        public String cnty;
        public String id;
        public String lat;
        public String lon;

        /**
         * loc : 2016-02-18 21:04
         * utc : 2016-02-18 13:04
         */
        public UpdateEntity update;

        public static class UpdateEntity extends BaseModel {
            public String loc;
            public String utc;
        }
    }

    public static class NowEntity implements Serializable {
        /**
         * code : 101
         * txt : 多云
         */

        public CondEntity cond;
        public String fl;
        public String hum;
        public String pcpn;
        public String pres;
        public String tmp;
        public String vis;
        /**
         * deg : 20
         * dir : 西北风
         * sc : 4-5
         * spd : 17
         */

        public WindEntity wind;

        public static class CondEntity implements Serializable {
            public String code;
            public String txt;
        }

        public static class WindEntity implements Serializable {
            public String deg;
            public String dir;
            public String sc;
            public String spd;
        }
    }

    public static class SuggestionEntity implements Serializable {
        /**
         * brf : 较舒适
         * txt : 白天天气阴沉，会感到有点儿凉，但大部分人完全可以接受。
         */

        public ComfEntity comf;
        /**
         * brf : 较适宜
         * txt : 较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。
         */

        public CwEntity cw;
        /**
         * brf : 较冷
         * txt : 建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。
         */

        public DrsgEntity drsg;
        /**
         * brf : 较易发
         * txt : 昼夜温差较大，较易发生感冒，请适当增减衣服。体质较弱的朋友请注意防护。
         */

        public FluEntity flu;
        /**
         * brf : 较适宜
         * txt : 阴天，较适宜进行各种户内外运动。
         */

        public SportEntity sport;
        /**
         * brf : 适宜
         * txt : 天气较好，温度适宜，总体来说还是好天气哦，这样的天气适宜旅游，您可以尽情地享受大自然的风光。
         */

        public TravEntity trav;
        /**
         * brf : 最弱
         * txt : 属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。
         */

        public UvEntity uv;

        public static class ComfEntity implements Serializable {
            public String brf;
            public String txt;
        }

        public static class CwEntity implements Serializable {
            public String brf;
            public String txt;
        }

        public static class DrsgEntity implements Serializable {
            public String brf;
            public String txt;
        }

        public static class FluEntity implements Serializable {
            public String brf;
            public String txt;
        }

        public static class SportEntity implements Serializable {
            public String brf;
            public String txt;
        }

        public static class TravEntity implements Serializable {
            public String brf;
            public String txt;
        }

        public static class UvEntity implements Serializable {
            public String brf;
            public String txt;
        }
    }

    public static class DailyForecastEntity implements Serializable {
        /**
         * sr : 07:30
         * ss : 18:44
         */

        public AstroEntity astro;
        /**
         * code_d : 100
         * code_n : 104
         * txt_d : 晴
         * txt_n : 阴
         */

        public CondEntity cond;
        public String date;
        public String hum;
        public String pcpn;
        public String pop;
        public String pres;
        /**
         * max : 19
         * min : 7
         */

        public TmpEntity tmp;
        public String vis;
        /**
         * deg : 54
         * dir : 无持续风向
         * sc : 微风
         * spd : 6
         */

        public WindEntity wind;

        public static class AstroEntity implements Serializable {
            public String sr;
            public String ss;
        }

        public static class CondEntity implements Serializable {
            public String codeD;
            public String codeN;
            public String txtD;
            public String txtN;
        }

        public static class TmpEntity implements Serializable {
            public String max;
            public String min;
        }

        public static class WindEntity implements Serializable {
            public String deg;
            public String dir;
            public String sc;
            public String spd;
        }
    }

    public static class HourlyForecastEntity implements Serializable {
        @SerializedName("date")
        public String date;
        @SerializedName("hum")
        public String hum;
        @SerializedName("pop")
        public String pop;
        @SerializedName("pres")
        public String pres;
        @SerializedName("tmp")
        public String tmp;
        /**
         * deg : 13
         * dir : 东北风
         * sc : 微风
         * spd : 16
         */

        @SerializedName("wind")
        public WindEntity wind;

        public static class WindEntity implements Serializable {
            public String deg;
            public String dir;
            public String sc;
            public String spd;
        }
    }
}
