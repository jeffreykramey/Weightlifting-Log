package com.example.lifttracker.currentWorkout

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [CurrentWorkout::class], version = 1, exportSchema = false)
abstract class CurrentWorkoutDatabase : RoomDatabase (){
    abstract val currentWorkoutDao : CurrentWorkoutDao

    companion object{
        @Volatile
        private var INSTANCE: CurrentWorkoutDatabase? = null

        fun getInstance(context: Context): CurrentWorkoutDatabase {
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(context.applicationContext, CurrentWorkoutDatabase::class.java, "current_workout_database")
                        .fallbackToDestructiveMigration()
                        .build()
                }
                INSTANCE = instance
                return instance
            }
        }
    }
}