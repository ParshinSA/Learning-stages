package com.example.bondcalculator.domain.instruction.purchased_bonds

import com.example.bondcalculator.domain.models.bonds_data.DomainBondAndCalendar

interface DomainPurchasedBonds {
    fun getPurchasedBonds(): Map<DomainBondAndCalendar, Int>
    fun removeBond(bond: DomainBondAndCalendar)
    fun addBond(bond: DomainBondAndCalendar)
    fun getPurchasedBondsHistory(): Map<DomainBondAndCalendar, Int>
    fun clear()
    fun checkMaturity(date: Long)
}
