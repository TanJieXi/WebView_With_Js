package com.example.stu.demo3.rxjavaretrofit2;

/**
 * Created by TanJieXi on 2018/3/26.
 */

public class WeatherBean {

    /**
     * resultcode : 200
     * reason : successed!
     * result : {"sk":{"temp":"20","wind_direction":"西风","wind_strength":"2级","humidity":"63%","time":"13:05"},"today":{"temperature":"13℃~23℃","weather":"阵雨转多云","weather_id":{"fa":"03","fb":"01"},"wind":"持续无风向微风","week":"星期一","city":"成都","date_y":"2018年03月26日","dressing_index":"较舒适","dressing_advice":"建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。","uv_index":"弱","comfort_index":"","wash_index":"不宜","travel_index":"较不宜","exercise_index":"较不宜","drying_index":""},"future":{"day_20180326":{"temperature":"13℃~23℃","weather":"阵雨转多云","weather_id":{"fa":"03","fb":"01"},"wind":"持续无风向微风","week":"星期一","date":"20180326"},"day_20180327":{"temperature":"13℃~27℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"持续无风向微风","week":"星期二","date":"20180327"},"day_20180328":{"temperature":"14℃~25℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"持续无风向微风","week":"星期三","date":"20180328"},"day_20180329":{"temperature":"16℃~26℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"持续无风向微风","week":"星期四","date":"20180329"},"day_20180330":{"temperature":"15℃~23℃","weather":"多云转小雨","weather_id":{"fa":"01","fb":"07"},"wind":"持续无风向微风","week":"星期五","date":"20180330"},"day_20180331":{"temperature":"16℃~26℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"持续无风向微风","week":"星期六","date":"20180331"},"day_20180401":{"temperature":"16℃~26℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"持续无风向微风","week":"星期日","date":"20180401"}}}
     * error_code : 0
     */

