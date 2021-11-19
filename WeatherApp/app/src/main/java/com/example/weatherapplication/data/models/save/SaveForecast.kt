package com.example.weatherapplication.data.models.save

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = SaveForecastContract.TABLE_NAME)
data class SaveForecast(

    @PrimaryKey
    @ColumnInfo(name = SaveForecastContract.Columns.PK_ID_CITY)
    val idCity: Int,

    @ColumnInfo(name = SaveForecastContract.Columns.FORECAST_JSON)
    val forecastJson: String
)