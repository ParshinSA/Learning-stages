package com.example.bondcalculator.domain.instruction.aplication_counter

import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApplicationCounterImpl @Inject constructor() : ApplicationCounter {

    private var counter = 0

    override fun incrementCounter() {
        counter++
        Log.d(TAG, "incrementCounter: $counter ")
    }

    companion object {
        private const val TAG = "ApplicationCounterImpl"
    }
}