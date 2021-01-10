package com.example.lifttracker.builder

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lifttracker.currentWorkout.CurrentWorkout
import com.example.lifttracker.currentWorkout.CurrentWorkoutDao
import com.example.lifttracker.exerciseDatabase.NewExercise
import com.example.lifttracker.exerciseDatabase.NewExerciseDao

import kotlinx.coroutines.*

class BuilderViewModel (val database: CurrentWorkoutDao, application: Application) : AndroidViewModel(application){

    val workoutSummary = database.getWorkout()


    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    fun onClear(){
        uiScope.launch {
            clear()
        }
    }

    private suspend fun clear(){
        withContext(Dispatchers.IO){
            database.clear()
        }
    }
}

