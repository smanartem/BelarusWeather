package com.example.belarusweather.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    lateinit var openWeatherMapApi: OpenWeatherMapApi

    fun init() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        openWeatherMapApi = retrofit.create(OpenWeatherMapApi::class.java)
    }
}