package com.example.lifttracker.currentWorkout

import android.content.Context
import androidx.room.*


@Database(entities = [CurrentWorkout::class], version = 2, exportSchema = false)
@TypeConverters(CurrentWorkoutConverter::class) //reference to appropriate type converters
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