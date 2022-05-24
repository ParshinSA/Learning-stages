package com.example.bondcalculator.domain.models.portfplio

import android.os.Parcelable
import com.example.bondcalculator.domain.models.bonds_data.DomainBondAndCalendar
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DomainPortfolioYield(
    val startBalance: Double,
    val resultBalance: Double,
    val purchaseHistory: Map<DomainBondAndCalendar, Int>
) : Parcelable
