package com.example.weatherapplication.ui.weather.searchcity.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.R
import com.example.weatherapplication.data.models.city.City

class ResultSearchAdapter(
    private val clickAdd: (city: City) -> Unit
) :
    ListAdapter<City, ResultSearchAdapter.CityHolder>(DiffUtilItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_result_search, parent, false)
        return CityHolder(view, clickAdd)
    }

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class DiffUtilItemCallback : DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem.lat == newItem.lat && oldItem.lon == newItem.lon
        }

        override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem == newItem
        }
    }

    class CityHolder(
        view: View,
        private val clickAdd: (city: City) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        private val title: Button = view.findViewById(R.id.item_result_search)
        private val subTitle: TextView = view.findViewById(R.id.state_city)
        private lateinit var currentCity: City

        fun bind(itemCity: City) {
            currentCity = itemCity
            title.text = "${itemCity.name}, ${itemCity.country}"
            subTitle.text = itemCity.state
        }

        init {
            title.setOnClickListener {
                clickAdd(currentCity)
            }
        }
    }
}
