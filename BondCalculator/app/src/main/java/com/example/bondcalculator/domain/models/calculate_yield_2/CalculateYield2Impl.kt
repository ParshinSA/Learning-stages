package com.example.bondcalculator.domain.models.calculate_yield_2

import android.util.Log
import com.example.bondcalculator.common.*
import com.example.bondcalculator.domain.models.balance.DomainBalance
import com.example.bondcalculator.domain.models.bonds_data.DomainBondAndCalendar
import com.example.bondcalculator.domain.models.bonds_data.DomainBondFormulas
import com.example.bondcalculator.domain.models.portfplio.DomainPortfolioSettings
import com.example.bondcalculator.domain.models.portfplio.DomainPortfolioYield
import com.example.bondcalculator.domain.models.purchased_bonds.DomainPurchasedBonds
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject

class CalculateYield2Impl @Inject constructor(
    private val formulas: DomainBondFormulas,
    private val purchasedBonds: DomainPurchasedBonds,
    private val balance: DomainBalance
) : CalculateYield2 {

    private lateinit var portfolioSettings: DomainPortfolioSettings

    private var startDate: Long = 0
    private var endDate: Long = 0
    private var currentDate: Long = 0

    private val generalPaymentList = TreeMap<Long, Double>()
    private var replenishmentCalendar = TreeMap<Long, Double>()

    override fun execute(settings: DomainPortfolioSettings): Observable<DomainPortfolioYield> {
        return Observable.create { subscription ->
            portfolioSettings = settings
            settingsCalculate()
            subscription.onNext(calculate())
        }
    }

    private fun settingsCalculate() {
        startDate = System.currentTimeMillis().toDayTimeStamp()
        endDate = startDate + (portfolioSettings.term * ONE_YEAR_SECONDS)
        currentDate = startDate

        balance.clear()
        purchasedBonds.clear()
        generalPaymentList.clear()
        replenishmentCalendar.clear()

        balance.increment(portfolioSettings.startBalance)

        if (portfolioSettings.isReplenishment) {
            createReplenishmentCalendar()
            addInGeneralPaymentCalendar(replenishmentCalendar)
        }
    }

    private fun calculate(): DomainPortfolioYield {
        while (true) {
            val previousDate = currentDate
            updateCurrentDate()
            Log.d(TAG, "calculate: current date ${currentDate.toDateString()}")

            if (currentDate == previousDate && currentDate != startDate) {
                sellAll()
                break
            } else if (currentDate >= endDate) {
                sellAll()
                break
            } else makePayment()
            byBondProcess()
        }

        return DomainPortfolioYield(
            startBalance = portfolioSettings.startBalance,
            resultBalance = balance.getBalance(),
            purchaseHistory = purchasedBonds.getPurchasedBondsHistory().toMap()
        )
    }

    /**
     * bond.key - данные по текущей облигации
     * bond.value - кол-во текущих облигаций
     * */
    private fun sellAll() {
        Log.d(TAG, "sellAll: ")
        val copyPurchasedBondsList = purchasedBonds.getPurchasedBonds().toMap()
        for (bond in copyPurchasedBondsList) {
            val price =
                ((formulas.getPriceToMaturity(bond.key, endDate) * bond.key.lotSize) * bond.value)
                    .roundDouble()

            Log.d(TAG, "sellAll: bond $bond")
            Log.d(TAG, "sellAll: price $price")
            purchasedBonds.removeBond(bond.key)
            balance.increment(price)
            Log.d(TAG, "sellAll: balance ${balance.getBalance()}")
        }
    }

    private fun byBondProcess() {
        Log.d(TAG, "byBondProcess: ")
        var purchaseAttemptCounter = 0

        val bondList = getBondTopList().shuffled()
        while (purchaseAttemptCounter < bondList.size) {
            for (bond in bondList) {
                val newBond = bond.checkCalendar(currentDate)
                val price = formulas.getTotalPrice(newBond, currentDate) * newBond.lotSize
                if (balance.checkPurchaseAvailability(price)) {
                    byBond(newBond, price)
                } else {
                    purchaseAttemptCounter++
                    Log.d(TAG, "byBondProcess: counter $purchaseAttemptCounter")
                }
            }
        }
    }

    private fun getBondTopList(): List<DomainBondAndCalendar> {
        return if (purchasedBonds.getPurchasedBonds().isEmpty()) {
            portfolioSettings.bondTopList
        } else {
            purchasedBonds.checkMaturity(currentDate)
            purchasedBonds.getPurchasedBonds().keys.toList()
        }
    }

    private fun updateCurrentDate() {
        Log.d(TAG, "updateCurrentDate: ")
        val paymentCalendar = generalPaymentList.keys
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
        Log.d(TAG, "makePayment: payment $payment ")
        balance.increment(payment)
        Log.d(TAG, "makePayment: balance ${balance.getBalance()}")
    }

    private fun createReplenishmentCalendar() {
        Log.d(TAG, "createReplenishmentCalendar: ")
        for (currentDay in startDate..endDate step ONE_DAY_SECONDS) {
            val numberCurrentDay = currentDay.toDateString("dd")

            if (numberCurrentDay == "01") {
                replenishmentCalendar[currentDay] = DEFAULT_REPLENISHMENT_MONTH_AMOUNT
            }
        }
    }

    private fun byBond(bond: DomainBondAndCalendar, price: Double) {
        Log.d(TAG, "byBond: ")
        Log.d(TAG, "byBond: bond $bond")
        val couponPaymentCalendar = bond.paymentCalendar.couponPayment
        val amortizationPaymentCalendar = bond.paymentCalendar.amortizationPayment

        addInGeneralPaymentCalendar(couponPaymentCalendar)
        addInGeneralPaymentCalendar(amortizationPaymentCalendar)

        purchasedBonds.addBond(bond)
        balance.decrement(price)
    }

    /**
     * Добавить выплату в общий календарь выплат
     * если по указанной дате "date:Long" есть выплата,то прибавить текущюю выплату к новой выплате
     * если нет, то добавить текущую дату с текущей выплатой
     * */
    private fun addInGeneralPaymentCalendar(paymentCalendar: TreeMap<Long, Double>) {
        Log.d(TAG, "addInGeneralPaymentCalendar: ")
        for ((date, amountMoney) in paymentCalendar) {
            if (date !in currentDate + 1..endDate) {
                continue
            }

            if (generalPaymentList.containsKey(date)) {
                val currentPayment = generalPaymentList.getValue(date)
                generalPaymentList[date] = (currentPayment + amountMoney).roundDouble()
            } else {
                generalPaymentList[date] = amountMoney
            }
        }
    }

    companion object {
        private val TAG = "portfolio"
    }
}