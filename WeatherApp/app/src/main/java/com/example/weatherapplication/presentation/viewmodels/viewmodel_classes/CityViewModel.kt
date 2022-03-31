package com.example.weatherapplication.presentation.viewmodels.viewmodel_classes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapplication.domain.interactors.interactors_interface.CityInteractor
import com.example.weatherapplication.domain.models.city.DomainCity
import com.example.weatherapplication.domain.models.city.DomainRequestSearchByCityName
import com.example.weatherapplication.presentation.ui.common.ResourcesProvider
import com.example.weatherapplication.presentation.viewmodels.BaseViewModel
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CityViewModel(
    private val interactor: CityInteractor
) : BaseViewModel() {

    private val resultSearchListMutableLiveData = MutableLiveData<List<DomainCity>>(emptyList())
    val resultCityLiveData: LiveData<List<DomainCity>>
        get() = resultSearchListMutableLiveData

    fun searchCity(userInput: Observable<String>) {
        compositeDisposable.add(
            filterUserInput(userInput)
                .subscribe({ filteredUserInput ->
                    search(filteredUserInput)
                },
                    {
                        Log.d(TAG, "filterUserInput: error  $it")
                    })
        )
    }

    private fun search(filteredUserInput: String) {
        compositeDisposable.add(
            interactor.searchByCityName(
                DomainRequestSearchByCityName(filteredUserInput)
            )
                .subscribe({ listCity ->
                    Log.d(TAG, "searchCity: complete $listCity")
                    resultSearchListMutableLiveData.postValue(listCity)
                }, {
                    Log.d(TAG, "searchCity: error $it")
                })
        )
    }

    private fun filterUserInput(userInput: Observable<String>): Observable<String> {
        return userInput
            .filter { it.length >= 3 }
            .debounce(350, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
    }

    fun addCity(domainCity: DomainCity) {
        compositeDisposable.add(
            interactor.addCityInDatabase(domainCity = domainCity)
                .subscribeOn(Schedulers.io())
                .subscribe({
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
        const val TAG = "CityVM_Logging"
    }
}