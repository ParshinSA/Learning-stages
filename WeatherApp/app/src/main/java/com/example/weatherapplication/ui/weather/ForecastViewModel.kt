package com.example.weatherapplication.ui.weather

import android.util.Log
import androidx.lifecycle.*
import com.example.weatherapplication.app.SingleLiveEvent
import com.example.weatherapplication.data.db.appsp.AppSharedPreferences
import com.example.weatherapplication.data.db.appsp.AppSharedPreferencesContract
import com.example.weatherapplication.data.models.city.City
import com.example.weatherapplication.data.models.forecast.Forecast
import com.example.weatherapplication.data.repositories.ForecastRepository
import kotlinx.coroutines.launch

class ForecastViewModel : ViewModel() {
    private var timeLastRequest: Long = 0L
    private val forecastPrefs = AppSharedPreferences.instancePrefs
    private val forecastRepository = ForecastRepository()

    init {
        timeLastRequest =
            forecastPrefs.getLong(AppSharedPreferencesContract.TIME_LAST_REQUEST_KEY, 0L)
    }

    private val forecastListMutableLiveData = MutableLiveData(listOf<Forecast>())
    val forecastList: LiveData<List<Forecast>>
        get() = forecastListMutableLiveData

    private val errorMessageSingleLiveEvent = SingleLiveEvent<String>()
    val errorMessage: LiveData<String>
        get() = errorMessageSingleLiveEvent


    fun getWeatherForecast(listCityId: List<City>) {
        viewModelScope.launch {
            clearCurrentForecastList()
            val listForecast =
                if (checkTimeLastRequestForecast()) {
                    saveTimeRequestForecast()
                    getForecastListInInternetOrDatabase(listCityId)
                } else {
                    getForecastListInDatabase(listCityId)
                }
            forecastListMutableLiveData.postValue(listForecast)
        }
    }

    private suspend fun getForecastListInDatabase(listCityId: List<City>): List<Forecast> {
        var currentList = listOf<Forecast>()
        listCityId.forEach { city: City ->
            forecastRepository.getForecastFromDatabase(city.id)
                ?.let {
                    currentList = currentList + listOf(it)
                    Log.d("SystemLogging", "xxxxx$it")
                }
                ?: errorMessage("Not data from internet or database, check internet connection")

        }
        return currentList
    }

    private suspend fun getForecastListInInternetOrDatabase(listCity: List<City>): List<Forecast> {
        var currentList = listOf<Forecast>()
        listCity.forEach { city: City ->
            forecastRepository.getForecast(city.id)
                ?.let {
                    currentList = currentList + listOf(it)
                }
                ?: errorMessage("Not data from internet or database, check internet connection")
        }

        if (currentList.isEmpty()) resetTimeLastRequestForecast()

        return currentList
    }

    private fun resetTimeLastRequestForecast() {
        timeLastRequest = 0L
    }

    private fun clearCurrentForecastList() {
        forecastListMutableLiveData.value = emptyList()
    }

    private fun saveTimeRequestForecast() {
        timeLastRequest = System.currentTimeMillis()
        forecastPrefs.edit()
            .putLong(AppSharedPreferencesContract.TIME_LAST_REQUEST_KEY, timeLastRequest)
            .apply()
    }

    private fun checkTimeLastRequestForecast(): Boolean {
        val tenMinutesTimer = System.currentTimeMillis() - timeLastRequest
        return tenMinutesTimer >= 600000
    }

    private fun errorMessage(message: String) {
        errorMessageSingleLiveEvent
            .postValue(message)
    }
}
