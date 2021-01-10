package com.example.lifttracker.builder

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lifttracker.currentWorkout.CurrentWorkoutDao
import com.example.lifttracker.exerciseDatabase.NewExerciseDao
import com.example.lifttracker.exerciseSelection.SelectionViewModel
import java.lang.IllegalArgumentException

class BuilderViewModelFactory (private val dataSource: CurrentWorkoutDao, private val application: Application) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(BuilderViewModel::class.java)){
            return BuilderViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}