package com.example.weatherapplication.presentation.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapplication.R
import com.example.weatherapplication.databinding.ItemShortForecastBinding
import com.example.weatherapplication.presentation.models.forecast.UiShortForecast
import kotlin.math.roundToInt

class ShortForecastListAdapter(
    private val onItemClick: (coordinationCity: Pair<Double, Double>, currentView: View) -> Unit
) : ListAdapter<UiShortForecast, ShortForecastListAdapter.ShortForecastHolder>(
    DiffUtilItemCallback()
) {

    class DiffUtilItemCallback : DiffUtil.ItemCallback<UiShortForecast>() {
        override fun areItemsTheSame(oldItem: UiShortForecast, newItem: UiShortForecast): Boolean {
            return oldItem.latitude == newItem.latitude
        }

        override fun areContentsTheSame(
            oldItem: UiShortForecast,
            newItem: UiShortForecast
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortForecastHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_short_forecast, parent, false)
        return ShortForecastHolder(view, onItemClick)
    }


    override fun onBindViewHolder(
        holder: ShortForecastHolder,
        position: Int
    ) {
        holder.itemView.transitionName = holder.itemView.context.resources.getString(
            R.string.ShortForecastFragment_transition_name_item,
            position
        )
        val item = getItem(position)
        holder.bind(item)
    }

    class ShortForecastHolder(
        view: View,
        onItemClick: (coordinationCity: Pair<Double, Double>, currentView: View) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        private val mContext = view.context
        private lateinit var currentCoordinationCity: Pair<Double, Double>

        init {
            view.setOnClickListener {
                onItemClick(currentCoordinationCity, view)
            }
        }

        private val viewBind = ItemShortForecastBinding.bind(view)

        fun bind(item: UiShortForecast) {
            currentCoordinationCity = Pair(item.latitude, item.longitude)

            with(item) {
                viewBind.tvCityName.text = cityName
                viewBind.tvTemperature.text = temperature.roundToInt().toString()

                Glide.with(itemView)
                    .load(
                        mContext.getString(
                            R.string.URL_loading_image_weather,
                            iconId
                        )
                    )
                    .placeholder(R.drawable.ic_cloud)
                    .error(R.drawable.ic_no_internet)
                    .into(viewBind.imvIcWeather)
            }
        }
    }

}