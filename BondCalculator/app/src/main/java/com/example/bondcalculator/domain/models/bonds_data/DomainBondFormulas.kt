package com.example.bondcalculator.domain.models.bonds_data

interface DomainBondFormulas {
    fun getTotalPrice(bond: DomainBondAndCalendar, currentDate: Long): Double
    fun getMaturityPrice(bond: DomainBondAndCalendar, currentDate: Long): Double
    fun getPriceToMaturity(bond: DomainBondAndCalendar, currentDate: Long): Double
    fun getCouponPay(bond: DomainBondAndCalendar, currentDate: Long): Double
    fun getAmortPay(bond: DomainBondAndCalendar, currentDate: Long): Double
}