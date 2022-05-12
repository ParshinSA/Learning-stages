package com.example.bondcalculator.domain.models.calculate_yield_2

import com.example.bondcalculator.common.*
import com.example.bondcalculator.domain.models.balance.DomainBalance
import com.example.bondcalculator.domain.models.bonds_data.DomainBondAndCalendar
import com.example.bondcalculator.domain.models.bonds_data.DomainBondFormulas
import com.example.bondcalculator.domain.models.portfplio.DomainPortfolioSettings
import com.example.bondcalculator.domain.models.purchased_bonds.DomainPurchasedBonds
import java.util.*
import javax.inject.Inject

class CalculateYield2 @Inject constructor(
    private val formulas: DomainBondFormulas,
    private val purchasedBonds: DomainPurchasedBonds,
    private val balance: DomainBalance
) {

    private lateinit var portfolioSettings: DomainPortfolioSettings

    private var startDate: Long = 0
    private var endDate: Long = 0
    private var currentDate: Long = 0

    private val generalPaymentList = TreeMap<Long, Double>()
    private var replenishmentCalendar = TreeMap<Long, Double>()

    fun execute(settings: DomainPortfolioSettings) {
        portfolioSettings = settings
        settingsCalculate()
        calculate()
    }


    private fun settingsCalculate() {
        startDate = System.currentTimeMillis().toDayTimeStamp()
        endDate = startDate + (portfolioSettings.term * ONE_YEAR_SECONDS)
        currentDate = startDate

        if (portfolioSettings.isReplenishment) {
            createReplenishmentCalendar()
            addInGeneralPaymentCalendar(replenishmentCalendar)
        }
    }

    private fun calculate() {
        var stopCalculate = false
        while (!stopCalculate) {
            updateCurrentDate()

            if (currentDate >= endDate) {
                sellAll()
                stopCalculate = true
            } else makePayment()

            byBondProcess()
        }
    }

    /**
     * bond.key - данные по текущей облигации
     * bond.key - кол-во текущих облигаций
     * */
    private fun sellAll() {
        val purchasedBondsList = purchasedBonds.getPurchasedBonds()
        for (bond in purchasedBondsList) {
            val price =
                ((formulas.getPriceToMaturity(bond.key, endDate) * bond.key.lotSize) * bond.value)
                    .roundDouble()

            purchasedBonds.removeBond(bond.key)
            balance.increment(price)
        }
    }

    private fun byBondProcess() {
        var purchaseAttemptCounter = 0

        val bondList = getBondTopList()
        while (purchaseAttemptCounter < bondList.size) {
            for (bond in bondList) {
                val newBond = bond.checkCalendar(currentDate)
                val price = formulas.getTotalPrice(newBond, currentDate) * newBond.lotSize

                if (balance.checkPurchaseAvailability(price)) byBond(newBond, price)
                else purchaseAttemptCounter++
            }
        }

    }

    private fun getBondTopList(): List<DomainBondAndCalendar> {
        return if (purchasedBonds.getPurchasedBonds().isEmpty()) {
            portfolioSettings.bondTopList
        } else {
            purchasedBonds.getPurchasedBonds().keys.toList()
        }
    }

    private fun updateCurrentDate() {
        val paymentCalendar = generalPaymentList.keys.toList()
        val nexDatePayment = paymentCalendar.firstOrNull() { date ->
            date > currentDate
        }

        if (nexDatePayment == null) return
        else if (nexDatePayment <= endDate) {
            currentDate = nexDatePayment
        }
    }

    private fun makePayment() {
        val payment = generalPaymentList[currentDate] ?: 0.0
        balance.increment(payment)
    }

    private fun createReplenishmentCalendar() {
        for (currentDay in startDate..endDate step ONE_DAY_SECONDS) {
            val numberCurrentDay = currentDay.toDateString("dd")

            if (numberCurrentDay == "01") {
                replenishmentCalendar[currentDay] = DEFAULT_REPLENISHMENT_MONTH_AMOUNT
            }
        }
    }

    private fun byBond(bond: DomainBondAndCalendar, price: Double) {
        val couponPaymentCalendar = bond.paymentCalendar.couponPayment
        val amortizationPaymentCalendar = bond.paymentCalendar.amortizationPayment

        addInGeneralPaymentCalendar(couponPaymentCalendar)
        addInGeneralPaymentCalendar(amortizationPaymentCalendar)

        balance.decrement(price)
        addBondInPurchaseList(bond)
    }

    private fun addBondInPurchaseList(bond: DomainBondAndCalendar) {
        purchasedBonds.addBond(bond)
    }

    /**
     * Добавить выплату в общий календарь выплат
     * если по указанной дате "date:Long" есть выплата,то прибавить текущюю выплату к новой выплате
     * если нет, то добавить текущую дату с текущей выплатой
     * */
    private fun addInGeneralPaymentCalendar(paymentCalendar: TreeMap<Long, Double>) {

        paymentCalendar.forEach { (date, amountMoney) ->
            if (generalPaymentList.containsKey(date)) {
                val currentPayment = generalPaymentList.getValue(date)
                generalPaymentList[date] = currentPayment + amountMoney
            } else {
                generalPaymentList[date] = amountMoney
            }
        }
    }

}