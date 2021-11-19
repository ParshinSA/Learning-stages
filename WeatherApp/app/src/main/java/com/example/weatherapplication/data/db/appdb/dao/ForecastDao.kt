package com.example.weatherapplication.data.db.appdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapplication.data.models.save.SaveForecast
import com.example.weatherapplication.data.models.save.SaveForecastContract

@Dao
interface ForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListForecast(forecasts: SaveForecast)

    @Query("SELECT * FROM ${SaveForecastContract.TABLE_NAME} WHERE ${SaveForecastContract.Columns.PK_ID_CITY} = :cityId OR null")
    suspend fun getWeatherForecast(cityId: Int): SaveForecast?

}