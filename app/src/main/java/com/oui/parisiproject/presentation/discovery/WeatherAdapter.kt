package com.oui.parisiproject.presentation.discovery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oui.parisiproject.R
import com.oui.parisiproject.domain.model.discoveryModels.Weather


class WeatherAdapter(list: List<Weather>) :
    RecyclerView.Adapter<WeatherAdapter.MyViewHolder>() {
    var list: List<Weather> = list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.weather_hourly_details_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val weatherPojo: Weather = list[position]
        holder.hour.text = "" + weatherPojo.hour
        holder.temp.text = "" + weatherPojo.temperature.toString() + "°C"
        val icon: String = weatherPojo.icon
        if (icon == "02n") {
            holder.icon.setImageResource(R.drawable.ic_two)
        } else if (icon == "03n") {
            holder.icon.setImageResource(R.drawable.ic_eight)
        } else if (icon == "02n") {
            holder.icon.setImageResource(R.drawable.ic_eleven)
        } else if (icon == "01dn") {
            holder.icon.setImageResource(R.drawable.ic_five)
        } else if (icon == "02n") {
            holder.icon.setImageResource(R.drawable.ic_nine)
        } else if (icon == "02n") {
            holder.icon.setImageResource(R.drawable.ic_one)
        } else if (icon == "02d") {
            holder.icon.setImageResource(R.drawable.ic_six)
        } else if (icon == "02d") {
            holder.icon.setImageResource(R.drawable.ic_ten)
        } else if (icon == "01d") {
            holder.icon.setImageResource(R.drawable.ic_three)
        } else if (icon == "01d") {
            holder.icon.setImageResource(R.drawable.ic_four)
        } else if (icon == "01d") {
            holder.icon.setImageResource(R.drawable.ic_twelve)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var icon: ImageView = itemView.findViewById(R.id.icon)
        var hour: TextView = itemView.findViewById(R.id.hour)
        var temp: TextView = itemView.findViewById(R.id.temp)

    }

}
