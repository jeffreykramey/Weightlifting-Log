package com.example.lifttracker.lifting

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lifttracker.currentWorkout.CurrentWorkout
import com.example.lifttracker.currentWorkout.CurrentWorkoutDao
import com.example.lifttracker.logDatabase.Logs
import com.example.lifttracker.logDatabase.LogsDao
import kotlinx.coroutines.*

class LiftingViewModel (val logDatabase: LogsDao, val workoutDatabase: CurrentWorkoutDao, application: Application) : AndroidViewModel(application){

    var allLogs = logDatabase.getAllLogs()
    var thisWorkout = workoutDatabase.getWorkout()
//    var currentExercise: CurrentWorkout
    var currentIndex: Int

    var setNumber = 1
    var repCount = MutableLiveData<Int>()
    var weight = MutableLiveData<Int>()
    var exerciseID : Long = 0

    init {
        repCount.value = 8
        weight.value = 315
//        currentExercise = thisWorkout.value?.get(0)!!
        currentIndex = 0
        thisWorkout.value?.let { println(it.size) }
    }


    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun onLogSet(){
        uiScope.launch {
            val newLog = Logs(exerciseID = exerciseID, setNumber = setNumber, repCount = repCount.value!!, weight = weight.value!!)
            insertLog(newLog)
            setNumber ++
        }
    }

    private suspend fun insertLog(logs: Logs){
        withContext(Dispatchers.IO){
            logDatabase.insert(logs)
        }
    }

    fun onClear(){
        uiScope.launch {
            clear()
        }
    }

    private suspend fun clear(){
        withContext(Dispatchers.IO){
            logDatabase.clear()
            setNumber = 1
        }
    }

    fun onRemoveLast(){
        uiScope.launch {
            removeLast()
        }
    }

    private suspend fun removeLast(){
        withContext(Dispatchers.IO){
            logDatabase.delete(logDatabase.getLastLog())
            setNumber --
        }
    }

}