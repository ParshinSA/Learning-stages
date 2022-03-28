package com.example.weatherapplication.presentation.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.R
import com.example.weatherapplication.databinding.ItemResultSearchBinding
import com.example.weatherapplication.domain.models.city.DomainCity

class SearchCityAdapterRV(
    private val clickAdd: (city: DomainCity) -> Unit
) :
    ListAdapter<DomainCity, SearchCityAdapterRV.CityHolder>(DiffUtilItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_result_search, parent, false)
        return CityHolder(view, clickAdd)
    }

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class DiffUtilItemCallback : DiffUtil.ItemCallback<DomainCity>() {
        override fun areItemsTheSame(oldItem: DomainCity, newItem: DomainCity): Boolean {
            return oldItem.latitude == newItem.latitude && oldItem.longitude == newItem.longitude
        }

        override fun areContentsTheSame(oldItem: DomainCity, newItem: DomainCity): Boolean {
            return oldItem == newItem
        }
    }

    class CityHolder(
        view: View,
        private val clickAdd: (city: DomainCity) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        private val bind = ItemResultSearchBinding.bind(view)

        private lateinit var currentCity: DomainCity

        fun bind(itemCity: DomainCity) {
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