    private String resultcode;
    private String reason;
    private ResultBean result;
    private int error_code;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean {
        @Override
        public String toString() {
            return "ResultBean{" +
                    "sk=" + sk +
                    ", today=" + today +
                    ", future=" + future +
                    '}';
        }

        /**
         * sk : {"temp":"20","wind_direction":"西风","wind_strength":"2级","humidity":"63%","time":"13:05"}
         * today : {"temperature":"13℃~23℃","weather":"阵雨转多云","weather_id":{"fa":"03","fb":"01"},"wind":"持续无风向微风","week":"星期一","city":"成都","date_y":"2018年03月26日","dressing_index":"较舒适","dressing_advice":"建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。","uv_index":"弱","comfort_index":"","wash_index":"不宜","travel_index":"较不宜","exercise_index":"较不宜","drying_index":""}
         * future : {"day_20180326":{"temperature":"13℃~23℃","weather":"阵雨转多云","weather_id":{"fa":"03","fb":"01"},"wind":"持续无风向微风","week":"星期一","date":"20180326"},"day_20180327":{"temperature":"13℃~27℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"持续无风向微风","week":"星期二","date":"20180327"},"day_20180328":{"temperature":"14℃~25℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"持续无风向微风","week":"星期三","date":"20180328"},"day_20180329":{"temperature":"16℃~26℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"持续无风向微风","week":"星期四","date":"20180329"},"day_20180330":{"temperature":"15℃~23℃","weather":"多云转小雨","weather_id":{"fa":"01","fb":"07"},"wind":"持续无风向微风","week":"星期五","date":"20180330"},"day_20180331":{"temperature":"16℃~26℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"持续无风向微风","week":"星期六","date":"20180331"},"day_20180401":{"temperature":"16℃~26℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"持续无风向微风","week":"星期日","date":"20180401"}}
         */



        private SkBean sk;
        private TodayBean today;
        private FutureBean future;

        public SkBean getSk() {
            return sk;
        }

        public void setSk(SkBean sk) {
            this.sk = sk;
        }

        public TodayBean getToday() {
            return today;
        }

        public void setToday(TodayBean today) {
            this.today = today;
        }

        public FutureBean getFuture() {
            return future;
        }

        public void setFuture(FutureBean future) {
            this.future = future;
        }

        public static class SkBean {
            @Override
            public String toString() {
                return "SkBean{" +
                        "temp='" + temp + '\'' +
                        ", wind_direction='" + wind_direction + '\'' +
                        ", wind_strength='" + wind_strength + '\'' +
                        ", humidity='" + humidity + '\'' +
                        ", time='" + time + '\'' +
                        '}';
            }

            /**
             * temp : 20
             * wind_direction : 西风
             * wind_strength : 2级
             * humidity : 63%
             * time : 13:05
             */

            private String temp;
            private String wind_direction;
            private String wind_strength;
            private String humidity;
            private String time;

            public String getTemp() {
                return temp;
            }

            public void setTemp(String temp) {
                this.temp = temp;
            }

            public String getWind_direction() {
                return wind_direction;
            }

            public void setWind_direction(String wind_direction) {
                this.wind_direction = wind_direction;
            }

            public String getWind_strength() {
                return wind_strength;
            }

            public void setWind_strength(String wind_strength) {
                this.wind_strength = wind_strength;
            }

            public String getHumidity() {
                return humidity;
            }

            public void setHumidity(String humidity) {
                this.humidity = humidity;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

        public static class TodayBean {
            /**
             * temperature : 13℃~23℃
             * weather : 阵雨转多云
             * weather_id : {"fa":"03","fb":"01"}
             * wind : 持续无风向微风
             * week : 星期一
             * city : 成都
             * date_y : 2018年03月26日
             * dressing_index : 较舒适
             * dressing_advice : 建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。
             * uv_index : 弱
             * comfort_index :
             * wash_index : 不宜
             * travel_index : 较不宜
             * exercise_index : 较不宜
             * drying_index :
             */

            private String temperature;
            private String weather;
            private WeatherIdBean weather_id;
            private String wind;
            private String week;
            private String city;
            private String date_y;
            private String dressing_index;
            private String dressing_advice;
            private String uv_index;
            private String comfort_index;
            private String wash_index;
            private String travel_index;
            private String exercise_index;
            private String drying_index;

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }

            public String getWeather() {
                return weather;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }

            public WeatherIdBean getWeather_id() {
                return weather_id;
            }

            public void setWeather_id(WeatherIdBean weather_id) {
                this.weather_id = weather_id;
            }

            public String getWind() {
                return wind;
            }

            public void setWind(String wind) {
                this.wind = wind;
            }

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getDate_y() {
                return date_y;
            }

            public void setDate_y(String date_y) {
                this.date_y = date_y;
            }

            public String getDressing_index() {
                return dressing_index;
            }

            public void setDressing_index(String dressing_index) {
                this.dressing_index = dressing_index;
            }

            public String getDressing_advice() {
                return dressing_advice;
            }

            public void setDressing_advice(String dressing_advice) {
                this.dressing_advice = dressing_advice;
            }

            public String getUv_index() {
                return uv_index;
            }

            public void setUv_index(String uv_index) {
                this.uv_index = uv_index;
            }

            public String getComfort_index() {
                return comfort_index;
            }

            public void setComfort_index(String comfort_index) {
                this.comfort_index = comfort_index;
            }

            public String getWash_index() {
                return wash_index;
            }

            public void setWash_index(String wash_index) {
                this.wash_index = wash_index;
            }

            public String getTravel_index() {
                return travel_index;
            }

            public void setTravel_index(String travel_index) {
                this.travel_index = travel_index;
            }

            public String getExercise_index() {
                return exercise_index;
            }

            public void setExercise_index(String exercise_index) {
                this.exercise_index = exercise_index;
            }

            public String getDrying_index() {
                return drying_index;
            }

            public void setDrying_index(String drying_index) {
                this.drying_index = drying_index;
            }

            public static class WeatherIdBean {
                /**
                 * fa : 03
                 * fb : 01
                 */

                private String fa;
                private String fb;

                public String getFa() {
                    return fa;
                }

                public void setFa(String fa) {
                    this.fa = fa;
                }

                public String getFb() {
                    return fb;
                }

                public void setFb(String fb) {
                    this.fb = fb;
                }
            }
        }

        public static class FutureBean {
            @Override
            public String toString() {
                return "FutureBean{" +
                        "day_20180326=" + day_20180326 +
                        ", day_20180327=" + day_20180327 +
                        ", day_20180328=" + day_20180328 +
                        ", day_20180329=" + day_20180329 +
                        ", day_20180330=" + day_20180330 +
                        ", day_20180331=" + day_20180331 +
                        ", day_20180401=" + day_20180401 +
                        '}';
            }

            /**
             * day_20180326 : {"temperature":"13℃~23℃","weather":"阵雨转多云","weather_id":{"fa":"03","fb":"01"},"wind":"持续无风向微风","week":"星期一","date":"20180326"}
             * day_20180327 : {"temperature":"13℃~27℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"持续无风向微风","week":"星期二","date":"20180327"}
             * day_20180328 : {"temperature":"14℃~25℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"持续无风向微风","week":"星期三","date":"20180328"}
             * day_20180329 : {"temperature":"16℃~26℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"持续无风向微风","week":"星期四","date":"20180329"}
             * day_20180330 : {"temperature":"15℃~23℃","weather":"多云转小雨","weather_id":{"fa":"01","fb":"07"},"wind":"持续无风向微风","week":"星期五","date":"20180330"}
             * day_20180331 : {"temperature":"16℃~26℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"持续无风向微风","week":"星期六","date":"20180331"}
             * day_20180401 : {"temperature":"16℃~26℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"持续无风向微风","week":"星期日","date":"20180401"}
             */


            private Day20180326Bean day_20180326;
            private Day20180327Bean day_20180327;
            private Day20180328Bean day_20180328;
            private Day20180329Bean day_20180329;
            private Day20180330Bean day_20180330;
            private Day20180331Bean day_20180331;
            private Day20180401Bean day_20180401;

            public Day20180326Bean getDay_20180326() {
                return day_20180326;
            }

            public void setDay_20180326(Day20180326Bean day_20180326) {
                this.day_20180326 = day_20180326;
            }

            public Day20180327Bean getDay_20180327() {
                return day_20180327;
            }

            public void setDay_20180327(Day20180327Bean day_20180327) {
                this.day_20180327 = day_20180327;
            }

            public Day20180328Bean getDay_20180328() {
                return day_20180328;
            }

            public void setDay_20180328(Day20180328Bean day_20180328) {
                this.day_20180328 = day_20180328;
            }

            public Day20180329Bean getDay_20180329() {
                return day_20180329;
            }

            public void setDay_20180329(Day20180329Bean day_20180329) {
                this.day_20180329 = day_20180329;
            }

            public Day20180330Bean getDay_20180330() {
                return day_20180330;
            }

            public void setDay_20180330(Day20180330Bean day_20180330) {
                this.day_20180330 = day_20180330;
            }

            public Day20180331Bean getDay_20180331() {
                return day_20180331;
            }

            public void setDay_20180331(Day20180331Bean day_20180331) {
                this.day_20180331 = day_20180331;
            }

            public Day20180401Bean getDay_20180401() {
                return day_20180401;
            }

            public void setDay_20180401(Day20180401Bean day_20180401) {
                this.day_20180401 = day_20180401;
            }

            public static class Day20180326Bean {
                /**
                 * temperature : 13℃~23℃
                 * weather : 阵雨转多云
                 * weather_id : {"fa":"03","fb":"01"}
                 * wind : 持续无风向微风
                 * week : 星期一
                 * date : 20180326
                 */

                private String temperature;
                private String weather;
                private WeatherIdBeanX weather_id;
                private String wind;
                private String week;
                private String date;

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public WeatherIdBeanX getWeather_id() {
                    return weather_id;
                }

                public void setWeather_id(WeatherIdBeanX weather_id) {
                    this.weather_id = weather_id;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public static class WeatherIdBeanX {
                    /**
                     * fa : 03
                     * fb : 01
                     */

                    private String fa;
                    private String fb;

                    public String getFa() {
                        return fa;
                    }

                    public void setFa(String fa) {
                        this.fa = fa;
                    }

                    public String getFb() {
                        return fb;
                    }

                    public void setFb(String fb) {
                        this.fb = fb;
                    }
                }
            }

            public static class Day20180327Bean {
                /**
                 * temperature : 13℃~27℃
                 * weather : 多云
                 * weather_id : {"fa":"01","fb":"01"}
                 * wind : 持续无风向微风
                 * week : 星期二
                 * date : 20180327
                 */

                private String temperature;
                private String weather;
                private WeatherIdBeanXX weather_id;
                private String wind;
                private String week;
                private String date;

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public WeatherIdBeanXX getWeather_id() {
                    return weather_id;
                }

                public void setWeather_id(WeatherIdBeanXX weather_id) {
                    this.weather_id = weather_id;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public static class WeatherIdBeanXX {
                    /**
                     * fa : 01
                     * fb : 01
                     */

                    private String fa;
                    private String fb;

                    public String getFa() {
                        return fa;
                    }

                    public void setFa(String fa) {
                        this.fa = fa;
                    }

                    public String getFb() {
                        return fb;
                    }

                    public void setFb(String fb) {
                        this.fb = fb;
                    }
                }
            }

            public static class Day20180328Bean {
                /**
                 * temperature : 14℃~25℃
                 * weather : 多云
                 * weather_id : {"fa":"01","fb":"01"}
                 * wind : 持续无风向微风
                 * week : 星期三
                 * date : 20180328
                 */

                private String temperature;
                private String weather;
                private WeatherIdBeanXXX weather_id;
                private String wind;
                private String week;
                private String date;

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public WeatherIdBeanXXX getWeather_id() {
                    return weather_id;
                }

                public void setWeather_id(WeatherIdBeanXXX weather_id) {
                    this.weather_id = weather_id;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public static class WeatherIdBeanXXX {
                    /**
                     * fa : 01
                     * fb : 01
                     */

                    private String fa;
                    private String fb;

                    public String getFa() {
                        return fa;
                    }

                    public void setFa(String fa) {
                        this.fa = fa;
                    }

                    public String getFb() {
                        return fb;
                    }

                    public void setFb(String fb) {
                        this.fb = fb;
                    }
                }
            }

            public static class Day20180329Bean {
                /**
                 * temperature : 16℃~26℃
                 * weather : 多云
                 * weather_id : {"fa":"01","fb":"01"}
                 * wind : 持续无风向微风
                 * week : 星期四
                 * date : 20180329
                 */

                private String temperature;
                private String weather;
                private WeatherIdBeanXXXX weather_id;
                private String wind;
                private String week;
                private String date;

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public WeatherIdBeanXXXX getWeather_id() {
                    return weather_id;
                }

                public void setWeather_id(WeatherIdBeanXXXX weather_id) {
                    this.weather_id = weather_id;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public static class WeatherIdBeanXXXX {
                    /**
                     * fa : 01
                     * fb : 01
                     */

                    private String fa;
                    private String fb;

                    public String getFa() {
                        return fa;
                    }

                    public void setFa(String fa) {
                        this.fa = fa;
                    }

                    public String getFb() {
                        return fb;
                    }

                    public void setFb(String fb) {
                        this.fb = fb;
                    }
                }
            }

            public static class Day20180330Bean {
                /**
                 * temperature : 15℃~23℃
                 * weather : 多云转小雨
                 * weather_id : {"fa":"01","fb":"07"}
                 * wind : 持续无风向微风
                 * week : 星期五
                 * date : 20180330
                 */

                private String temperature;
                private String weather;
                private WeatherIdBeanXXXXX weather_id;
                private String wind;
                private String week;
                private String date;

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public WeatherIdBeanXXXXX getWeather_id() {
                    return weather_id;
                }

                public void setWeather_id(WeatherIdBeanXXXXX weather_id) {
                    this.weather_id = weather_id;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public static class WeatherIdBeanXXXXX {
                    /**
                     * fa : 01
                     * fb : 07
                     */

                    private String fa;
                    private String fb;

                    public String getFa() {
                        return fa;
                    }

                    public void setFa(String fa) {
                        this.fa = fa;
                    }

                    public String getFb() {
                        return fb;
                    }

                    public void setFb(String fb) {
                        this.fb = fb;
                    }
                }
            }

            public static class Day20180331Bean {
                /**
                 * temperature : 16℃~26℃
                 * weather : 多云
                 * weather_id : {"fa":"01","fb":"01"}
                 * wind : 持续无风向微风
                 * week : 星期六
                 * date : 20180331
                 */

                private String temperature;
                private String weather;
                private WeatherIdBeanXXXXXX weather_id;
                private String wind;
                private String week;
                private String date;

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public WeatherIdBeanXXXXXX getWeather_id() {
                    return weather_id;
                }

                public void setWeather_id(WeatherIdBeanXXXXXX weather_id) {
                    this.weather_id = weather_id;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public static class WeatherIdBeanXXXXXX {
                    /**
                     * fa : 01
                     * fb : 01
                     */

                    private String fa;
                    private String fb;

                    public String getFa() {
                        return fa;
                    }

                    public void setFa(String fa) {
                        this.fa = fa;
                    }

                    public String getFb() {
                        return fb;
                    }

                    public void setFb(String fb) {
                        this.fb = fb;
                    }
                }
            }

            public static class Day20180401Bean {
                /**
                 * temperature : 16℃~26℃
                 * weather : 多云
                 * weather_id : {"fa":"01","fb":"01"}
                 * wind : 持续无风向微风
                 * week : 星期日
                 * date : 20180401
                 */

                private String temperature;
                private String weather;
                private WeatherIdBeanXXXXXXX weather_id;
                private String wind;
                private String week;
                private String date;

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public WeatherIdBeanXXXXXXX getWeather_id() {
                    return weather_id;
                }

                public void setWeather_id(WeatherIdBeanXXXXXXX weather_id) {
                    this.weather_id = weather_id;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public static class WeatherIdBeanXXXXXXX {
                    /**
                     * fa : 01
                     * fb : 01
                     */

                    private String fa;
                    private String fb;

                    public String getFa() {
                        return fa;
                    }

                    public void setFa(String fa) {
                        this.fa = fa;
                    }

                    public String getFb() {
                        return fb;
                    }

                    public void setFb(String fb) {
                        this.fb = fb;
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "WeatherBean{" +
                "resultcode='" + resultcode + '\'' +
                ", reason='" + reason + '\'' +
                ", result=" + result +
                ", error_code=" + error_code +
                '}';
    }
}
