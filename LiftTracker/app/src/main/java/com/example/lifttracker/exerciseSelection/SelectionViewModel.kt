package com.example.lifttracker.exerciseSelection

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.lifttracker.exerciseDatabase.NewExercise
import com.example.lifttracker.exerciseDatabase.NewExerciseDao
import kotlinx.coroutines.*

class SelectionViewModel (val database: NewExerciseDao, application: Application) : AndroidViewModel(application){

    val allExercises = database.getAllExercises()


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

    fun onFilter(newText : String?){
        uiScope.launch {
            filter(newText)
        }
    }

    private suspend fun filter(newText: String?){
        withContext(Dispatchers.IO){
            database.filter(newText)
        }
    }

    private val _navigateToExerciseData = MutableLiveData<String>()
    val navigateToExerciseData
        get() = _navigateToExerciseData

    fun onExerciseClicked(title: String){
        _navigateToExerciseData.value = title
    }
}

