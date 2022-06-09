package com.example.bondcalculator.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bondcalculator.R
import com.example.bondcalculator.domain.interactors.CompositionFrgInteractor
import com.example.bondcalculator.domain.models.analysis_portfolio_yield.DomainDataForCompositionFrg
import com.example.bondcalculator.presentation.common.ResourcesProvider
import com.example.bondcalculator.presentation.models.CompositionFrgItemRv
import javax.inject.Inject
import kotlin.math.roundToInt

class CompositionViewModel @Inject constructor(
    private val interactor: CompositionFrgInteractor,
    private val resourcesProvider: ResourcesProvider
) : BaseViewModel() {

    private val dataTextInfoDepositMutLiveData = MutableLiveData<List<CompositionFrgItemRv>>()
    val dataCompositionFrgLiveData: LiveData<List<CompositionFrgItemRv>> get() = dataTextInfoDepositMutLiveData

    fun getData() {
        compositeDisposable.add(
            interactor.getDataForCompositionFrg()
                .subscribe({ data ->
                    val convertData = convertToCompositionListItemRv(data)
                    dataTextInfoDepositMutLiveData.postValue(convertData)
                }, {
                    Log.d(TAG, "getData: $it")
                    errorMessage(resourcesProvider.getString(R.string.dialog_error_get_data_from_shared_prefs))
                })
        )
    }

    private fun convertToCompositionListItemRv(
        data: DomainDataForCompositionFrg
    ): List<CompositionFrgItemRv> {
        val listTextInfoDepositItem = mutableListOf<CompositionFrgItemRv>()
        val sumCounterBonds = data.counterBonds.values.sum()

        try {
            for (itemName in data.listShortName) {
                val percentPortfolio =
                    100 / sumCounterBonds.toDouble() * (data.counterBonds[itemName] ?: 0)

                listTextInfoDepositItem.add(
                    CompositionFrgItemRv(
                        name = itemName,
                        nominal = data.nominal[itemName]!!,
                        percentPrice = data.percentPrice[itemName]!!,
                        amount = data.counterBonds[itemName]!!,
                        percentPortfolio = percentPortfolio.roundToInt()
                    )
                )
            }
        } catch (t: Throwable) {
            Log.d(TAG, "convertToTextInfoDepositListItem: ERROR $t")
        }
        return listTextInfoDepositItem
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    companion object {
        private const val TAG = "CompositionViewModel"
    }
}