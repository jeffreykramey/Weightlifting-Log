package com.example.lifttracker.addExercise

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lifttracker.exerciseDatabase.NewExerciseDao
import java.lang.IllegalArgumentException

class AdderViewModelFactory (private val dataSource: NewExerciseDao, private val application: Application) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AdderViewModel::class.java)){
            return AdderViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}