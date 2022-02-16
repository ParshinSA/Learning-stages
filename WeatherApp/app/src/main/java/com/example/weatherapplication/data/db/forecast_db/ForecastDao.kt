package com.example.weatherapplication.data.db.forecast_db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapplication.data.models.forecast.Forecast
import com.example.weatherapplication.data.models.forecast.ForecastContract.TableDatabase
import io.reactivex.Flowable

@Dao
interface ForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListForecast(forecasts: Forecast)

    @Query("SELECT * FROM ${TableDatabase.TABLE_NAME}")
    fun getWeatherForecast(): Flowable<List<Forecast>>

}