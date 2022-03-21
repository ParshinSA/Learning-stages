package com.example.weatherapplication.data.data_source.shared_preference

interface SharedPreferenceDataSource {

    fun getLastUpdateTime(): Long
    fun saveLastUpdateTime(time: Long)

}