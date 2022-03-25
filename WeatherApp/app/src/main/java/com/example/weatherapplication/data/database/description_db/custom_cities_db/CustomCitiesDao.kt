package com.example.weatherapplication.data.database.description_db.custom_cities_db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapplication.data.database.models.city.RoomCityDto
import com.example.weatherapplication.data.database.models.city.RoomCityDtoContract.TableDatabase
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface CustomCitiesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addCity(roomCityDto: RoomCityDto): Completable

    @Query("SELECT * FROM ${TableDatabase.TABLE_NAME}")
    fun getCityList(): Observable<List<RoomCityDto>>
}