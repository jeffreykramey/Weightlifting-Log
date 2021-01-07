package com.example.lifttracker.exerciseDatabase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NewExerciseDao {

    @Insert
    fun insert(newExercise: NewExercise)

    @Update
    fun update(newExercise: NewExercise)

    @Query("SELECT * from exercise_table WHERE exerciseTitle = :exerciseTitle" )
    fun get(exerciseTitle: String) : NewExercise

    @Query("DELETE FROM exercise_table")
    fun clear()

    @Query("SELECT * FROM exercise_table ORDER BY exerciseTitle ASC")
    fun getAllExercises(): LiveData<List<NewExercise>>

    @Query("SELECT * FROM exercise_table WHERE exerciseTitle = :exerciseTitle ORDER BY exerciseTitle ASC")
    fun filter(exerciseTitle: String?): LiveData<List<NewExercise>>
}