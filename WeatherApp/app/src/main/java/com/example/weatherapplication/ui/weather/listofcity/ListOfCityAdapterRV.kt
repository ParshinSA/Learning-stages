package com.example.weatherapplication.ui.weather.listofcity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapplication.R
import com.example.weatherapplication.data.models.forecast.Forecast
import com.example.weatherapplication.databinding.ItemInfoWeatherCityBinding
import kotlin.math.round

class ListOfCityAdapterRV(
    private val onItemClick: (clickOnPosition: Int) -> Unit
) :
    ListAdapter<Forecast, ListOfCityAdapterRV.WeatherForecastHolder>(DiffUtilItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherForecastHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_info_weather_city, parent, false)
        return WeatherForecastHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: WeatherForecastHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class DiffUtilItemCallback : DiffUtil.ItemCallback<Forecast>() {
        override fun areItemsTheSame(oldItem: Forecast, newItem: Forecast): Boolean {
            return oldItem.cityId == newItem.cityId
        }

        override fun areContentsTheSame(
            oldItem: Forecast,
            newItem: Forecast
        ): Boolean {
            return oldItem == newItem
        }
    }

    class WeatherForecastHolder(
        view: View,
        onItemClick: (idCity: Int) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        init {
            view.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }

        private val viewBind = ItemInfoWeatherCityBinding.bind(view)

        fun bind(item: Forecast) {
            with(viewBind) {
                nameCityTV.text = item.cityName
                temperatureCityTV.text = round(item.main.temp).toInt().toString()

                Glide.with(itemView)
                    .load("https://openweathermap.org/img/wn/${item.weather[0].sectionURL.toString()}@2x.png")
                    .placeholder(R.drawable.ic_cloud)
                    .error(R.drawable.ic_no_internet)
                    .into(iconWeatherIV)
            }
        }
    }
}