package com.example.bondcalculator.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bondcalculator.domain.interactors.intf.SecuritiesDataInteractor
import com.example.bondcalculator.domain.models.DomainSecuritiesData
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SelectionViewModel @Inject constructor(
    private val interactor: SecuritiesDataInteractor
) : BaseViewModel() {

    private val securitiesListMutLiveData = MutableLiveData<List<DomainSecuritiesData>>(emptyList())
    val securitiesListLiveData: LiveData<List<DomainSecuritiesData>>
        get() = securitiesListMutLiveData

    fun getData() {
        securitiesListMutLiveData.value = emptyList() // очищаем текущие данные

        compositeDisposable.add(
            interactor.requestSecuritiesData()  // запрашиваеи новые данные
                .subscribeOn(Schedulers.newThread())
                .subscribe({
                    Log.d(TAG, "getData:$it ")
                }, {
                    Log.d(TAG, "getData: ERROR $it")
                })
        )
    }

    companion object {
        private val TAG = this::class.qualifiedName
    }
}