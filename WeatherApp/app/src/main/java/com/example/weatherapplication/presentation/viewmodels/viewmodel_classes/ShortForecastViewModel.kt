package com.example.weatherapplication.presentation.viewmodels.viewmodel_classes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapplication.R
import com.example.weatherapplication.domain.interactors.interactors_interface.ForecastInteractor
import com.example.weatherapplication.domain.models.forecast.DomainForecastDto
import com.example.weatherapplication.domain.models.forecast.convertToUiDetailsForecastDto
import com.example.weatherapplication.domain.models.forecast.convertToUiShortForecastDto
import com.example.weatherapplication.presentation.common.SingleLiveEvent
import com.example.weatherapplication.presentation.models.forecast.details.UiDetailsForecastDto
import com.example.weatherapplication.presentation.models.forecast.short.UiShortForecastDto
import com.example.weatherapplication.presentation.ui.common.ResourcesProvider
import com.example.weatherapplication.presentation.viewmodels.BaseViewModel
import java.text.SimpleDateFormat
import java.util.*

class ShortForecastViewModel(
    private val interactor: ForecastInteractor,
    private val resourcesProvider: ResourcesProvider
) : BaseViewModel() {

    private lateinit var listDomainForecastDto: List<DomainForecastDto>

    private val forecastListMutableLiveData = MutableLiveData<List<UiShortForecastDto>>()
    val forecastListLiveData: LiveData<List<UiShortForecastDto>>
        get() {
            getForecastList()
            return forecastListMutableLiveData
        }

    private val errorMessageMutableLiveData = SingleLiveEvent<String>()
    val errorMessageLiveData: LiveData<String>
        get() = errorMessageMutableLiveData

    val isLoadingMutableLiveData = MutableLiveData<Boolean>()

    init {
        compositeDisposable.add(
            interactor.getListForecastFromDatabase()
                .subscribe({ listDomainForecast ->
                    listDomainForecastDto = listDomainForecast
                    val listShortForecastDto =
                        listDomainForecast.map { it.convertToUiShortForecastDto() }

                    Log.d(TAG, "update forecastListMutableLiveData: completed")

                    forecastListMutableLiveData.postValue(listShortForecastDto)
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

    fun getLastUpdateTimeString(): String { // TODO: 25.03.2022 return String?
        val lastTimeUpdate = interactor.getLastUpdateTime().time
        val dateFormat = Date(lastTimeUpdate)
        val sdf =
            SimpleDateFormat(
                resourcesProvider.getString(R.string.ShortForecastListFragment_time_format_ddMMMMHHmmss),
                Locale("ru")
            ).format(dateFormat)

        return sdf
    }

    fun errorMessage(message: String) {
        errorMessageMutableLiveData.postValue(message)
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared: VM cleared")
        super.onCleared()
    }

    fun getDetailForecast(coordinationCity: Pair<Double, Double>): UiDetailsForecastDto {
        return listDomainForecastDto.first { domainForecastDto ->
            domainForecastDto.latitude == coordinationCity.first &&
                    domainForecastDto.longitude == coordinationCity.second
        }.convertToUiDetailsForecastDto()
    }

    companion object {
        const val TAG = "ShortVM_Logging"
    }
}
