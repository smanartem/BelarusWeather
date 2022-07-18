package com.example.belarusweather

import android.app.Application
import com.example.belarusweather.retrofit.RetrofitClient

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        RetrofitClient.init()
    }
}