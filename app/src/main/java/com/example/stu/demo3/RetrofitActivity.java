package com.example.stu.demo3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RetrofitActivity extends AppCompatActivity {

    private Retrofit mRetrofit;
    private GitHubService mGitHubService;
    private Call<String> mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        initRefit();


        GitHubService gitHubService = mRetrofit.create(GitHubService.class);
        //mModel = gitHubService.listRepos("Guolei1130");
        try {
            String names = URLDecoder.decode("成都","utf-8");
            mModel = gitHubService.getWeather(names,"json","1","dcc498b952b3f9abdd0c8dcd4e68b8d9");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }

    private void initRefit() {
        /*mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(StringConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();*/


        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://v.juhe.cn")
                .addConverterFactory(StringConverterFactory.create())
                .build();

    }

    public void btnClick(View view) {
        mModel.clone().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("dsfdsgd", response.body().toString());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("dsfdsgd", t.getMessage());
            }
        });


    }
}
