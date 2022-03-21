package com.example.weatherapplication.data.database.forecast_db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapplication.data.database.models.forecast.Forecast
import com.example.weatherapplication.data.database.models.forecast.ForecastContract.TableDatabase
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface ForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertForecast(forecasts: Forecast): Completable

    @Query("SELECT * FROM ${TableDatabase.TABLE_NAME}")
    fun getListForecast(): Observable<List<Forecast>>

}