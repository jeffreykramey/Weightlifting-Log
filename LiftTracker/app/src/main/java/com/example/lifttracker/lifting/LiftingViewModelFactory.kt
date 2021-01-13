package com.example.lifttracker.lifting

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lifttracker.currentWorkout.CurrentWorkoutDao
import com.example.lifttracker.logDatabase.LogsDao
import java.lang.IllegalArgumentException

class LiftingViewModelFactory (private val dataSource1: LogsDao, private val dataSource2: CurrentWorkoutDao, private val application: Application) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LiftingViewModel::class.java)){
            return LiftingViewModel(dataSource1, dataSource2, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}