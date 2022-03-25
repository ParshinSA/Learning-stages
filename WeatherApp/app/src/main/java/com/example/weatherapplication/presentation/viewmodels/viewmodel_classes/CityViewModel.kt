package com.example.weatherapplication.presentation.viewmodels.viewmodel_classes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapplication.domain.interactors.interactors_interface.CityInteractor
import com.example.weatherapplication.domain.models.city.request.DomainRequestSearchByCityNameDto
import com.example.weatherapplication.domain.models.city.response.DomainCityDto
import com.example.weatherapplication.presentation.viewmodels.BaseViewModel
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class CityViewModel(
    private val interactor: CityInteractor
) : BaseViewModel() {

    private val resultSearchListMutableLiveData = MutableLiveData<List<DomainCityDto>>(emptyList())
    val resultCityLiveData: LiveData<List<DomainCityDto>>
        get() = resultSearchListMutableLiveData

    fun searchCity(userInput: Observable<String>) {
        compositeDisposable.add(
            userInput
                .filter { it.length >= 3 }
                .debounce(350, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .subscribe { filteredUserInput ->
                    compositeDisposable.add(
                        interactor.searchByCityName(
                            DomainRequestSearchByCityNameDto(
                                filteredUserInput
                            )
                        )
                            .subscribe({ listCity ->
                                Log.d(TAG, "searchCity: complete $listCity")
                                resultSearchListMutableLiveData.postValue(listCity)
                            }, {
                                Log.d(TAG, "searchCity: error $it")
                            })
                    )
                }
        )
    }

    fun addCity(domainCityDto: DomainCityDto) {
        compositeDisposable.add(
            interactor.addCityInDatabase(domainCityDto = domainCityDto).subscribe({
                Log.d(TAG, "addCity: complete")
            }, {
                Log.d(TAG, "addCity: error $it")
            })
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    companion object {
        const val TAG = "SearchCityVM_Logging"
    }
}