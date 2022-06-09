package com.example.bondcalculator.domain.instruction.calculate_portfolio_yield

import android.util.Log
import com.example.bondcalculator.common.*
import com.example.bondcalculator.domain.instruction.balance.Balance
import com.example.bondcalculator.domain.instruction.dond_formulas.BondFormulas
import com.example.bondcalculator.domain.instruction.download_progress.DownloadProgress
import com.example.bondcalculator.domain.instruction.purchased_bonds.PurchasedBonds
import com.example.bondcalculator.domain.models.bonds_data.DomainBondAndCalendar
import com.example.bondcalculator.domain.models.download_progress.DomainDownloadProgressData
import com.example.bondcalculator.domain.models.payment_calendar.DomainPaymentCalendar
import com.example.bondcalculator.domain.models.portfplio.DomainPortfolioSettings
import com.example.bondcalculator.domain.models.portfplio.DomainPortfolioYield
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject

class CalculatePortfolioYieldImpl @Inject constructor(
    private val formulas: BondFormulas,
    private val purchasedBonds: PurchasedBonds,
    private val balance: Balance,
    private val downloadProgress: DownloadProgress
) : CalculatePortfolioYield {

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
            subscription.onComplete()
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
            changeStateProgress()

            if (currentDate == previousDate && currentDate != startDate) {
                sellAll()
                changeStateProgress()
                break
            } else if (currentDate >= endDate) {
                sellAll()
                changeStateProgress()
                break
            } else makePayment()
            byBondProcess()
        }

        return DomainPortfolioYield(
            startDateCalculate = startDate,
            endDateCalculate = endDate,
            term = portfolioSettings.term,
            startBalance = portfolioSettings.startBalance,
            resultBalance = balance.getBalance(),
            purchaseHistory = purchasedBonds.getPurchasedBondsHistory(),
            generalPaymentList = generalPaymentList
        )
    }

    private fun changeStateProgress() {
        val currentProgress = (currentDate - startDate).toInt()
        val maxProgress = (endDate - startDate).toInt()
        downloadProgress.setProgressData(
            DomainDownloadProgressData(
                maxProgress = maxProgress,
                currentProgress = currentProgress
            )
        )
    }

    private fun sellAll() {
        val copyPurchasedBondsList = purchasedBonds.getPurchasedBonds().toMap()
        for ((bond, amount) in copyPurchasedBondsList) {
            val price =
                ((formulas.getPriceToMaturity(bond, endDate) * bond.lotSize) * amount)
                    .roundDouble()

            purchasedBonds.removeBond(bond)
            balance.increment(price)
        }
    }

    private fun byBondProcess() {
        var purchaseAttemptCounter = 0

        val bondList = getBondTopList().shuffled()
        while (purchaseAttemptCounter < bondList.size) {
            for (bond in bondList) {
                val newBond = checkCalendar(bond, currentDate)
                val price = formulas.getTotalPrice(newBond, currentDate) * newBond.lotSize
                if (balance.checkPurchaseAvailability(price)) {
                    byBond(newBond, price)
                } else {
                    purchaseAttemptCounter++
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
        val paymentCalendar = generalPaymentList.keys
        val nexDatePayment = paymentCalendar.firstOrNull { date ->
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
        Log.d(TAG, "byBond: ")
        val couponPaymentCalendar = bond.paymentCalendar.couponPayment
        val amortizationPaymentCalendar = bond.paymentCalendar.amortizationPayment

        addInGeneralPaymentCalendar(couponPaymentCalendar)
        addInGeneralPaymentCalendar(amortizationPaymentCalendar)

        purchasedBonds.addBond(bond)
        balance.decrement(currentDate, price)
    }

    /**
     * Добавить выплату в общий календарь выплат
     * если по указанной дате "date:Long" есть выплата,то прибавить текущюю выплату к новой выплате
     * если нет, то добавить текущую дату с текущей выплатой
     * */
    private fun addInGeneralPaymentCalendar(paymentCalendar: TreeMap<Long, Double>) {
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

    private fun checkCalendar(
        bond: DomainBondAndCalendar,
        currentDate: Long
    ): DomainBondAndCalendar {

        return if (currentDate + ONE_YEAR_SECONDS < bond.repayment) bond else {
            val oldStartCouponPayments =
                bond.paymentCalendar.couponPayment.keys.toList().sorted()[0]

            val newStartYear = currentDate.toDateString("yyyy")
            val newStartDayMonth = oldStartCouponPayments.toDateString("MM-dd")
            val newStartCouponPayments = "$newStartYear-$newStartDayMonth".toTimeStampSeconds()
            val shiftPayment: Long = newStartCouponPayments - oldStartCouponPayments

            val newAmortizationPaymentCalendar = TreeMap<Long, Double>()
            val newCouponPaymentCalendar = TreeMap<Long, Double>()

            for ((date, value) in bond.paymentCalendar.amortizationPayment) {
                newAmortizationPaymentCalendar[(date + shiftPayment)] = value
            }


            for ((date, value) in bond.paymentCalendar.couponPayment) {
                newCouponPaymentCalendar[(date + shiftPayment)] = value
            }

            val repayment = newAmortizationPaymentCalendar.keys.toList().maxOrNull()!!

            DomainBondAndCalendar(
                secId = bond.secId,
                nominal = bond.nominal,
                shortName = bond.shortName,
                couponValuePercent = bond.couponValuePercent,
                pricePercent = bond.pricePercent,
                lotSize = bond.lotSize,
                repayment = repayment,
                couponPeriod = bond.couponPeriod,
                paymentCalendar = DomainPaymentCalendar(
                    amortizationPayment = newAmortizationPaymentCalendar,
                    couponPayment = newCouponPaymentCalendar
                )
            )
        }
    }

    companion object {
        private const val TAG = "CalculatePortfolioYieldImpl"
    }
}