package com.example.lifttracker.exerciseSelection

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lifttracker.currentWorkout.CurrentWorkout
import com.example.lifttracker.currentWorkout.CurrentWorkoutDao
import com.example.lifttracker.exerciseDatabase.NewExerciseDao
import java.lang.IllegalArgumentException

class SelectionViewModelFactory (private val dataSource1: NewExerciseDao, private val dataSource2: CurrentWorkoutDao, private val application: Application) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SelectionViewModel::class.java)){
            return SelectionViewModel(dataSource1, dataSource2, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}