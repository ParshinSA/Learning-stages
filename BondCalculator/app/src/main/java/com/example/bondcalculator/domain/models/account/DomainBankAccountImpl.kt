package com.example.bondcalculator.domain.models.account

import com.example.bondcalculator.domain.models.balance.DomainBalance
import javax.inject.Inject

class DomainBankAccountImpl @Inject constructor(
    private val balance: DomainBalance
) : DomainBankAccount {
    private val historyReplenishment = mutableMapOf<Long, Double>()

    override fun accountCreate(currentDate: Long, startBalance: Double) {
        userReplenishmentBalance(currentDate, startBalance)
    }

    override fun userReplenishmentBalance(currentDate: Long, amountMoney: Double) {
        addHistoryReplenishment(currentDate, amountMoney)
        increment(amountMoney)
    }

    override fun increment(amountMoney: Double) {
        balance.increment(amountMoney)
    }

    override fun decrementBalance(amountMoney: Double) {
        balance.decrement(amountMoney)
    }

    override fun getBalance(): Double {
        return balance.getBalance()
    }

    private fun addHistoryReplenishment(currentDate: Long, amount: Double) {
        if (historyReplenishment.containsKey(currentDate)) {
            historyReplenishment[currentDate] = historyReplenishment.getValue(currentDate) + amount
        } else {
            historyReplenishment[currentDate] = amount
        }
    }


}