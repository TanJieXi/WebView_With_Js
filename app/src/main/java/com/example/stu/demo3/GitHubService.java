package com.example.stu.demo3;

/**
 * Created by TanJieXi on 2018/3/23.
 */

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface GitHubService {
    @GET("users/{user}")
    Call<String> listRepos(@Path("user") String user);


    @GET("weather/index")
    Call<String> getWeather(@Query("cityname") String name,
                            @Query("dtype") String dtype,
                            @Query("format") String format,
                            @Query("key") String key);

}
