package com.example.bondcalculator.presentation.ui.charts.nested.viewmodels

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.bondcalculator.R
import com.example.bondcalculator.domain.interactors.PortfolioFrgInteractor
import com.example.bondcalculator.domain.models.analysis_portfolio_yield.DomainDataForPortfolioFrg
import com.example.bondcalculator.presentation.common.ResourcesProvider
import com.example.bondcalculator.presentation.common.SingleLiveEvent
import com.example.bondcalculator.presentation.viewmodels.BaseViewModel
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import kotlin.random.Random

class PortfolioViewModel @Inject constructor(
    private val interactor: PortfolioFrgInteractor,
    private val provider: ResourcesProvider
) : BaseViewModel() {

    private val listColor = mutableListOf<Int>()
    private val previousData: DomainDataForPortfolioFrg? = null

    private val dataMutLiveData = SingleLiveEvent<DomainDataForPortfolioFrg>()
    val dataLiveData: LiveData<DomainDataForPortfolioFrg> get() = dataMutLiveData

    private val errorMessageSingleLiveEvent = SingleLiveEvent<String>()
    val errorMessageLiveData: LiveData<String> get() = errorMessageSingleLiveEvent

    fun getData() {
        compositeDisposable.add(
            interactor.getDataForPortfolioFrg()
                .subscribeOn(Schedulers.newThread())
                .subscribe({ data ->
                    dataMutLiveData.postValue(data)
                    Log.d(TAG, "start: result $data")
                }, {
                    Log.d(TAG, "start: ERROR $it")
                    errorMessageSingleLiveEvent.postValue(
                        provider.getString(R.string.dialog_error_get_data_from_shared_prefs)
                    )
                })
        )
    }

    private fun generateRandomColor(): Int {
        return Color.argb(
            255,
            Random.nextInt(256),
            Random.nextInt(256),
            Random.nextInt(256)
        )
    }

    fun getColor(): Int {
        var newColor: Int

        do {
            newColor = generateRandomColor()
        } while (listColor.contains(newColor))

        return newColor
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared: ")
        super.onCleared()
    }

    companion object {
        private const val TAG = "PortfolioViewModel"
    }
}