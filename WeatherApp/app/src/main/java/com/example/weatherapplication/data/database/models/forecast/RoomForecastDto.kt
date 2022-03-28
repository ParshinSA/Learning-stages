package com.example.weatherapplication.data.database.models.forecast

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.example.weatherapplication.data.database.description_db.forecast_db.ForecastDbContract.Database.Columns
import com.example.weatherapplication.data.database.description_db.forecast_db.ForecastDbContract.Database.NAME


@Entity(tableName = NAME, primaryKeys = [Columns.LATITUDE, Columns.LONGITUDE])
data class RoomForecastDto(

    @ColumnInfo(name = Columns.LATITUDE)
    val latitude: Double,

    @ColumnInfo(name = Columns.LONGITUDE)
    val longitude: Double,

    @ColumnInfo(name = Columns.TEMPERATURE)
    val temperature: Double,

    @ColumnInfo(name = Columns.PRESSURE)
    val pressure: Int,

    @ColumnInfo(name = Columns.HUMIDITY)
    val humidity: Int,

    @ColumnInfo(name = Columns.DESCRIPTION)
    val description: String,

    @ColumnInfo(name = Columns.ICON_ID)
    val iconId: String,

    @ColumnInfo(name = Columns.VISIBILITY)
    val visibility: Long,

    @ColumnInfo(name = Columns.WIND_SPEED)
    val windSpeed: Double,

    @ColumnInfo(name = Columns.WIND_DIRECTION_DEGREES)
    val windDirectionDegrees: Float,

    @ColumnInfo(name = Columns.CITY_NAME)
    val cityName: String,

    @ColumnInfo(name = Columns.COUNTRY)
    val country: String,

    @ColumnInfo(name = Columns.SUNRISE)
    val sunrise: Long,

    @ColumnInfo(name = Columns.SUNSET)
    val sunset: Long,

    @ColumnInfo(name = Columns.FORECAST_TIME)
    val forecastTime: Long

)
