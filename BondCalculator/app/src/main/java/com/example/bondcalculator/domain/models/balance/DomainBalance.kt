package com.example.bondcalculator.domain.models.balance

interface DomainBalance {
    fun increment(amountMoney: Double)
    fun decrement(amountMoney: Double)
    fun getBalance(): Double
    fun checkPurchaseAvailability(price: Double): Boolean
    fun clear()
}