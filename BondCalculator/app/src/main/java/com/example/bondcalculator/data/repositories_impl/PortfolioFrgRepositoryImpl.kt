package com.example.bondcalculator.data.repositories_impl

import com.example.bondcalculator.data.data_sourse.memory.SharedPreferenceDataSource
import com.example.bondcalculator.domain.models.analysis_portfolio_yield.DomainDataForPortfolioFrg
import com.example.bondcalculator.domain.repositories_intf.PortfolioFrgRepository
import io.reactivex.Single
import javax.inject.Inject

class PortfolioFrgRepositoryImpl @Inject constructor(
    private val sharedPreferenceDataSource: SharedPreferenceDataSource
) : PortfolioFrgRepository {

    override fun getDataForPortfolioFrg(): Single<DomainDataForPortfolioFrg> {
        return sharedPreferenceDataSource.getDataForPortfolioFrg()
    }

}