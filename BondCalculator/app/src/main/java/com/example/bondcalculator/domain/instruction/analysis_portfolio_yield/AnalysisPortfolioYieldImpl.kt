package com.example.bondcalculator.domain.instruction.analysis_portfolio_yield

import com.example.bondcalculator.common.ONE_YEAR_SECONDS
import com.example.bondcalculator.common.toDateString
import com.example.bondcalculator.domain.models.analysis_portfolio_yield.DomainDataForPayoutsFrg
import com.example.bondcalculator.domain.models.analysis_portfolio_yield.DomainDataForPortfolioFrg
import com.example.bondcalculator.domain.models.analysis_portfolio_yield.DomainDataForPurchaseHistoryFrg
import com.example.bondcalculator.domain.models.portfplio.DomainPortfolioYield
import java.util.*
import javax.inject.Inject

class AnalysisPortfolioYieldImpl @Inject constructor() : AnalysisPortfolioYield {

    override fun analysisForPortfolioFrg(
        data: DomainPortfolioYield
    ): DomainDataForPortfolioFrg {
        val listShortName = data.purchaseHistory.map { it.key.shortName }.toSet()
        val counterBonds = mutableMapOf<String, Int>()

        listShortName.forEach { shortName ->
            data.purchaseHistory.forEach { (bond, amount) ->
                if (bond.shortName == shortName)
                    if (counterBonds.containsKey(shortName))
                        counterBonds[shortName] = counterBonds.getValue(shortName) + amount
                    else counterBonds[shortName] = amount
            }
        }

        return DomainDataForPortfolioFrg(counterBonds = counterBonds)
    }

    override fun analysisForPayoutsFrg(
        data: DomainPortfolioYield
    ): DomainDataForPayoutsFrg {
        val allYears = allYears(data)

        val couponPaymentsStepYear = mutableMapOf<String, Double>()
        val amortizationPaymentsStepYear = mutableMapOf<String, Double>()

        for ((bond, counterBond) in data.purchaseHistory) {

            bond.paymentCalendar.couponPayment.forEach { (date, payment) ->
                val convertDate = date.toDateString("yyyy")

                if (allYears.contains(convertDate)) {
                    couponPaymentsStepYear[convertDate] =
                        (couponPaymentsStepYear[convertDate] ?: 0.0) +
                                (payment * bond.lotSize * counterBond)
                }
            }

            bond.paymentCalendar.amortizationPayment.forEach { (date, payment) ->
                val convertDate = date.toDateString("yyyy")

                if (allYears.contains(convertDate)) {
                    amortizationPaymentsStepYear[convertDate] =
                        (amortizationPaymentsStepYear[convertDate] ?: 0.0) +
                                (payment * bond.lotSize * counterBond)
                }
            }
        }
        return DomainDataForPayoutsFrg(
            allYearsInCalculatePeriod = allYears,
            couponPaymentsStepYear = couponPaymentsStepYear,
            amortizationPaymentsStepYear = amortizationPaymentsStepYear
        )
    }

    override fun analysisForPurchaseHistoryFrg(data: DomainPortfolioYield): DomainDataForPurchaseHistoryFrg {
        val allYears = allYears(data)

        val couponPaymentsStepYear = TreeMap<String, Double>()
        val amortizationPaymentsStepYear = TreeMap<String, Double>()

        for ((bond, counterBond) in data.purchaseHistory) {

            bond.paymentCalendar.couponPayment.forEach { (date, payment) ->
                val convertDate = date.toDateString("yyyy")

                if (allYears.contains(convertDate)) {
                    couponPaymentsStepYear[convertDate] =
                        (couponPaymentsStepYear[convertDate] ?: 0.0) +
                                (payment * bond.lotSize * counterBond)
                }
            }

            bond.paymentCalendar.amortizationPayment.forEach { (date, payment) ->
                val convertDate = date.toDateString("yyyy")

                if (allYears.contains(convertDate)) {
                    amortizationPaymentsStepYear[convertDate] =
                        (amortizationPaymentsStepYear[convertDate] ?: 0.0) +
                                (payment * bond.lotSize * counterBond)
                }
            }
        }

        return DomainDataForPurchaseHistoryFrg(
            allYearsInCalculatePeriod = allYears(data),
            startBalance = data.startBalance,
            couponPaymentsStepYear = couponPaymentsStepYear,
            amortizationPaymentsStepYear = amortizationPaymentsStepYear
        )
    }

    private fun allYears(data: DomainPortfolioYield): Set<String> {
        val allYearsInCalculatePeriod = mutableSetOf<String>()

        for (date in data.startDateCalculate..data.endDateCalculate step ONE_YEAR_SECONDS / 2)
            allYearsInCalculatePeriod.add(date.toDateString("yyyy"))

        return allYearsInCalculatePeriod
    }
}