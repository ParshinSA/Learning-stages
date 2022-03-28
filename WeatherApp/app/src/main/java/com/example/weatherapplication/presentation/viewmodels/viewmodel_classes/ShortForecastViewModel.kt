package com.example.weatherapplication.presentation.viewmodels.viewmodel_classes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapplication.R
import com.example.weatherapplication.domain.interactors.interactors_interface.ForecastInteractor
import com.example.weatherapplication.domain.models.forecast.DomainForecast
import com.example.weatherapplication.presentation.common.SingleLiveEvent
import com.example.weatherapplication.presentation.models.forecast.details_forecast.UiDetailsForecast
import com.example.weatherapplication.presentation.models.forecast.details_forecast.convertToUiDetailsForecast
import com.example.weatherapplication.presentation.models.forecast.short_forecast.UiShortForecast
import com.example.weatherapplication.presentation.models.forecast.short_forecast.convertToUiShortForecast
import com.example.weatherapplication.presentation.ui.common.ResourcesProvider
import com.example.weatherapplication.presentation.viewmodels.BaseViewModel
import java.text.SimpleDateFormat
import java.util.*

class ShortForecastViewModel(
    private val interactor: ForecastInteractor,
    private val resourcesProvider: ResourcesProvider
) : BaseViewModel() {

    private lateinit var listDomainForecast: List<DomainForecast>

    private val forecastListMutableLiveData = MutableLiveData<List<UiShortForecast>>()
    val forecastListLiveData: LiveData<List<UiShortForecast>>
        get() = forecastListMutableLiveData

    private val errorMessageMutableLiveData = SingleLiveEvent<String>()
    val errorMessageLiveData: LiveData<String>
        get() = errorMessageMutableLiveData

    val isLoadingMutableLiveData = MutableLiveData<Boolean>()

    init {
        compositeDisposable.add(
            interactor.getListForecastFromDatabase()
                .subscribe({ listDomainForecast ->
                    Log.d(TAG, "listDomain $listDomainForecast: ")
                    if (listDomainForecast.isEmpty()) {
                        forecastListMutableLiveData.postValue(emptyList())
                    } else {
                        this.listDomainForecast = listDomainForecast
                        val listShortForecast =
                            listDomainForecast.map { it.convertToUiShortForecast() }

                        Log.d(
                            TAG,
                            "update forecastListMutableLiveData: completed $listShortForecast"
                        )

                        forecastListMutableLiveData.postValue(listShortForecast)
                    }

                },
                    {
                        Log.d(TAG, "update forecastListMutableLiveData: error $it")
                    }
                )
        )
    }


    fun getForecastList() {
        Log.d(TAG, "getForecastList: ")
        isLoadingMutableLiveData.postValue(true)
        interactor.updateForecast()
        isLoadingMutableLiveData.postValue(false)
    }

    fun getLastUpdateTimeString(): String {
        val lastTimeUpdate = interactor.getLastUpdateTime().time
        val dateFormat = Date(lastTimeUpdate)
        return SimpleDateFormat(
            resourcesProvider.getString(R.string.ShortForecastListFragment_time_format_ddMMMMHHmmss),
            Locale("ru")
        ).format(dateFormat)
    }

    fun errorMessage(message: String) {
        errorMessageMutableLiveData.postValue(message)
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared: VM cleared")
        super.onCleared()
    }

    fun getDetailForecast(coordinationCity: Pair<Double, Double>): UiDetailsForecast {
        return listDomainForecast.first { domainForecastDto ->
            domainForecastDto.latitude == coordinationCity.first &&
                    domainForecastDto.longitude == coordinationCity.second
        }.convertToUiDetailsForecast()
    }

    companion object {
        const val TAG = "ShortVM_Logging"
    }
}
