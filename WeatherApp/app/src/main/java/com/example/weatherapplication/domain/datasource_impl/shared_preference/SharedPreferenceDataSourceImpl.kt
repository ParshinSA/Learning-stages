package com.example.weatherapplication.domain.datasource_impl.shared_preference

import android.content.SharedPreferences
import com.example.weatherapplication.data.data_source.shared_preference.SharedPreferenceDataSource
import com.example.weatherapplication.presentation.common.contracts.SharedPrefsContract
import javax.inject.Inject

class SharedPreferenceDataSourceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : SharedPreferenceDataSource {


    override fun getLastUpdateTime(): Long {
        return sharedPreferences.getLong(SharedPrefsContract.TIME_LAST_REQUEST_KEY, 0L)
    }

    override fun saveLastUpdateTime(time: Long) {
        val currentTime = System.currentTimeMillis()
        sharedPreferences.edit()
            .putLong(SharedPrefsContract.TIME_LAST_REQUEST_KEY, currentTime)
            .apply()
    }
}