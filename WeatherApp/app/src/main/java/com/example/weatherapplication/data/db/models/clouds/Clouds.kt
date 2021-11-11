package com.example.weatherapplication.data.db.models.clouds

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = CloudsContract.TABLE_NAME)
data class Clouds(
    @PrimaryKey
    @ColumnInfo(name = CloudsContract.Columns.PK_QUANTITY)
    val quantity: Int
)