package com.example.weatherapplication.data.models.forecast

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.weatherapplication.data.models.clouds.Clouds
import com.example.weatherapplication.data.models.main.Main
import com.example.weatherapplication.data.models.sys.Sys
import com.example.weatherapplication.data.models.weather.Weather
import com.example.weatherapplication.data.models.wind.Wind
import com.example.weatherapplication.data.models.forecast.ForecastContract.GsonName
import com.example.weatherapplication.data.models.forecast.ForecastContract.DatabaseTable.Columns
import com.example.weatherapplication.data.models.forecast.ForecastContract.DatabaseTable.TABLE_NAME
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@TypeConverters(ForecastConverterTypeField::class)
@Entity(tableName = TABLE_NAME)
data class Forecast(
    @PrimaryKey
    @ColumnInfo(name = Columns.PK_CITY_ID)
    @SerializedName(GsonName.CITY_ID)
    val cityId: Int,

    @ColumnInfo(name = Columns.MAIN)
    @SerializedName(GsonName.MAIN)
    val main: Main,

    @ColumnInfo(name = Columns.WEATHER)
    @SerializedName(GsonName.WEATHER)
    val weather: List<Weather>,

    @ColumnInfo(name = Columns.VISIBILITY)
    @SerializedName(GsonName.VISIBILITY)
    val visibility: Long,

    @ColumnInfo(name = Columns.WIND)
    @SerializedName(GsonName.WIND)
    val wind: Wind,

    @ColumnInfo(name = Columns.CLOUDS)
    @SerializedName(GsonName.CLOUDS)
    val clouds: Clouds,

    @ColumnInfo(name = Columns.CITY_NAME)
    @SerializedName(GsonName.CITY_NAME)
    val cityName: String,

    @ColumnInfo(name = Columns.SYS)
    @SerializedName(GsonName.SYS)
    val sys: Sys,

    @ColumnInfo(name = Columns.TIME_FORECAST)
    @SerializedName(GsonName.TIME_FORECAST)
    val timeForecast: Long

) : Parcelable
