package com.example.bondcalculator.domain.instruction.balance

interface Balance {
    fun increment(amountMoney: Double)
    fun getBalance(): Double
    fun checkPurchaseAvailability(price: Double): Boolean
    fun clear()
    fun decrement(date: Long, amountMoney: Double)
    fun getBuyHistory(): Map<Long, Double>
}