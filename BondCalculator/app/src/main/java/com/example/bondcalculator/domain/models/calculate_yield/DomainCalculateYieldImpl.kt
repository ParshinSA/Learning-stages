package com.example.bondcalculator.domain.models.calculate_yield

import com.example.bondcalculator.common.*
import com.example.bondcalculator.domain.models.portfplio.DomainPortfolio
import com.example.bondcalculator.domain.models.portfplio.DomainPortfolioSettings
import com.example.bondcalculator.domain.models.portfplio.DomainPortfolioYield
import com.example.bondcalculator.presentation.models.TypeInvestmentCurrency
import io.reactivex.Observable
import javax.inject.Inject

class DomainCalculateYieldImpl @Inject constructor(
    private val portfolio: DomainPortfolio
) : DomainCalculateYield {
    private lateinit var portfolioSettings: DomainPortfolioSettings

    override fun calculatePortfolioYield(portfolioSettings: DomainPortfolioSettings): Observable<DomainPortfolioYield> {
        return Observable.create { subscription ->

            this.portfolioSettings = portfolioSettings
            // дата начала расчета
            val startDate = System.currentTimeMillis().toDayTimeStamp()
            // дата конца расчета
            val endDate = startDate + (portfolioSettings.term * ONE_YEAR_SECONDS)


            // запуск итерации с шагом в один день
            for (currentDate in startDate..endDate step ONE_DAY_SECONDS) {

                when (currentDate) {
                    startDate -> {
                        portfolio.createAccount(currentDate, portfolioSettings.startBalance)
                        portfolio.byProcess(currentDate, portfolioSettings.bondTopList)
                    }
                    endDate -> {
                        portfolio.sellAll(currentDate)
                    }
                    else -> {
                        portfolio.checkPaymentCalendar(currentDate)
                        if (portfolioSettings.isReplenishment) checkMonthReplenishment(currentDate)
                        portfolio.byProcess(currentDate, portfolioSettings.bondTopList)
                    }
                }
            }
            subscription.onNext(
                DomainPortfolioYield(
                    startBalance = portfolioSettings.startBalance,
                    resultBalance = portfolio.getBalance(),
                    purchaseHistory = portfolio.getPurchaseHistory()
                )
            )
        }
    }


    private fun checkMonthReplenishment(currentDate: Long) {
        if (currentDate.toDateString("dd") == "01") {
            val price = if (portfolioSettings.currency == TypeInvestmentCurrency.RUB) {
                DEFAULT_REPLENISHMENT_MONTH_AMOUNT
            } else {
                DEFAULT_REPLENISHMENT_MONTH_AMOUNT / portfolioSettings.exchangeRateUsdToRub
            }
            portfolio.userReplenishment(currentDate, price)
        }
    }

    companion object {
        private const val TAG = "PortfolioImpl"
    }
}