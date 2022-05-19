package com.example.bondcalculator.common.di.modules

import android.content.Context
import android.content.SharedPreferences
import com.example.bondcalculator.common.SHARED_PREFS_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MemoryModule {

    @Provides
    @Singleton
    fun provideSharedPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(
            SHARED_PREFS_NAME,
            Context.MODE_PRIVATE
        )
    }

}