package com.example.belarusweather.repositories

import androidx.lifecycle.MutableLiveData
import com.example.belarusweather.entity.DataWeatherEntity
import com.example.belarusweather.model.Data
import com.example.belarusweather.retrofit.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat


class DataWeatherRepository(private val dataWeatherDao: DataWeatherDAO) {

    var allData = MutableLiveData<Array<DataWeatherEntity>>()

    var city: String = ""

    private fun isToday(dbDate: Long): Boolean {
        val currentDate = System.currentTimeMillis()
        val formatter = SimpleDateFormat("dd-MM-yyyy")
        return formatter.format(currentDate) == formatter.format(dbDate * 1000)
    }


    private suspend fun getDataNetwork(city: String): Array<Data> {
        val dataNetwork = RetrofitClient.openWeatherMapApi.getData(city).body()!!
        val fourDaysData: Array<Data> = arrayOf(
            dataNetwork.list[0],
            dataNetwork.list[7],
            dataNetwork.list[15],
            dataNetwork.list[23]
        )
        return fourDaysData
    }

    suspend fun saveToDb(array: Array<Data>, city: String) {
        for (n in array.indices) {
            insert(fromDtoToEntity(array[n], city))
        }
    }

    private fun fromDtoToEntity(dto: Data, city: String): DataWeatherEntity {
        val data = DataWeatherEntity(
            dto.dt, city, dto.main.temp, dto.weather[0].icon, dto.main.pressure,
            dto.main.humidity, dto.main.feels_like, dto.wind.speed
        )
        return data
    }

    private suspend fun insert(dataWeather: DataWeatherEntity) {
        dataWeatherDao.insert(dataWeather)
    }

    private suspend fun deleteAll() {
        dataWeatherDao.deleteAll()
    }

    private suspend fun checkDb(): Int {
        return dataWeatherDao.checkDb()
    }

    fun getWeather(refresh: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            if (refresh) {
                deleteAll()
                val dataNetwork = getDataNetwork(city)
                saveToDb(dataNetwork, city)
                allData.postValue(dataWeatherDao.getDataWeatherRoom())
            } else {
                if (checkDb() == 0) {
                    saveToDb(getDataNetwork(city), city)
                } else {
                    val dataLocal = dataWeatherDao.getDataWeatherRoom()
                    if (!isToday(dataLocal[0].dt)) {
                        deleteAll()
                        val dataNetwork = getDataNetwork(city)
                        saveToDb(dataNetwork, city)
                        allData.postValue(dataWeatherDao.getDataWeatherRoom())
                    } else {
                        allData.postValue(dataLocal)
                    }
                }
            }
        }
    }
}



