package com.example.weatherapplication.data.db.custom_cities_db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapplication.data.models.city.City
import com.example.weatherapplication.data.models.city.CityContract.TableDatabase
import com.example.weatherapplication.data.models.city.CityContract.TableDatabase.Columns
import io.reactivex.Observable

@Dao
interface CustomCitiesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addCity(city: City)

    @Query("SELECT * FROM ${TableDatabase.TABLE_NAME}")
    fun getCityList(): Observable<List<City>>
}