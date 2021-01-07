package com.example.lifttracker.currentWorkout

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_workout_table")
data class CurrentWorkout (
    @PrimaryKey(autoGenerate = true)
    var exerciseID: Long = 0L,

    @ColumnInfo
    var exerciseTitle: String = "",

    @ColumnInfo(name = "equipment")
    var equipment : String = "",

    @ColumnInfo(name = "metric")
    var metric : String = ""
)