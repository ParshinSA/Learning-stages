package com.example.bondcalculator.data.repositories_impl

import com.example.bondcalculator.data.data_source.intf.RequestSecuritiesDataDataSource
import com.example.bondcalculator.data.networking.models.toDomainSecuritiesData
import com.example.bondcalculator.data.networking.models.toRemoteRequestSecuritiesData
import com.example.bondcalculator.domain.models.DomainRequestSecuritiesData
import com.example.bondcalculator.domain.models.DomainSecuritiesData
import com.example.bondcalculator.domain.repositories_intf.RequestSecuritiesDataRepository
import io.reactivex.Observable
import javax.inject.Inject

class RequestSecuritiesDataRepositoryImpl @Inject constructor(
    private val requestSecuritiesData: RequestSecuritiesDataDataSource
) : RequestSecuritiesDataRepository {

    override fun requestSecuritiesData(request: DomainRequestSecuritiesData): Observable<List<DomainSecuritiesData>> {
        return requestSecuritiesData.requestSecuritiesData(request.toRemoteRequestSecuritiesData())
            .map {
                it.toDomainSecuritiesData()
            }
    }

}