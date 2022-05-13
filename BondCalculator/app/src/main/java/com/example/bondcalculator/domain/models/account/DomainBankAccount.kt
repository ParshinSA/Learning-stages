package com.example.bondcalculator.domain.models.account


interface DomainBankAccount {
    fun userReplenishmentBalance(currentDate: Long, amountMoney: Double)
    fun increment(amountMoney: Double)
    fun decrementBalance(amountMoney: Double)
    fun getBalance(): Double
    fun accountCreate(currentDate: Long, startBalance: Double)
}