package com.example.bondcalculator.data.repositories_impl

import com.example.bondcalculator.data.memory.SharedPreferenceDataSource
import com.example.bondcalculator.domain.models.portfplio.DomainPortfolioYield
import com.example.bondcalculator.domain.repositories_intf.ChartsFrgRepository
import io.reactivex.Observable
import javax.inject.Inject

class ChartsFrgRepositoryImpl @Inject constructor(
    private val sharedPreferenceDataSource: SharedPreferenceDataSource
) : ChartsFrgRepository {

    override fun getCalculatePortfolioYield(): Observable<DomainPortfolioYield> {
        return sharedPreferenceDataSource.getCalculatePortfolioYield()
    }
}