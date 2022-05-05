package com.example.bondcalculator.domain.models.balance

import com.example.bondcalculator.common.roundDouble
import javax.inject.Inject

class DomainBalanceImpl @Inject constructor() : DomainBalance {
    private var currentBalance: Double = 0.0

    override fun increment(amountMoney: Double) {
        currentBalance = (currentBalance + amountMoney).roundDouble()
    }

    override fun decrement(amountMoney: Double) {
        currentBalance = (currentBalance - amountMoney).roundDouble()
    }

    override fun getBalance(): Double {
        return currentBalance
    }
}