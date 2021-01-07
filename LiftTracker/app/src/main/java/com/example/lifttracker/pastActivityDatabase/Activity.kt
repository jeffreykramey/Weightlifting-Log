package com.example.lifttracker.pastActivityDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant


@Entity(tableName = "past_activity_table")
data class Activity (
    @PrimaryKey
    var date: Instant = Instant.now(),

    @ColumnInfo(name = "training_duration")
    var trainingDuration : String = "",

    @ColumnInfo(name = "num_exercises")
    var numExercises : Int = 0,

    @ColumnInfo(name = "total_weight")
    var totalWeight : Int = 0


)
