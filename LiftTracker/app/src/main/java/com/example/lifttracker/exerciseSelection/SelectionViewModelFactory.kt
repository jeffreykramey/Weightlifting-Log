package com.example.lifttracker.exerciseSelection

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lifttracker.exerciseDatabase.NewExerciseDao
import java.lang.IllegalArgumentException

class SelectionViewModelFactory (private val dataSource: NewExerciseDao, private val application: Application) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SelectionViewModel::class.java)){
            return SelectionViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}