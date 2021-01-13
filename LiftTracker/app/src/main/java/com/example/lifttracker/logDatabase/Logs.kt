package com.example.lifttracker.logDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "logs_table")
data class Logs(
    @PrimaryKey(autoGenerate = false)
    var logID: Long = System.currentTimeMillis(),

    @ColumnInfo
    var exerciseID: Long = 0,

    @ColumnInfo
    var setNumber : Int = 0,

    @ColumnInfo
    var repCount : Int = 0,

    @ColumnInfo
    var weight : Int = 0
)
