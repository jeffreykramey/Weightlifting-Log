package com.example.lifttracker.builder

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lifttracker.currentWorkout.CurrentWorkout
import com.example.lifttracker.currentWorkout.CurrentWorkoutDao

import kotlinx.coroutines.*

class BuilderViewModel (val database: CurrentWorkoutDao, application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var thisWorkout = MutableLiveData<CurrentWorkout>()



    init {
        initExercise()
    }

    //launches coroutine, create the new ex
    private fun initExercise() {
        uiScope.launch {
            thisWorkout.value = CurrentWorkout()
        }
    }

}