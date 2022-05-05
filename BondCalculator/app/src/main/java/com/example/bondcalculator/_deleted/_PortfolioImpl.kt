package com.example.bondcalculator._deleted//package com.example.bondcalculator.portfolio
//
//import android.util.Log
//import com.example.bondcalculator.common.*
//import com.example.bondcalculator.domain.models.bonds_data.BondFormulas
//import com.example.bondcalculator.domain.models.bonds_data.DomainBondAndCalendar
//import com.example.bondcalculator.presentation.models.TypeInvestmentAccount.IIS
//import com.example.bondcalculator.presentation.models.TypeInvestmentCurrency.RUB
//import io.reactivex.Observable
//import javax.inject.Inject
//
//class PortfolioImpl @Inject constructor(
//    private val bondFormulas: BondFormulas
//) : Portfolio {
//    private lateinit var portfolioSettings: PortfolioSettings
//    private val listPurchasedBonds = mutableMapOf<DomainBondAndCalendar, Int>()
//    private val purchaseHistory = mutableMapOf<DomainBondAndCalendar, Int>()
//    private val replenishmentHistory = mutableMapOf<Long, Double>()
//    private var currentBalance = 0.0
//
//    override fun setSettings(portfolioSettings: PortfolioSettings): Observable<PortfolioYield> {
//        return Observable.create { subscription ->
//            this.portfolioSettings = portfolioSettings
//            subscription.onNext(calculatePortfolioYield())
//            subscription.onComplete()
//        }
//    }
//
//    private fun incrementBalance(amountMoney: Double) {
//        currentBalance = (currentBalance + amountMoney).roundDouble()
//    }
//
//    private fun decrementBalance(amountMoney: Double) {
//        currentBalance = (currentBalance - amountMoney).roundDouble()
//    }
//
//    private fun by(bond: DomainBondAndCalendar, price: Double) {
//        Log.d(TAG, "by: $currentBalance")
//        addBondInListPurchaseBonds(bond)
//        decrementBalance(price)
//    }
//
//    private fun sell(bond: DomainBondAndCalendar, amount: Int, price: Double) {
//        removeBondFromListPurchaseBonds(bond)
//        addBondInPurchaseHistory(bond, amount)
//        incrementBalance(price)
//    }
//
//    private fun addBondInPurchaseHistory(bond: DomainBondAndCalendar, amount: Int) {
//        Log.d(TAG, "addBondInPurchaseHistory: ")
//        purchaseHistory[bond] = amount
//    }
//
//    private fun removeBondFromListPurchaseBonds(bond: DomainBondAndCalendar) {
//        Log.d(TAG, "removeBondFromListPurchaseBonds: ")
//        listPurchasedBonds.remove(bond)
//    }
//
//    private fun addBondInListPurchaseBonds(bond: DomainBondAndCalendar) {
//        if (listPurchasedBonds.containsKey(bond)) {
//            listPurchasedBonds[bond] = listPurchasedBonds.getValue(bond) + 1
//            Log.d(TAG, " by: increment $listPurchasedBonds")
//            // 2) если нет, добавляем
//        } else {
//            listPurchasedBonds[bond] = 1
//            Log.d(TAG, " by: add bond $listPurchasedBonds")
//        }
//    }
//
//
//    private fun calculatePortfolioYield(): PortfolioYield {
//        // очищаем закуп на случай перезапуска
//        listPurchasedBonds.clear()
//
//        // дата начала расчета
//        val startDateCalculate = System.currentTimeMillis().toDayTimeStamp()
//        // дата конца расчета
//        val endDateCalculate = portfolioSettings.term
//
//        val benefitsCalendarPayment =
//            if (portfolioSettings.account == IIS) {
//                getBenefitsCalendarPayment(startDateCalculate, endDateCalculate)
//            } else emptyList()
//
//        // запуск итерации с шагом в один день
//        for (currentDate in startDateCalculate..endDateCalculate step ONE_DAY_SECONDS) {
//            Log.d(
//                TAG,
//                "calculatePortfolioYield: FOR START current day ${currentDate.toDateString()}"
//            )
//
//            when (currentDate) {
//                startDateCalculate -> {
//                    Log.d(TAG, "calculatePortfolioYield: start by")
//                    replenishment(currentDate, portfolioSettings.startBalance)
//                    byBonds(currentDate)
//                }
//                // если текущаяя дата последний день расчета, то продать все
//                endDateCalculate -> {
//                    Log.d(TAG, "calculatePortfolioYield: END DATE")
//                    paymentBenefits(currentDate, benefitsCalendarPayment)
//                    sellAll(currentDate)
//                }
//                else -> {
//                    monthReplenishment(currentDate)
//                    paymentBenefits(currentDate, benefitsCalendarPayment)
//                    checkPaymentCalendar(currentDate)
//                    byBonds(currentDate)
//                }
//            }
//        }
//        return PortfolioYield(
//            startBalance = portfolioSettings.startBalance,
//            resultBalance = currentBalance,
//            purchaseHistory = purchaseHistory
//        )
//    }
//
//    private fun getBenefitsCalendarPayment(
//        startDate: Long,
//        endDate: Long
//    ): List<Long> {
//        val benefitsCalendarPayment = mutableListOf<Long>()
//        for (currentDate in startDate..endDate step ONE_YEAR_SECONDS) {
//            if (currentDate == startDate) continue
//            benefitsCalendarPayment.add(currentDate)
//        }
//        return benefitsCalendarPayment.toList()
//    }
//
//    private fun byBonds(currentDate: Long) {
//        var stop = false
//        fun checkTopListAndBalance() {
//            if (stop) return
//            portfolioSettings.bondTopList.forEachIndexed { index, bond ->
//                val price = bondFormulas.getTotalPrice(bond, currentDate) * bond.lotSize
//                if (currentBalance < price) {
//                    if (index == portfolioSettings.bondTopList.lastIndex)
//                        stop = true // остановит рекурсию
//                } else {
//                    val newBond = bond.shiftCalendar(currentDate)
//                    by(newBond, price)
//                }
//            }
//            return checkTopListAndBalance()
//        }
//        checkTopListAndBalance()
//    }
//
//    private fun sellAll(currentDate: Long) {
//        val copyListPurchasedBonds = listPurchasedBonds.toMap()
//        for ((bond, amount) in copyListPurchasedBonds) {
//            if (bond.repayment == currentDate) {
//                makeRepayment(bond, currentDate, amount)
//            } else {
//                sellToMaturity(bond, currentDate, amount)
//            }
//        }
//    }
//
//    private fun sellToMaturity(bond: DomainBondAndCalendar, currentDate: Long, amount: Int) {
//        val price = bondFormulas.getPriceToMaturity(bond, currentDate) * bond.lotSize * amount
//        sell(bond, amount, price)
//    }
//
//    private fun checkPaymentCalendar(currentDate: Long) {
//        val copyListPurchasedBonds = listPurchasedBonds.toMap()
//        for ((bond, amount) in copyListPurchasedBonds) {
//            if (bond.repayment == currentDate) {
//                makeRepayment(bond, currentDate, amount)
//            } else {
//                makeCouponPayment(bond, amount, currentDate)
//                makeAmortizationPayment(bond, amount, currentDate)
//            }
//        }
//    }
//
//    private fun makeRepayment(bond: DomainBondAndCalendar, currentDate: Long, amount: Int) {
//        val price = bondFormulas.getMaturityPrice(bond, currentDate) * bond.lotSize * amount
//        sell(bond, amount, price)
//        Log.d(TAG, "makeRepayment: $price")
//    }
//
//    private fun makeAmortizationPayment(
//        bond: DomainBondAndCalendar,
//        amount: Int,
//        currentDate: Long
//    ) {
//        if (bond.paymentCalendar.amortizationPayment.contains(currentDate)) {
//            val price = bondFormulas.getAmortPay(bond, currentDate) * bond.lotSize * amount
//            incrementBalance(price)
//            Log.d(TAG, "makeAmortizationPayment: $price")
//        }
//    }
//
//    private fun makeCouponPayment(bond: DomainBondAndCalendar, amount: Int, currentDate: Long) {
//        if (bond.paymentCalendar.couponPayment.contains(currentDate)) {
//            val price = bondFormulas.getCouponPay(bond, currentDate) * bond.lotSize * amount
//            incrementBalance(price)
//            Log.d(TAG, "makeCouponPayment: $price")
//        }
//    }
//
//    private fun paymentBenefits(currentDate: Long, benefitsCalendarPayment: List<Long>) {
//        if (portfolioSettings.account != IIS) return
//
//        if (benefitsCalendarPayment.contains(currentDate)) {
//
//            val startDate = currentDate - ONE_YEAR_SECONDS
//            val lastYearHistoryReplenishment = replenishmentHistory
//                .filter { it.key in startDate..currentDate }
//            val replenishmentAmount = lastYearHistoryReplenishment.values.sum()
//
//            val price = if (replenishmentAmount > MAX_BENEFITS)
//                MAX_BENEFITS / ONE_HUNDRED_PERCENT * 13
//            else replenishmentAmount / ONE_HUNDRED_PERCENT * 13
//
//            incrementBalance(price)
//            Log.d(TAG, "yearReplenishment: $price")
//        }
//    }
//
//    private fun monthReplenishment(currentDate: Long) {
//        if (!portfolioSettings.isReplenishment) return
//
//        if (currentDate.toDateString("dd") == "01") {
//            val price = if (portfolioSettings.currency == RUB) {
//                DEFAULT_REPLENISHMENT_MONTH_AMOUNT
//            } else {
//                DEFAULT_REPLENISHMENT_MONTH_AMOUNT / portfolioSettings.exchangeRateUsdToRub
//            }
//            Log.d(TAG, "monthReplenishment: $price")
//            replenishment(currentDate, price)
//        }
//    }
//
//    private fun replenishment(currentDate: Long, price: Double) {
//        addHistoryReplenishment(currentDate, price)
//        incrementBalance(price)
//    }
//
//    private fun addHistoryReplenishment(currentDate: Long, amount: Double) {
//        if (replenishmentHistory.containsKey(currentDate)) {
//            replenishmentHistory[currentDate] = replenishmentHistory.getValue(currentDate) + amount
//        } else {
//            replenishmentHistory[currentDate] = amount
//        }
//    }
//
//    companion object {
//        private const val TAG = "PortfolioImpl"
//    }
//}