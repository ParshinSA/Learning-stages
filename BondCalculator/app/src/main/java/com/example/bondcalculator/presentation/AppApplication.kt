package com.example.bondcalculator.presentation

import android.app.Application
import com.example.bondcalculator.common.di.AppComponent
import com.example.bondcalculator.common.di.DaggerAppComponent

class AppApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent
            .builder()
            .context(context = this)
            .build()
    }

}