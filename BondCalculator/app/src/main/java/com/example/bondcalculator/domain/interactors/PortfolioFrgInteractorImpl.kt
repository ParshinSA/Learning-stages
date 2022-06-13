package com.example.bondcalculator.domain.interactors

import com.example.bondcalculator.domain.models.analysis_portfolio_yield.DomainDataForPortfolioFrg
import com.example.bondcalculator.domain.repositories_intf.PortfolioFrgRepository
import io.reactivex.Single
import javax.inject.Inject

class PortfolioFrgInteractorImpl @Inject constructor(
    private val repository: PortfolioFrgRepository
) : PortfolioFrgInteractor {

    override fun getDataForPortfolioFrg(): Single<DomainDataForPortfolioFrg> {
        return repository.getDataForPortfolioFrg()
    }

}