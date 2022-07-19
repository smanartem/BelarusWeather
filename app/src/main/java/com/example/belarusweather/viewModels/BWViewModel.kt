package com.example.belarusweather.viewModels

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.example.belarusweather.entity.DataWeatherEntity
import com.example.belarusweather.isInternetAvailable
import com.example.belarusweather.repositories.DataWeatherDatabase
import com.example.belarusweather.repositories.DataWeatherRepository

class BWViewModel(application: Application) : AndroidViewModel(application) {

    var repository: DataWeatherRepository
    var dataWeather: MutableLiveData<Array<DataWeatherEntity>>

    init {
        val dao = DataWeatherDatabase.getDatabase(application).getDataWeatherDao()
        repository = DataWeatherRepository(dao)
        dataWeather = repository.allData
    }

    fun setCity(city: String, context: Context) {
        repository.city = city
        repository.getWeather(isInternetAvailable(context))
    }
}
