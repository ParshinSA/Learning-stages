package com.example.bondcalculator.data.repositories_impl

import com.example.bondcalculator.data.data_sourse.memory.SharedPreferenceDataSource
import com.example.bondcalculator.domain.models.analysis_portfolio_yield.DomainDataForTextInfoDepositFrg
import com.example.bondcalculator.domain.repositories_intf.TextInfoDepositFrgRepository
import io.reactivex.Single
import javax.inject.Inject

class TextInfoDepositFrgRepositoryImpl @Inject constructor(
    private val sharedPreferenceDataSource: SharedPreferenceDataSource
) : TextInfoDepositFrgRepository {

    override fun getDataForTextInfoDepositFrg(): Single<DomainDataForTextInfoDepositFrg> {
        return sharedPreferenceDataSource.getDataForTextInfoDepositFrg()
    }
}