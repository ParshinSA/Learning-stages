package com.example.myapplication

import android.widget.EditText
import kotlin.math.floor
import kotlin.math.min

class CalculatingValues {
    private lateinit var field: EditText
    private var course: Double = 0.0

    private var valuesBuy = 0.0
    private var valuesSell = 0.0
    private var userValue = 0.0

    fun initCalculate(
        field: EditText,
        course: Double,
        userValue: Double,
        callbackBuyValues: (valuesBuy: Double) -> Unit,
        callbackSellValues: (valuesSell: Double) -> Unit,

        ) {
        this.field = field
        this.course = course
        this.userValue = userValue

        when (field.id) {
            R.id.edit_buy -> {
                calculateSell()
                callbackSellValues(valuesSell)
            }
            R.id.edit_sell -> {
                calculateBuy()
                callbackSellValues(valuesSell)
                callbackBuyValues(valuesBuy)
            }
        }
    }

    private fun calculateBuy() {
        valuesBuy = floor(min((userValue / course), 999999.0))
        valuesSell = min((valuesBuy * course), 999999999.0)
    }

    private fun calculateSell() {
        valuesSell = min((userValue * course), 999999999.0)
    }

}