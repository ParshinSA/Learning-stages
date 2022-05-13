package com.example.bondcalculator.domain.models.portfplio

import com.example.bondcalculator.domain.models.bonds_data.DomainBondAndCalendar

interface DomainPortfolio {
    fun sellAll(currentDate: Long)
    fun checkPaymentCalendar(currentDate: Long)
    fun getBalance(): Double
    fun byProcess(currentDate: Long, bondTopList: List<DomainBondAndCalendar>)
    fun createAccount(currentDate: Long, startBalance: Double)
    fun userReplenishment(currentDate: Long, amountMony: Double)
    fun getPurchaseHistory(): Map<DomainBondAndCalendar, Int>
}