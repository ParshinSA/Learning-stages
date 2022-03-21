package com.example.weatherapplication.presentation.viewmodels.viewmodel_classes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapplication.data.database.models.city.City
import com.example.weatherapplication.domain.interactors.interactors_interface.CustomCitiesInteractor
import com.example.weatherapplication.presentation.viewmodels.BaseViewModel
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class CustomCityViewModel(
    private val interactor: CustomCitiesInteractor
) : BaseViewModel() {

    private val resultSearchListMutableLiveData = MutableLiveData<List<City>>(emptyList())
    val resultCityLiveData: LiveData<List<City>>
        get() = resultSearchListMutableLiveData

    fun searchCity(userInput: Observable<String>) {
        compositeDisposable.add(
            userInput
                .filter { it.length >= 3 }
                .debounce(350, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .subscribe { filteredUserInput ->
                    compositeDisposable.add(
                        interactor.searchForCustomCityByName(filteredUserInput)
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

    fun addCity(city: City) {
        compositeDisposable.add(
            interactor.addCityInDatabase(city = city).subscribe({
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