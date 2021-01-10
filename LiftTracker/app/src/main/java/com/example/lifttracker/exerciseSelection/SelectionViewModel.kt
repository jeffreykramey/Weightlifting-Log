package com.example.lifttracker.exerciseSelection

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lifttracker.currentWorkout.CurrentWorkout
import com.example.lifttracker.currentWorkout.CurrentWorkoutDao
import com.example.lifttracker.exerciseDatabase.NewExercise
import com.example.lifttracker.exerciseDatabase.NewExerciseDao
import kotlinx.coroutines.*

class SelectionViewModel (val newExerciseDatabase: NewExerciseDao, val currentWorkoutDatabase: CurrentWorkoutDao, application: Application) : AndroidViewModel(application){

    val allExercises = newExerciseDatabase.getAllExercises()


    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun onAddSelected(arrayList: MutableList<CurrentWorkout>){
        uiScope.launch {
            addSelected(arrayList)
        }
    }

    private suspend fun addSelected(list: MutableList<CurrentWorkout>){
        withContext(Dispatchers.IO){
            currentWorkoutDatabase.insertAll(list)
        }
    }

    fun onClear(){
        uiScope.launch {
            clear()
        }
    }

    private suspend fun clear(){
        withContext(Dispatchers.IO){
            newExerciseDatabase.clear()
        }
    }

    fun onFilter(newText : String?){
        uiScope.launch {
            filter(newText)
        }
    }

    private suspend fun filter(newText: String?){
        withContext(Dispatchers.IO){
            newExerciseDatabase.filter(newText)
        }
    }

    private val _navigateToExerciseData = MutableLiveData<String>()
    val navigateToExerciseData
        get() = _navigateToExerciseData

    fun onExerciseClicked(title: String){
        _navigateToExerciseData.value = title
    }
}

