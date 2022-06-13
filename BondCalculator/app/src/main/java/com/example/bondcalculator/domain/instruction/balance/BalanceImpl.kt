package com.example.bondcalculator.domain.instruction.balance

import com.example.bondcalculator.common.roundDouble
import javax.inject.Inject

class BalanceImpl @Inject constructor() : Balance {
    private var currentBalance: Double = 0.0
    private val buyHistory = mutableMapOf<Long, Double>()

    override fun checkPurchaseAvailability(price: Double): Boolean {
        return price <= currentBalance
    }

    override fun increment(amountMoney: Double) {
        currentBalance = (currentBalance + amountMoney).roundDouble()
    }

    override fun decrement(date: Long, amountMoney: Double) {
        currentBalance = (currentBalance - amountMoney).roundDouble()

        if (buyHistory.containsKey(date)) {
            buyHistory[date] = buyHistory.getValue(date) + amountMoney
        } else buyHistory[date] = amountMoney
    }

    override fun getBalance(): Double {
        return currentBalance
    }

    override fun getBuyHistory(): Map<Long, Double> {
        return buyHistory
    }

    override fun clear() {
        currentBalance = 0.0
    }
}