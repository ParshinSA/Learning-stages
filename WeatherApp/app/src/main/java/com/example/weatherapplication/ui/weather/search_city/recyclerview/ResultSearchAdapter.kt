package com.example.weatherapplication.ui.weather.search_city.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.R
import com.example.weatherapplication.data.models.city.City
import com.example.weatherapplication.databinding.ItemResultSearchBinding

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

        private val bind = ItemResultSearchBinding.bind(view)

        private lateinit var currentCity: City

        fun bind(itemCity: City) {
            currentCity = itemCity
            bind.btnItemResult.text = "${itemCity.name}, ${itemCity.country}"
            bind.tvStateCity.text = itemCity.state
        }

        init {
            bind.btnItemResult.setOnClickListener {
                clickAdd(currentCity)
            }
        }
    }
}
