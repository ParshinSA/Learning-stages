package com.example.bondcalculator.domain.models.portfplio

import android.util.Log
import com.example.bondcalculator.common.shiftCalendar
import com.example.bondcalculator.common.toDateString
import com.example.bondcalculator.domain.models.account.DomainBankAccount
import com.example.bondcalculator.domain.models.bonds_data.DomainBondAndCalendar
import com.example.bondcalculator.domain.models.bonds_data.DomainBondFormulas
import com.example.bondcalculator.domain.models.purchased_bonds.DomainPurchasedBonds
import javax.inject.Inject

class DomainPortfolioImpl @Inject constructor(
    private val bankAccount: DomainBankAccount,
    private val purchasedBonds: DomainPurchasedBonds,
    private val bondFormulas: DomainBondFormulas
) : DomainPortfolio {

    override fun createAccount(currentDate: Long, startBalance: Double) {
        Log.d(TAG, "createAccount: ")
        bankAccount.accountCreate(currentDate, startBalance)
    }

    override fun getBalance(): Double {
        Log.d(TAG, "getBalance: ")
        return bankAccount.getBalance()
    }

    override fun userReplenishment(currentDate: Long, amountMony: Double) {
        Log.d(TAG, "userReplenishment: ")
        bankAccount.userReplenishmentBalance(currentDate, amountMony)
    }

    override fun getPurchaseHistory(): Map<DomainBondAndCalendar, Int> {
        Log.d(TAG, "getPurchaseHistory: ")
        return purchasedBonds.getPurchasedBondsHistory()
    }

    override fun byProcess(currentDate: Long, bondTopList: List<DomainBondAndCalendar>) {
        Log.d(TAG, "byProcess: bondList $bondTopList")
        Log.d(TAG, "byProcess: currentDate ${currentDate.toDateString()}")
        var stop = false

        fun checkTopListAndBalance() {
            if (stop) return
            bondTopList.forEachIndexed { index, bond ->
                val newBond = bond.shiftCalendar(currentDate)
                val price = bondFormulas.getTotalPrice(newBond, currentDate) * bond.lotSize
                val balance = bankAccount.getBalance()
                if (balance < price) {
                    if (index == bondTopList.lastIndex) {
                        stop = true // остановит рекурсию
                        Log.d(TAG, "checkTopListAndBalance: stop = true $balance, $price")
                    }
                } else {
                    byBond(newBond, price)
                }
            }
            return checkTopListAndBalance()
        }
        checkTopListAndBalance()
    }

    private fun byBond(bond: DomainBondAndCalendar, price: Double) {
        Log.d(TAG, "byBond: before balance ${bankAccount.getBalance()}")
        purchasedBonds.addBond(bond)
        bankAccount.decrementBalance(price)
        Log.d(TAG, "byBond: after balance ${bankAccount.getBalance()}, $price, $bond")
    }

    private fun sellBond(bond: DomainBondAndCalendar, price: Double) {
        Log.d(TAG, "sellBond: before balance ${bankAccount.getBalance()}")
        purchasedBonds.removeBond(bond)
        bankAccount.increment(price)
        Log.d(TAG, "sellBond: after balance ${bankAccount.getBalance()}, $price, $bond")
    }

    override fun sellAll(currentDate: Long) {
        Log.d(TAG, "sellAll: ")
        val purchasedBonds = purchasedBonds.getPurchasedBonds()
        val copyPurchasedBonds = purchasedBonds.toMap()
        for ((bond, amount) in copyPurchasedBonds) {
            if (bond.repayment == currentDate) {
                makeRepayment(bond, currentDate, amount)
            } else {
                makeSellToMaturity(bond, currentDate, amount)
            }
        }
    }

    override fun checkPaymentCalendar(currentDate: Long) {
        Log.d(TAG, "checkPaymentCalendar: ")
        val purchasedBonds = purchasedBonds.getPurchasedBonds()
        val copyListPurchasedBonds = purchasedBonds.toMap()
        for ((bond, amount) in copyListPurchasedBonds) {
            if (bond.repayment == currentDate) {
                makeRepayment(bond, currentDate, amount)
            } else {
                makeCouponPayment(bond, amount, currentDate)
                makeAmortPayment(bond, amount, currentDate)
            }
        }
    }

    private fun makeRepayment(bond: DomainBondAndCalendar, currentDate: Long, amountBond: Int) {
        Log.d(TAG, "makeRepayment: ")
        val price = bondFormulas.getMaturityPrice(bond, currentDate) * bond.lotSize * amountBond
        sellBond(bond, price)
    }

    private fun makeCouponPayment(bond: DomainBondAndCalendar, amountBond: Int, currentDate: Long) {
        Log.d(TAG, "makeCouponPayment: ")
        if (bond.paymentCalendar.couponPayment.contains(currentDate)) {
            val price = bondFormulas.getCouponPay(bond, currentDate) * bond.lotSize * amountBond
            bankAccount.increment(price)
        }
    }

    private fun makeSellToMaturity(bond: DomainBondAndCalendar, currentDate: Long, amount: Int) {
        Log.d(TAG, "makeSellToMaturity: ")
        val price = bondFormulas.getPriceToMaturity(bond, currentDate) * bond.lotSize * amount
        sellBond(bond, price)
    }

    private fun makeAmortPayment(bond: DomainBondAndCalendar, amountBond: Int, currentDate: Long) {
        Log.d(TAG, "makeAmortPayment: ")
        if (bond.paymentCalendar.amortizationPayment.contains(currentDate)) {
            val price = bondFormulas.getAmortPay(bond, currentDate) * bond.lotSize * amountBond
            bankAccount.increment(price)
        }
    }

    companion object {
        private val TAG = this::class.qualifiedName
    }
}
