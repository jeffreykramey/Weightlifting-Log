package com.example.lifttracker.currentWorkout

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CurrentWorkoutDao {

    @Insert
    fun insert(currentWorkout: CurrentWorkout)

    @Query("Select * FROM current_workout_table ORDER BY exerciseID ASC")
    fun getAllExercises(): LiveData<List<CurrentWorkout>>
}