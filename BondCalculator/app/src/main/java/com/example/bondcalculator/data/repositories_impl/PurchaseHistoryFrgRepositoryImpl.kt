package com.example.bondcalculator.data.repositories_impl

import com.example.bondcalculator.data.memory.SharedPreferenceDataSource
import com.example.bondcalculator.domain.models.analysis_portfolio_yield.DomainDataForPurchaseHistoryFrg
import com.example.bondcalculator.domain.repositories_intf.PurchaseHistoryFrgRepository
import io.reactivex.Single
import javax.inject.Inject

class PurchaseHistoryFrgRepositoryImpl @Inject constructor(
    private val sharedPreferenceDataSource: SharedPreferenceDataSource
) : PurchaseHistoryFrgRepository {

    override fun getDataForPurchaseHistoryFrg(): Single<DomainDataForPurchaseHistoryFrg> {
        return sharedPreferenceDataSource.getDataForPurchaseHistoryFrg()
    }
}