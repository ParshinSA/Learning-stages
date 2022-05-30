package com.example.bondcalculator.domain.instruction.purchased_bonds

import com.example.bondcalculator.domain.models.bonds_data.DomainBondAndCalendar
import javax.inject.Inject

class DomainPurchasedBondsImpl @Inject constructor() : DomainPurchasedBonds {
    private val purchasedBonds = mutableMapOf<DomainBondAndCalendar, Int>()
    private val purchasedBondsHistory = mutableMapOf<DomainBondAndCalendar, Int>()

    override fun addBond(bond: DomainBondAndCalendar) {
        if (purchasedBonds.containsKey(bond)) {
            purchasedBonds[bond] = purchasedBonds.getValue(bond) + 1
        } else {
            purchasedBonds[bond] = 1
        }
    }

    override fun removeBond(bond: DomainBondAndCalendar) {
        addInPurchaseHistory(bond)
        purchasedBonds.remove(bond)
    }

    override fun getPurchasedBonds(): Map<DomainBondAndCalendar, Int> {
        return purchasedBonds
    }

    override fun getPurchasedBondsHistory(): Map<DomainBondAndCalendar, Int> {
        return purchasedBondsHistory
    }

    override fun clear() {
        purchasedBonds.clear()
        purchasedBondsHistory.clear()
    }

    private fun addInPurchaseHistory(bond: DomainBondAndCalendar) {
        purchasedBondsHistory[bond] = purchasedBonds.getValue(bond)
    }

    override fun checkMaturity(date: Long) {
        val copyPurchasedBonds = purchasedBonds.keys.toList()
        copyPurchasedBonds.forEach { bond ->
            if (bond.repayment < date) removeBond(bond)
        }
    }
}
