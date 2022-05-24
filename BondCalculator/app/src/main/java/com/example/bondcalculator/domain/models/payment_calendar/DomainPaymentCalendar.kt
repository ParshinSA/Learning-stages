package com.example.bondcalculator.domain.models.payment_calendar

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class DomainPaymentCalendar(
    val amortizationPayment: TreeMap<Long, Double>,
    val couponPayment: TreeMap<Long, Double>
) : Parcelable
