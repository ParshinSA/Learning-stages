package com.example.bondcalculator.domain.interactors.intf

import com.example.bondcalculator.domain.models.DomainSecuritiesData
import io.reactivex.Observable

interface SecuritiesDataInteractor {

    fun requestSecuritiesData(): Observable<DomainSecuritiesData>

}