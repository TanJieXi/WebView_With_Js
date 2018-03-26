package com.example.stu.demo3.rxjavaretrofit2;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by TanJieXi on 2018/3/26.
 */

public interface Api {

    @GET("weather/index")
    Observable<String> getWeather(@Query("cityname") String name,
                                  @Query("dtype") String dtype,
                                  @Query("format") String format,
                                  @Query("key") String key);
    @GET("weather/index")
    Observable<String> getWeather(@QueryMap(encoded = true) Map<String,String> params);

    @GET("users/{user}")
    Observable<String> getOthers(@Path("user") String user);
}
