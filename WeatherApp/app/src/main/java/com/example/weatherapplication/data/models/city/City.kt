package com.example.weatherapplication.data.models.city

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.example.weatherapplication.data.models.city.CityContract.GsonName
import com.example.weatherapplication.data.models.city.CityContract.TableDatabase.Columns
import com.example.weatherapplication.data.models.city.CityContract.TableDatabase.TABLE_NAME
import com.google.gson.annotations.SerializedName


@Entity(tableName = TABLE_NAME, primaryKeys = [Columns.LATITUDE, Columns.LONGITUDE])
data class City(

    @ColumnInfo(name = Columns.NAME)
    @SerializedName(GsonName.NAME)
    val name: String,

    @ColumnInfo(name = Columns.LATITUDE)
    @SerializedName(GsonName.LATITUDE)
    val latitude: Double,

    @ColumnInfo(name = Columns.LONGITUDE)
    @SerializedName(GsonName.LONGITUDE)
    val longitude: Double,

    @ColumnInfo(name = Columns.COUNTRY)
    @SerializedName(GsonName.COUNTRY)
    val country: String,

    @ColumnInfo(name = Columns.STATE)
    @SerializedName(GsonName.STATE)
    val state: String

)
