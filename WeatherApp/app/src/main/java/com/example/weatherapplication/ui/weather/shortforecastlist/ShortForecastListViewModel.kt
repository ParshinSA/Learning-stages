package com.example.weatherapplication.ui.weather.shortforecastlist

import android.util.Log
import androidx.lifecycle.*
import com.example.weatherapplication.data.db.appsp.AppSharedPreferences
import com.example.weatherapplication.data.db.appsp.AppSharedPreferencesContract
import com.example.weatherapplication.data.models.city.City
import com.example.weatherapplication.data.models.forecast.Forecast
import com.example.weatherapplication.data.repositories.DatabaseForecastRepository
import com.example.weatherapplication.data.repositories.RemoteForecastRepository
import kotlinx.coroutines.launch
import java.io.IOException

class ShortForecastListViewModel : ViewModel() {
    private var timeLastRequest: Long = 0L
    private val forecastPrefs = AppSharedPreferences.instancePrefs
    private val remoteRepo = RemoteForecastRepository()
    private val databaseRepo = DatabaseForecastRepository()

    init {
        timeLastRequest =
            forecastPrefs.getLong(AppSharedPreferencesContract.TIME_LAST_REQUEST_KEY, 0L)
    }

    private val forecastListMutableLiveData = MutableLiveData(listOf<Forecast>())
    val forecastListLiveData: LiveData<List<Forecast>>
        get() = forecastListMutableLiveData

    private val errorMessageMutableLiveData = MutableLiveData<String>()
    val errorMessageLiveData: LiveData<String>
        get() = errorMessageMutableLiveData

    private val isLoadingMutableLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean>
        get() = isLoadingMutableLiveData

    fun updateForecastList(listCityId: List<City>) {
        resetTimeLastRequestForecast()
        getForecastList(listCityId)
    }

    fun getForecastList(listCityId: List<City>) {
        viewModelScope.launch {
            isLoadingMutableLiveData.postValue(true)
            forecastListMutableLiveData.postValue(

                if (isInternetRequest()) {
                    Log.d("SystemLogging", "request internet")
                    clearCurrentForecastList()
                    saveTimeRequestForecast()
                    try {
                        getForecastListInInternet(listCityId).also {
                            Log.d("SystemLogging", "listForecastInternet $it")
                        }
                    } catch (e: IOException) {
                        getForecastListInDatabase(listCityId).also {
                            Log.d("SystemLogging", "listForecastDatabase $it")
                        }
                    } finally {
                        isLoadingMutableLiveData.postValue(false)
                    }
                } else {
                    clearCurrentForecastList()
                    getForecastListInDatabase(listCityId).also {
                        Log.d("SystemLogging", "listForecastDatabase $it")
                        isLoadingMutableLiveData.postValue(false)
                    }
                }
            )
        }
    }

    private suspend fun getForecastListInDatabase(listCityId: List<City>): List<Forecast> {
        Log.d("SystemLogging", "request database")
        return listCityId.map {
            try {
                Log.d("SystemLogging", "$it")
                databaseRepo.getForecastFromDatabase(it.id)!!
            } catch (t: Throwable) {
                resetTimeLastRequestForecast()
                Log.d("SystemLogging", "throwable $t")
                errorMessage("Не данных из интернета и из базы данных, проверьте подключение к интернету")
                return emptyList()
            }
        }
    }

    private suspend fun getForecastListInInternet(listCity: List<City>): List<Forecast> {
        return listCity.map {
            remoteRepo.requestForecast(it.id)
        }
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

    // Интернет-запрос не чаще одного раза в десять минут
    private fun isInternetRequest(): Boolean {
        return System.currentTimeMillis() - timeLastRequest >= 600000
    }

    fun errorMessage(message: String) {
        errorMessageMutableLiveData.postValue(message)
    }
}
