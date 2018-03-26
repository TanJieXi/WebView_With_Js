package com.example.stu.demo3.rxjavaretrofit2;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
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

    @GET("users/{user}")
    Observable<String> getOthers(@Path("user") String user);
}
