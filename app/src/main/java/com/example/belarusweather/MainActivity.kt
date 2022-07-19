package com.example.belarusweather

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.net.*
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.belarusweather.entity.DataWeatherEntity
import com.example.belarusweather.viewModels.BWViewModel
import com.squareup.picasso.Picasso


const val apiKey = "faeb023ddb47170b027bafb46c3830e1"

class MainActivity : AppCompatActivity() {
    val model: BWViewModel by viewModels()
    val adapter = RecyclerViewAdapter()
    lateinit var prefs: SharedPreferences
    var cityStart: String = ""
    lateinit var currentCity: String

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = getSharedPreferences(
            "settings", Context.MODE_PRIVATE
        )

        val city: TextView = findViewById(R.id.city)
        val temperature: TextView = findViewById(R.id.temp)
        val image: ImageView = findViewById(R.id.currentWeatherimg)
        val pressure: TextView = findViewById(R.id.pressure)
        val humidity: TextView = findViewById(R.id.humadity)
        val feels: TextView = findViewById(R.id.feelsTemp)
        val windy: TextView = findViewById(R.id.windParam)

        val recyclerview: RecyclerView = findViewById(R.id.recyclerView)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = adapter
        recyclerview.setHasFixedSize(true)

        val spinner: Spinner = findViewById(R.id.spinner2)
        val arrayCities = resources.getStringArray(R.array.cities)
        val spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, arrayCities
        )

        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (parent.selectedItem.toString() == "Choose the city") {
                    model.setCity(cityStart,this@MainActivity)
                    currentCity = cityStart
                } else {
                    model.setCity(parent.selectedItem.toString(), this@MainActivity)
                    currentCity = parent.selectedItem.toString()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        model.dataWeather.observe(this) {
            val data: Array<DataWeatherEntity> = it
            adapter.setData(data)
            city.text = data[0].city
            temperature.text = "${data[0].temperature.toInt()} C"
            pressure.text = "Pressure ${data[0].pressure} pH"
            humidity.text = "Humidity ${data[0].humidity}  %"
            feels.text = "Feels like ${data[0].feelLike.toInt()} C"
            windy.text = "Wind speed ${data[0].wind} m/s"

            val icon = data[0].image
            Picasso.get().load("https://openweathermap.org/img/wn/$icon.png")
                .resize(300, 300)
                .into(image)
        }
    }

    override fun onStop() {
        super.onStop()
        prefs.edit().putString("tag", currentCity).apply()
    }

    override fun onStart() {
        super.onStart()
        cityStart = if (prefs.contains("tag")) {
            prefs.getString("tag", "").toString()
        } else {
            "Gomel"
        }
    }
}


fun isInternetAvailable(context: Context): Boolean {
    var result = false
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        connectivityManager.run {
            connectivityManager.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }

            }
        }
    }

    return result
}



