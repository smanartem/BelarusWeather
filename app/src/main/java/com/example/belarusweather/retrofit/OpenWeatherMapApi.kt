package com.example.belarusweather.retrofit

import com.example.belarusweather.apiKey
import com.example.belarusweather.model.DataWeather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherMapApi {
    @GET("2.5/forecast?cnt=24&appid=${apiKey}&units=metric")
    suspend fun getData(@Query("q") city : String): Response<DataWeather>

}