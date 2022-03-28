package com.example.weatherapplication.data.database.description_db.forecast_db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapplication.data.database.models.forecast.RoomForecastDto
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface ForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertForecast(roomForecastDto: RoomForecastDto): Completable

    @Query("SELECT * FROM ${ForecastDbContract.Database.NAME}")
    fun getListForecast(): Observable<List<RoomForecastDto>>

}