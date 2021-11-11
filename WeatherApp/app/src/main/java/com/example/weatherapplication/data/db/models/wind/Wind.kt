package com.example.weatherapplication.data.db.models.wind

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = WindContract.TABLE_NAME)
data class Wind(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = WindContract.Columns.PK_ID)
    val id: Long,
    @ColumnInfo(name = WindContract.Columns.SPEED)
    val speed: Int,
    @ColumnInfo(name = WindContract.Columns.ROUTE_DEGREES)
    val routeDegrees: Int,
)