package com.example.bondcalculator.domain.interactors

import com.example.bondcalculator.common.COUNT_TOP_BOUND
import com.example.bondcalculator.common.MIN_PRICE_PERCENT
import com.example.bondcalculator.common.ONE_YEAR_SECONDS
import com.example.bondcalculator.common.toDayTimeStamp
import com.example.bondcalculator.domain.instruction.analysis_portfolio_yield.AnalysisPortfolioYield
import com.example.bondcalculator.domain.instruction.calculate_portfolio_yield.CalculatePortfolioYield
import com.example.bondcalculator.domain.models.bonds_data.DomainBond
import com.example.bondcalculator.domain.models.bonds_data.DomainBondAndCalendar
import com.example.bondcalculator.domain.models.bonds_data.DomainRequestBondList
import com.example.bondcalculator.domain.models.exchange_rate.DomainExchangeRateUsdToRub
import com.example.bondcalculator.domain.models.payment_calendar.DomainRequestPaymentCalendar
import com.example.bondcalculator.domain.models.portfplio.DomainPortfolioSettings
import com.example.bondcalculator.domain.models.portfplio.DomainPortfolioYield
import com.example.bondcalculator.domain.repositories_intf.SelectedFrgRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class SelectedFrgInteractorImpl @Inject constructor(
    private val repository: SelectedFrgRepository,
    private val calculatePortfolioYield: CalculatePortfolioYield,
    private val analysisPortfolioYield: AnalysisPortfolioYield
) : SelectedFrgInteractor {

    override fun calculatePortfolioYield(portfolioSettings: DomainPortfolioSettings): Observable<DomainPortfolioYield> {
        return calculatePortfolioYield.execute(portfolioSettings)
    }

    override fun getProfitableBonds(request: DomainRequestBondList): Observable<List<DomainBondAndCalendar>> {
        return requestBoundsList(request)
            .flatMapObservable { bondList ->
                chooseTopBondList(bondList)
            }
            .flatMap { bondTopList ->
                addCalendarCouponPayment(bondTopList)
            }
    }

    private fun requestBoundsList(request: DomainRequestBondList): Single<List<DomainBond>> {
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
                        .toObservable()
                        .map { couponPaymentCalendar ->
                            DomainBondAndCalendar(
                                secId = secId,
                                nominal = nominal,
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

    override fun getExchangerRateUsdToRub(): Single<DomainExchangeRateUsdToRub> {
        return repository.getExchangeRateUsdToRub()
    }

    override fun analysisPortfolioYield(data: DomainPortfolioYield) {
        analysisAndSaveForPortfolioFrg(data)
        analysisAndSaveForPayoutsFrg(data)
        analysisAndSaveForPurchaseHistoryFrg(data)
        analysisAndSaveForTextInfoDepositFrg(data)
        analysisAndSaveForCompositionFrg(data)
    }

    private fun analysisAndSaveForCompositionFrg(data: DomainPortfolioYield) {
        val dataForCompositionFrg = analysisPortfolioYield.analysisForCompositionFrg(data)
        repository.saveDataForCompositionFrg(dataForCompositionFrg)
    }

    private fun analysisAndSaveForTextInfoDepositFrg(data: DomainPortfolioYield) {
        val dataForTextInfoDepositFrg = analysisPortfolioYield.analysisForTextInfoDepositFrg(data)
        repository.saveDataForTextInfoDepositFrg(dataForTextInfoDepositFrg)
    }

    private fun analysisAndSaveForPurchaseHistoryFrg(data: DomainPortfolioYield) {
        val dataForPurchaseHistoryFrg = analysisPortfolioYield.analysisForPurchaseHistoryFrg(data)
        repository.saveDataForPurchaseHistoryFrg(dataForPurchaseHistoryFrg)
    }

    private fun analysisAndSaveForPayoutsFrg(data: DomainPortfolioYield) {
        val dataForPayoutsFrg = analysisPortfolioYield.analysisForPayoutsFrg(data)
        repository.saveDataForPayoutsFrg(dataForPayoutsFrg)
    }

    private fun analysisAndSaveForPortfolioFrg(data: DomainPortfolioYield) {
        val dataForPortfolioFrg = analysisPortfolioYield.analysisForPortfolioFrg(data)
        repository.saveDataForPortfolioFrg(dataForPortfolioFrg)
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

}
