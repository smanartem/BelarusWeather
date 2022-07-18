package com.example.belarusweather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.belarusweather.entity.DataWeatherEntity
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat

class RecyclerViewAdapter() : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    var weather: Array<DataWeatherEntity> = arrayOf()

    fun setData(data: Array<DataWeatherEntity>) {
        this.weather = arrayOf(data[1], data[2], data[3])
        notifyDataSetChanged()
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val temp: TextView = view.findViewById(R.id.temp2)
        val date: TextView = view.findViewById(R.id.date2)
        val imgWeather: ImageView = view.findViewById(R.id.currentWeatherimg2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val formatter = SimpleDateFormat("dd-MM-yyyy")
        holder.date.text = formatter.format(weather[position].dt*1000)
        holder.temp.text = "t ${weather[position].temperature.toInt()} C"
        val icon = weather[position].image
        Picasso.get().load("https://openweathermap.org/img/wn/$icon.png")
            .resize(300, 300)
            .into(holder.imgWeather)
    }

    override fun getItemCount(): Int {
        return weather.size
    }

}