package com.example.weatherapplication.data.database.models.city

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.example.weatherapplication.data.database.description_db.city_db.CityDbContract.Database.Columns
import com.example.weatherapplication.data.database.description_db.city_db.CityDbContract.Database.NAME

@Entity(tableName = NAME, primaryKeys = [Columns.LATITUDE, Columns.LONGITUDE])
data class RoomCityDto(

    @ColumnInfo(name = Columns.NAME)
    val name: String,

    @ColumnInfo(name = Columns.LATITUDE)
    val latitude: Double,

    @ColumnInfo(name = Columns.LONGITUDE)
    val longitude: Double,

    @ColumnInfo(name = Columns.COUNTRY)
    val country: String,

    @ColumnInfo(name = Columns.STATE)
    val state: String
)
