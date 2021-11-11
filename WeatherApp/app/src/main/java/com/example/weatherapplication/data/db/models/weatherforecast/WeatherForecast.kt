package com.example.weatherapplication.data.db.models.weatherforecast

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherapplication.data.db.models.city.City
import com.example.weatherapplication.data.db.models.clouds.Clouds
import com.example.weatherapplication.data.db.models.weather.Weather
import com.example.weatherapplication.data.db.models.wind.Wind

@Entity(tableName = WeatherForecastContract.TABLE_NAME)
data class WeatherForecast(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = WeatherForecastContract.Columns.PK_ID)
    val id: Long,
    @ColumnInfo(name = WeatherForecastContract.Columns.FK_WEATHER_ID)
    val weather: Weather,
    @ColumnInfo(name = WeatherForecastContract.Columns.VISIBILITY)
    val visibility: Long,
    @ColumnInfo(name = WeatherForecastContract.Columns.FK_WIND_ID)
    val wind: Wind,
    @ColumnInfo(name = WeatherForecastContract.Columns.FK_CLOUDS_ID)
    val clouds: Clouds,
    @ColumnInfo(name = WeatherForecastContract.Columns.FK_CITY_ID)
    val city: City,
    @ColumnInfo(name = WeatherForecastContract.Columns.TIME_FORECAST)
    val timeForecast: Long
) {
}