package com.example.weatherapplication.data.models.forecast

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.weatherapplication.data.models.forecast.main.Main
import com.example.weatherapplication.data.models.forecast.sys.Sys
import com.example.weatherapplication.data.models.forecast.weather.Weather
import com.example.weatherapplication.data.models.forecast.wind.Wind
import com.example.weatherapplication.data.models.forecast.ForecastContract.GsonName
import com.example.weatherapplication.data.models.forecast.ForecastContract.TableDatabase.Columns
import com.example.weatherapplication.data.models.forecast.ForecastContract.TableDatabase.TABLE_NAME
import com.example.weatherapplication.data.models.forecast.coord.Coordination
import com.example.weatherapplication.data.objects.AppTypeConverter
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@TypeConverters(AppTypeConverter::class)
@Entity(tableName = TABLE_NAME)
data class Forecast(
    @PrimaryKey
    @ColumnInfo(name = Columns.PK_COORDINATION_CITY)
    @SerializedName(GsonName.COORDINATION_CITY)
    val coordination: Coordination,

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
