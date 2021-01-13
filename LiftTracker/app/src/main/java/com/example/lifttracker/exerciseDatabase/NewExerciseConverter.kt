package com.example.lifttracker.exerciseDatabase

import androidx.room.TypeConverter
import com.example.lifttracker.logDatabase.Logs
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*


class NewExerciseConverter {

    var gson = Gson()

    @TypeConverter
    fun fromJSON(json: String): ArrayList<Logs>{
        return gson.fromJson(json, object : TypeToken<ArrayList<Logs>>() {}.type )
    }

    @TypeConverter
    fun toJSON(logs: ArrayList<Logs>): String{
        return gson.toJson(logs)
    }
}