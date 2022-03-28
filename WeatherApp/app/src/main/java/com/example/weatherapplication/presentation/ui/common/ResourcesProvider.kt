package com.example.weatherapplication.presentation.ui.common

import android.content.Context
import javax.inject.Inject

class ResourcesProvider @Inject constructor(
    private val context: Context
) {
    fun getString(resId: Int): String {
        return context.getString(resId)
    }
}