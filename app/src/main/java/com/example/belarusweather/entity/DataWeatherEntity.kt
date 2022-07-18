package com.example.belarusweather.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_weather")
class DataWeatherEntity(
    @ColumnInfo(name = "dt") val dt: Long,
    @ColumnInfo(name = "city") val city: String,
    @ColumnInfo(name = "temperature") val temperature: Double,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "pressure") val pressure: Int,
    @ColumnInfo(name = "humidity") val humidity: Int,
    @ColumnInfo(name = "feelLike") val feelLike: Double,
    @ColumnInfo(name = "wind") val wind: Double
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}
