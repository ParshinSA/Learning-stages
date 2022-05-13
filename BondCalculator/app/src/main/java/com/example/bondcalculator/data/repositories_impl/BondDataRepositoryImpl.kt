package com.example.bondcalculator.data.repositories_impl

import com.example.bondcalculator.data.data_source.intf.RemoteBondDataDataSource
import com.example.bondcalculator.data.networking.models.toDomainBondData
import com.example.bondcalculator.data.networking.models.toDomainCouponInfo
import com.example.bondcalculator.data.networking.models.toRemoteRequestBondData
import com.example.bondcalculator.data.networking.models.toRemoteRequestCouponInfo
import com.example.bondcalculator.domain.models.bonds_data.DomainBond
import com.example.bondcalculator.domain.models.bonds_data.DomainRequestBondList
import com.example.bondcalculator.domain.models.payment_calendar.DomainPaymentCalendar
import com.example.bondcalculator.domain.models.payment_calendar.DomainRequestPaymentCalendar
import com.example.bondcalculator.domain.repositories_intf.BondDataRepository
import io.reactivex.Observable
import javax.inject.Inject

class BondDataRepositoryImpl @Inject constructor(
    private val remoteBondData: RemoteBondDataDataSource
) : BondDataRepository {

    override fun requestBondList(request: DomainRequestBondList): Observable<List<DomainBond>> {
        return remoteBondData.requestBondList(request.toRemoteRequestBondData())
            .map { it.toDomainBondData() }
    }

    override fun requestCouponInfo(request: DomainRequestPaymentCalendar): Observable<DomainPaymentCalendar> {
        return remoteBondData.requestCouponInfo(request.toRemoteRequestCouponInfo())
            .map { it.toDomainCouponInfo() }
    }

}