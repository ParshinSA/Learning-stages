package com.example.bondcalculator.presentation.common

import android.content.Context
import android.graphics.Color
import javax.inject.Inject

class ResourcesProvider @Inject constructor(
    private val context: Context
) {
    fun getString(resId: Int): String {
        return context.getString(resId)
    }
}