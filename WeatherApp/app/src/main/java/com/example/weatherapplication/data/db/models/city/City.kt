package com.example.weatherapplication.data.db.models.city

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = CityContract.TABLE_NAME)
data class City(
    @PrimaryKey
    @ColumnInfo(name = CityContract.Columns.PK_CITY_ID)
    val id: Long,
    @ColumnInfo(name = CityContract.Columns.CITY_NAME)
    val name: String
)