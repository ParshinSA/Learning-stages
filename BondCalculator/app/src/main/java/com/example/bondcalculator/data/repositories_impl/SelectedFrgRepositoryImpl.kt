package com.example.bondcalculator.data.repositories_impl

import com.example.bondcalculator.data.data_sourse.remote.intf.RemoteBondDataDataSource
import com.example.bondcalculator.data.data_sourse.remote.intf.RemoteExchangeRateDataDataSource
import com.example.bondcalculator.data.data_sourse.memory.SharedPreferenceDataSource
import com.example.bondcalculator.data.networking.models.*
import com.example.bondcalculator.domain.models.analysis_portfolio_yield.*
import com.example.bondcalculator.domain.models.bonds_data.DomainBond
import com.example.bondcalculator.domain.models.bonds_data.DomainRequestBondList
import com.example.bondcalculator.domain.models.exchange_rate.DomainExchangeRateUsdToRub
import com.example.bondcalculator.domain.models.payment_calendar.DomainPaymentCalendar
import com.example.bondcalculator.domain.models.payment_calendar.DomainRequestPaymentCalendar
import com.example.bondcalculator.domain.repositories_intf.SelectedFrgRepository
import io.reactivex.Single
import javax.inject.Inject

class SelectedFrgRepositoryImpl @Inject constructor(
    private val exchangeRateData: RemoteExchangeRateDataDataSource,
    private val bondDataDataSource: RemoteBondDataDataSource,
    private val sharedPreferenceDataSource: SharedPreferenceDataSource
) : SelectedFrgRepository {

    override fun getExchangeRateUsdToRub(): Single<DomainExchangeRateUsdToRub> {
        return exchangeRateData.getExchangeRate()
            .map { it.toDomainExchangeRateUsdToRub() }
    }

    override fun requestBondList(request: DomainRequestBondList): Single<List<DomainBond>> {
        return bondDataDataSource.requestBondList(request.toRemoteRequestBondData())
            .map { it.toDomainBondData() }
    }

    override fun requestCouponInfo(request: DomainRequestPaymentCalendar): Single<DomainPaymentCalendar> {
        return bondDataDataSource.requestCouponInfo(request.toRemoteRequestCouponInfo())
            .map { it.toDomainCouponInfo() }
    }

    override fun saveDataForPortfolioFrg(data: DomainDataForPortfolioFrg) {
        sharedPreferenceDataSource.saveDataForPortfolioFrg(data)
    }

    override fun saveDataForPayoutsFrg(data: DomainDataForPayoutsFrg) {
        sharedPreferenceDataSource.saveDataForPayoutsFrg(data)
    }

    override fun saveDataForPurchaseHistoryFrg(data: DomainDataForPurchaseHistoryFrg) {
        sharedPreferenceDataSource.saveDataForPurchaseHistoryFrg(data)
    }

    override fun saveDataForTextInfoDepositFrg(data: DomainDataForTextInfoDepositFrg) {
        sharedPreferenceDataSource.saveDataForTextInfoDepositFrg(data)
    }

    override fun saveDataForCompositionFrg(data: DomainDataForCompositionFrg) {
        sharedPreferenceDataSource.saveDataForCompositionFrg(data)
    }
}
