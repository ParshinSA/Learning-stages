package com.example.bondcalculator.domain.interactors

import com.example.bondcalculator.common.COUNT_TOP_BOUND
import com.example.bondcalculator.common.MIN_PRICE_PERCENT
import com.example.bondcalculator.common.ONE_YEAR_SECONDS
import com.example.bondcalculator.common.toDayTimeStamp
import com.example.bondcalculator.domain.interactors.intf.SelectedDataInteractor
import com.example.bondcalculator.domain.models.bonds_data.DomainBond
import com.example.bondcalculator.domain.models.bonds_data.DomainBondAndCalendar
import com.example.bondcalculator.domain.models.bonds_data.DomainRequestBondList
import com.example.bondcalculator.domain.models.calculate_yield_2.CalculateYield2
import com.example.bondcalculator.domain.models.exchange_rate.DomainExchangeRateUsdToRub
import com.example.bondcalculator.domain.models.payment_calendar.DomainRequestPaymentCalendar
import com.example.bondcalculator.domain.models.portfplio.DomainPortfolioSettings
import com.example.bondcalculator.domain.models.portfplio.DomainPortfolioYield
import com.example.bondcalculator.domain.repositories_intf.BondDataRepository
import com.example.bondcalculator.domain.repositories_intf.ExchangeRateRepository
import io.reactivex.Observable
import javax.inject.Inject

class SelectedDataInteractorImpl @Inject constructor(
    private val bondDataRepository: BondDataRepository,
    private val exchangeRateRepository: ExchangeRateRepository,
    private val calculateYieldPortfolio: CalculateYield2,
) : SelectedDataInteractor {

    override fun calculateYieldPortfolio(portfolioSettings: DomainPortfolioSettings): Observable<DomainPortfolioYield> {
        return calculateYieldPortfolio.execute(portfolioSettings)
    }

    override fun getProfitableBonds(request: DomainRequestBondList): Observable<List<DomainBondAndCalendar>> {
        return requestBoundsList(request)
            .flatMap { bondList ->
                chooseTopBondList(bondList)
            }
            .flatMap { bondTopList ->
                addCalendarCouponPayment(bondTopList)
            }
    }


    private fun requestBoundsList(request: DomainRequestBondList): Observable<List<DomainBond>> {
        return bondDataRepository.requestBondList(request)
            .map { bondList ->
                val currentDate = System.currentTimeMillis().toDayTimeStamp()
                bondList
                    .filter { it.pricePercent < MIN_PRICE_PERCENT }
                    .filter { it.repayment > currentDate + ONE_YEAR_SECONDS }
                    .filter { it.couponPercent > 0 }
            }
    }

    private fun addCalendarCouponPayment(bondYtmTopList: List<DomainBond>): Observable<List<DomainBondAndCalendar>> {
        return Observable.fromIterable(bondYtmTopList)
            .flatMap { bond ->
                with(bond) {
                    val request = DomainRequestPaymentCalendar(secId)
                    bondDataRepository.requestCouponInfo(request)
                        .map { couponPaymentCalendar ->
                            DomainBondAndCalendar(
                                secId = secId,
                                shortName = shortName,
                                couponValuePercent = couponPercent,
                                pricePercent = pricePercent,
                                lotSize = lotSize,
                                repayment = repayment,
                                couponPeriod = couponPeriod,
                                paymentCalendar = couponPaymentCalendar
                            )
                        }
                }
            }
            .buffer(bondYtmTopList.size)
    }

    override fun getExchangerRateUsdToRub(): Observable<DomainExchangeRateUsdToRub> {
        return exchangeRateRepository.getExchangeRateUsdToRub()
    }

    private fun chooseTopBondList(bondList: List<DomainBond>): Observable<List<DomainBond>> {
        return Observable.create { subscription ->
            var top = bondList.sortedBy(DomainBond::yieldPercent)
            top = top.reversed() // от большего к меньшему
            top = top.filterIndexed { index, _ ->
                index < COUNT_TOP_BOUND
            } // выбираем первые "count" облигаций

            subscription.onNext(top)
            subscription.onComplete()
        }
    }

    companion object {
        private val TAG = this::class.qualifiedName
    }
}
