package com.example.weatherapplication.data.data_source.impl.shared_preference

import android.content.SharedPreferences
import com.example.weatherapplication.data.data_source.interf.shared_preference.SharedPreferenceDataSource
import com.example.weatherapplication.data.shared_prefs.SharedPrefsContract
import com.example.weatherapplication.domain.models.update_time.DomainLastTimeUpdate
import javax.inject.Inject

class SharedPreferenceDataSourceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : SharedPreferenceDataSource {


    override fun getLastUpdateTime(): DomainLastTimeUpdate {
        return DomainLastTimeUpdate(
            time = sharedPreferences.getLong(SharedPrefsContract.TIME_LAST_REQUEST_KEY, 0L)
        )
    }

    override fun saveLastUpdateTime() {
        val currentTime = System.currentTimeMillis()
        sharedPreferences.edit()
            .putLong(SharedPrefsContract.TIME_LAST_REQUEST_KEY, currentTime)
            .apply()
    }
}