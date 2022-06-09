package com.example.bondcalculator.data.repositories_impl

import com.example.bondcalculator.data.data_sourse.memory.SharedPreferenceDataSource
import com.example.bondcalculator.domain.models.analysis_portfolio_yield.DomainDataForCompositionFrg
import com.example.bondcalculator.domain.repositories_intf.CompositionFrgRepository
import io.reactivex.Single
import javax.inject.Inject

class CompositionFrgRepositoryImpl @Inject constructor(
    private val sharedPreferenceDataSource: SharedPreferenceDataSource
) : CompositionFrgRepository {

    override fun getDataForCompositionFrg(): Single<DomainDataForCompositionFrg> {
        return sharedPreferenceDataSource.getDataForCompositionFrg()
    }
}