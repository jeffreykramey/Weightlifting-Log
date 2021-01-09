package com.example.lifttracker.currentWorkout

import androidx.room.TypeConverter
import com.example.lifttracker.exerciseDatabase.NewExercise
import com.google.gson.Gson

class CurrentWorkoutConverter {
    var gson = Gson()

    @TypeConverter
    fun fromJSON(json: String): NewExercise{
        return gson.fromJson(json, NewExercise::class.java)
    }

    @TypeConverter
    fun toJSON(exercise: NewExercise): String{
        return gson.toJson(exercise)
    }

}