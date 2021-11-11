package com.example.weatherapplication.data.db.models.main

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = MainContract.TABLE_NAME)
data class Main(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = MainContract.Columns.PK_ID)
    val idPk: Long,
    @ColumnInfo(name = MainContract.Columns.TEMP)
    val temp: Int,
    @ColumnInfo(name = MainContract.Columns.PRESSURE)
    val pressure: Int,
    @ColumnInfo(name = MainContract.Columns.HUMIDITY)
    val humidity: Int
)