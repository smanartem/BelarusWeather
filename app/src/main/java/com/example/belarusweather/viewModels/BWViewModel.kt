package com.example.belarusweather.viewModels

import android.app.Application
import androidx.lifecycle.*
import com.example.belarusweather.entity.DataWeatherEntity
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

    fun setCity(city: String){
        repository.city = city
        repository.getWeather(true)
    }
}
