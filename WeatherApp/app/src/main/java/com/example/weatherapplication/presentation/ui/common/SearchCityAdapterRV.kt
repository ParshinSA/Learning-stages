package com.example.weatherapplication.presentation.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.R
import com.example.weatherapplication.databinding.ItemResultSearchBinding
import com.example.weatherapplication.domain.models.city.response.DomainCityDto

class SearchCityAdapterRV(
    private val clickAdd: (city: DomainCityDto) -> Unit
) :
    ListAdapter<DomainCityDto, SearchCityAdapterRV.CityHolder>(DiffUtilItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_result_search, parent, false)
        return CityHolder(view, clickAdd)
    }

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class DiffUtilItemCallback : DiffUtil.ItemCallback<DomainCityDto>() {
        override fun areItemsTheSame(oldItem: DomainCityDto, newItem: DomainCityDto): Boolean {
            return oldItem.latitude == newItem.latitude && oldItem.longitude == newItem.longitude
        }

        override fun areContentsTheSame(oldItem: DomainCityDto, newItem: DomainCityDto): Boolean {
            return oldItem == newItem
        }
    }

    class CityHolder(
        view: View,
        private val clickAdd: (city: DomainCityDto) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        private val bind = ItemResultSearchBinding.bind(view)

        private lateinit var currentCity: DomainCityDto

        fun bind(itemCity: DomainCityDto) {
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
