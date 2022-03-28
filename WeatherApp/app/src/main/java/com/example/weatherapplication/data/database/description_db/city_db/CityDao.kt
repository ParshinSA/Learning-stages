package com.example.weatherapplication.data.database.description_db.city_db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapplication.data.database.models.city.RoomCityDto
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addCity(roomCityDto: RoomCityDto): Completable

    @Query("SELECT * FROM ${CityDbContract.Database.NAME}")
    fun getCityList(): Observable<List<RoomCityDto>>
}