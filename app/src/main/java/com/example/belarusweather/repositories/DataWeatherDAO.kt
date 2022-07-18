package com.example.belarusweather.repositories

import androidx.room.*
import com.example.belarusweather.entity.DataWeatherEntity

@Dao
interface DataWeatherDAO {
    @Query("SELECT * FROM table_weather")
    fun getDataWeatherRoom(): Array<DataWeatherEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(data: DataWeatherEntity)

    @Query("DELETE FROM table_weather")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM table_weather")
    suspend fun checkDb(): Int
}