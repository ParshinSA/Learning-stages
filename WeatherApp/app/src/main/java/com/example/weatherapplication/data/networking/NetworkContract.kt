package com.example.weatherapplication.data.networking

object NetworkContract {
    const val API_KEY = "1357e31f17dff00c5e7ba287807cf428"
    const val UNITS = "metric"
    const val LANG = "ru"
    const val LIMIT_VARIANTS = 3

    object Url {
        const val FORECAST_API = "https://api.openweathermap.org/"
        const val STATISTICAL_API = "https://history.openweathermap.org/"
        const val SEARCH_CITY_API = "https://api.openweathermap.org/"
    }
}