package com.example.weatherapplication.data.db.models.weather

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = WeatherContract.TABLE_NAME)
data class Weather(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = WeatherContract.Columns.PK_ID)
    val pkId: Long,
    @ColumnInfo(name = WeatherContract.Columns.WEATHER_ID)
    val idWeather: Long,
    @ColumnInfo(name = WeatherContract.Columns.DESCRIPTION)
    val description: String,
    @ColumnInfo(name = WeatherContract.Columns.ICON_ID)
    val selectionURL: String
)