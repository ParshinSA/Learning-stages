package com.example.bondcalculator.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bondcalculator.R
import com.example.bondcalculator.domain.interactors.TextInfoDepositFrgInteractor
import com.example.bondcalculator.domain.models.analysis_portfolio_yield.DomainDataForTextInfoDepositFrg
import com.example.bondcalculator.presentation.common.ResourcesProvider
import com.example.bondcalculator.presentation.models.TextInfoDepositData
import javax.inject.Inject

class TextInfoDepositViewModel @Inject constructor(
    private val interactor: TextInfoDepositFrgInteractor,
    private val resourcesProvider: ResourcesProvider,
) : BaseViewModel() {

    private val dataTextInfoDepositMutLiveData = MutableLiveData<TextInfoDepositData>()
    val dataTextInfoDepositLiveData: LiveData<TextInfoDepositData> get() = dataTextInfoDepositMutLiveData

    fun getData() {
        compositeDisposable.addAll(
            interactor.getDataForTextInfoDepositFrg().subscribe({ data ->
                val convertData = convertToTextInfoDepositData(data)
                dataTextInfoDepositMutLiveData.postValue(convertData)
            }, {
                Log.d(TAG, "getData: $it")
                errorMessage(resourcesProvider.getString(R.string.dialog_error_get_data_from_shared_prefs))
            })
        )
    }

    private fun convertToTextInfoDepositData(data: DomainDataForTextInfoDepositFrg): TextInfoDepositData {
        val profit = data.resultBalance - data.startBalance
        val resultYield = 100 / data.startBalance * profit / data.term

        return TextInfoDepositData(
            startBalance = data.startBalance,
            endBalance = data.resultBalance,
            resultYield = resultYield,
            profit = profit,
            term = data.term
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    companion object {
        private const val TAG = "TextInfoDepositViewModel"
    }
}