package com.example.bondcalculator.domain.repositories_intf

import com.example.bondcalculator.domain.models.DomainRequestSecuritiesData
import com.example.bondcalculator.domain.models.DomainSecuritiesData
import io.reactivex.Observable

interface RequestSecuritiesDataRepository {
    fun requestSecuritiesData(request: DomainRequestSecuritiesData): Observable<List<DomainSecuritiesData>>
}
