package com.example.belarusweather.model

import java.io.Serializable

data class DataWeather(
    val list: Array<Data>,
    val city: City
) : Serializable

data class Data(
    val dt: Long,
    val main: MainInfo,
    val weather: Array<Weather>,
    val wind: Wind,
    val dt_txt: String,
) : Serializable


data class City(
    val id: Int,
    val name: String
) : Serializable


data class MainInfo(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int
) : Serializable


data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
) : Serializable


data class Wind(
    val speed: Double, val deg: Int
) : Serializable

