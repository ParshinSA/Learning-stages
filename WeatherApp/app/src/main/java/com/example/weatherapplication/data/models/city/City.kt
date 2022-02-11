package com.example.weatherapplication.data.models.city

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverters
import androidx.room.PrimaryKey
import com.example.weatherapplication.data.models.city.CityContract.GsonName
import com.example.weatherapplication.data.models.city.CityContract.TableDatabase.Columns
import com.example.weatherapplication.data.models.city.CityContract.TableDatabase.TABLE_NAME
import com.example.weatherapplication.data.objects.AppTypeConverter
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@TypeConverters(AppTypeConverter::class)
@Entity(tableName = TABLE_NAME, primaryKeys = [Columns.LATITUDE, Columns.LONGITUDE])
data class City(

    @ColumnInfo(name = Columns.NAME)
    @SerializedName(GsonName.NAME)
    val name: String,

    @ColumnInfo(name = Columns.LATITUDE)
    @SerializedName(GsonName.LATITUDE)
    val lat: Double,

    @ColumnInfo(name = Columns.LONGITUDE)
    @SerializedName(GsonName.LONGITUDE)
    val lon: Double,

    @ColumnInfo(name = Columns.COUNTRY)
    @SerializedName(GsonName.COUNTRY)
    val country: String,

    @ColumnInfo(name = Columns.STATE)
    @SerializedName(GsonName.STATE)
    val state: String

) : Parcelable
