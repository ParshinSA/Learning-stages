package com.example.bondcalculator.domain.interactors

import com.example.bondcalculator.common.COUNT_TOP_BOUND
import com.example.bondcalculator.common.MIN_PRICE_PERCENT
import com.example.bondcalculator.common.ONE_YEAR_SECONDS
import com.example.bondcalculator.common.toDayTimeStamp
import com.example.bondcalculator.domain.models.bonds_data.DomainBond
import com.example.bondcalculator.domain.models.bonds_data.DomainBondAndCalendar
import com.example.bondcalculator.domain.models.bonds_data.DomainRequestBondList
import com.example.bondcalculator.domain.models.calculate_yield.CalculateYield
import com.example.bondcalculator.domain.models.exchange_rate.DomainExchangeRateUsdToRub
import com.example.bondcalculator.domain.models.payment_calendar.DomainRequestPaymentCalendar
import com.example.bondcalculator.domain.models.portfplio.DomainPortfolioSettings
import com.example.bondcalculator.domain.models.portfplio.DomainPortfolioYield
import com.example.bondcalculator.domain.repositories_intf.SelectedFrgRepository
import io.reactivex.Observable
import javax.inject.Inject

class SelectedFrgInteractorImpl @Inject constructor(
    private val repository: SelectedFrgRepository,
    private val calculateYieldPortfolio: CalculateYield,
) : SelectedFrgInteractor {

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
        return repository.requestBondList(request)
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
                    repository.requestCouponInfo(request)
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
        return repository.getExchangeRateUsdToRub()
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
