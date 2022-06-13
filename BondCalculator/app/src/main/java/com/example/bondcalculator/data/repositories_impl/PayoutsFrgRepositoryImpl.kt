package com.example.bondcalculator.data.repositories_impl

import com.example.bondcalculator.data.data_sourse.memory.SharedPreferenceDataSource
import com.example.bondcalculator.domain.models.analysis_portfolio_yield.DomainDataForPayoutsFrg
import com.example.bondcalculator.domain.repositories_intf.PayoutsFrgRepository
import io.reactivex.Single
import javax.inject.Inject

class PayoutsFrgRepositoryImpl @Inject constructor(
    private val sharedPreferenceDataSource: SharedPreferenceDataSource
) : PayoutsFrgRepository {

    override fun getDataForPayoutsFrg(): Single<DomainDataForPayoutsFrg> {
        return sharedPreferenceDataSource.getDataForPayoutsFrg()
    }
}