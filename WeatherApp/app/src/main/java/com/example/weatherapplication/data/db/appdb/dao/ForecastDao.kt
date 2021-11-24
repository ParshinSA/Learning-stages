package com.example.weatherapplication.data.db.appdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapplication.data.models.forecast.Forecast
import com.example.weatherapplication.data.models.forecast.ForecastContract

@Dao
interface ForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListForecast(forecasts: Forecast)

    @Query("SELECT * FROM ${ForecastContract.DatabaseTable.TABLE_NAME} WHERE ${ForecastContract.DatabaseTable.Columns.PK_CITY_ID} = :cityId OR null")
    suspend fun getWeatherForecast(cityId: Int): Forecast?

}