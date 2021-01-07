package com.example.lifttracker.exerciseDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lifttracker.currentWorkout.CurrentWorkoutDao

@Database (entities = [NewExercise::class], version = 2, exportSchema = false)
abstract class ExerciseDatabase : RoomDatabase() {

    abstract val exerciseDatabaseDao : NewExerciseDao

    companion object{
        @Volatile
        private var INSTANCE: ExerciseDatabase? = null

        fun getInstance(context: Context): ExerciseDatabase{
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(context.applicationContext, ExerciseDatabase::class.java, "exercise_database")
                        .fallbackToDestructiveMigration()
                        .build()
                }
                INSTANCE = instance
                return instance


            }
        }
    }
}