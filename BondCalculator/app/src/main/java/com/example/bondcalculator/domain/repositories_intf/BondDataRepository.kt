package com.example.bondcalculator.domain.repositories_intf

import com.example.bondcalculator.domain.models.bonds_data.DomainBond
import com.example.bondcalculator.domain.models.bonds_data.DomainRequestBondList
import com.example.bondcalculator.domain.models.payment_calendar.DomainPaymentCalendar
import com.example.bondcalculator.domain.models.payment_calendar.DomainRequestPaymentCalendar
import io.reactivex.Observable

interface BondDataRepository {
    fun requestBondList(request: DomainRequestBondList): Observable<List<DomainBond>>
    fun requestCouponInfo(request: DomainRequestPaymentCalendar): Observable<DomainPaymentCalendar>
}
