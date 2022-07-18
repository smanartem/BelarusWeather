package com.example.belarusweather.repositories

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.belarusweather.entity.DataWeatherEntity

@Database(entities = arrayOf(DataWeatherEntity::class), version = 1, exportSchema = false)

abstract class DataWeatherDatabase : RoomDatabase() {

    abstract fun getDataWeatherDao(): DataWeatherDAO

    companion object {
        @Volatile
        private var INSTANCE: DataWeatherDatabase? = null

        fun getDatabase(context: Context): DataWeatherDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataWeatherDatabase::class.java,
                    "table_weather"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}