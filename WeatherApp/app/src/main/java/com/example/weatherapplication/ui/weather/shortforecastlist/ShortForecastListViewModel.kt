package com.example.weatherapplication.ui.weather.shortforecastlist

import android.util.Log
import androidx.lifecycle.*
import com.example.weatherapplication.data.db.appsp.AppSharedPreferences
import com.example.weatherapplication.data.db.appsp.AppSharedPreferencesContract
import com.example.weatherapplication.data.models.city.City
import com.example.weatherapplication.data.models.forecast.Forecast
import com.example.weatherapplication.data.repositories.DatabaseForecastRepository
import com.example.weatherapplication.data.repositories.RemoteForecastRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException

class ShortForecastListViewModel : ViewModel() {
    private var timeLastRequest: Long = 0L
    private val forecastPrefs = AppSharedPreferences.instancePrefs
    private val remoteRepo = RemoteForecastRepository()
    private val databaseRepo = DatabaseForecastRepository()

    init {
        Timber.d("init")

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
        Timber.d("updateForecastList")

        resetTimeLastRequestForecast()
        getForecastList(listCityId)
    }

    fun getForecastList(listCityId: List<City>) {
        Timber.d("getForecastList")

        viewModelScope.launch(Dispatchers.IO) {
            isLoadingMutableLiveData.postValue(true)
            forecastListMutableLiveData.postValue(

                if (isInternetRequest()) {
                    Timber.d("request internet")

                    clearCurrentForecastList()
                    saveTimeRequestForecast()
                    try {
                        getForecastListInInternet(listCityId).also {
                            Timber.d("response list forecast from internet $it")
                        }
                    } catch (e: IOException) {
                        getForecastListInDatabase(listCityId).also {
                            Timber.d("response list forecast from database $it, error ${e.message}")
                        }
                    } finally {
                        isLoadingMutableLiveData.postValue(false)
                    }
                } else {
                    clearCurrentForecastList()
                    getForecastListInDatabase(listCityId).also {
                        Timber.d(" list forecast from database $it")
                        isLoadingMutableLiveData.postValue(false)
                    }
                }
            )
        }
    }

    private suspend fun getForecastListInDatabase(listCityId: List<City>): List<Forecast> {
        Timber.d(" request database")

        return listCityId.map {
            try {
                databaseRepo.getForecastFromDatabase(it.id)!!.also { forecast ->
                    Timber.d(" list forecast from database $forecast")
                }
            } catch (t: Throwable) {
                resetTimeLastRequestForecast()
                Timber.d(" throwable request database $t")
                errorMessage("Не данных из интернета и из базы данных, проверьте подключение к интернету")
                return emptyList()
            }
        }
    }

    private suspend fun getForecastListInInternet(listCity: List<City>): List<Forecast> {
        Timber.d("getForecastListInInternet")

        return listCity.map {
            remoteRepo.requestForecast(it.id)
        }
    }

    private fun resetTimeLastRequestForecast() {
        Timber.d("resetTimeLastRequestForecast")

        timeLastRequest = 0L
    }

    private fun clearCurrentForecastList() {
        Timber.d("clearCurrentForecastList")

        forecastListMutableLiveData.postValue(emptyList())
    }

    private fun saveTimeRequestForecast() {
        Timber.d("saveTimeRequestForecast")

        timeLastRequest = System.currentTimeMillis()
        forecastPrefs.edit()
            .putLong(AppSharedPreferencesContract.TIME_LAST_REQUEST_KEY, timeLastRequest)
            .apply()
    }

    // Интернет-запрос не чаще одного раза в десять минут
    private fun isInternetRequest(): Boolean {
        Timber.d("isInternetRequest")

        return System.currentTimeMillis() - timeLastRequest >= 600000
    }

    fun errorMessage(message: String) {
        Timber.d("isInternetRequest $message")

        errorMessageMutableLiveData.postValue(message)
    }

    override fun onCleared() {
        Timber.d("onCleared")
        super.onCleared()
    }
}
