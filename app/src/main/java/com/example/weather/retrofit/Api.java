package com.example.weather.retrofit;

import com.example.weather.model.Pojo;
import com.example.weather.model.WeatherDetailPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @GET("/pincode/{PINCODE}/")
    Call<List<Pojo>> getPinCode(@Path("PINCODE") int value);

    @GET("/data/2.5/weather")
    Call<WeatherDetailPojo> getWeatherDetail(@Query("q") String city ,
                                             @Query("appid") String key);

}
