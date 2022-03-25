package com.example.weatherapplication.data.data_source.interf.shared_preference

import com.example.weatherapplication.domain.models.update_time.DomainLastTimeUpdate

interface SharedPreferenceDataSource {

    fun getLastUpdateTime(): DomainLastTimeUpdate
    fun saveLastUpdateTime()

}