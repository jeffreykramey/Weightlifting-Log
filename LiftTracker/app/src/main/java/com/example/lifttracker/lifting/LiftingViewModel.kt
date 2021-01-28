package com.example.lifttracker.lifting

import android.app.Application
import androidx.lifecycle.*
import com.example.lifttracker.currentWorkout.CurrentWorkoutDao
import com.example.lifttracker.logDatabase.Logs
import com.example.lifttracker.logDatabase.LogsDao
import kotlinx.coroutines.*

class LiftingViewModel (val logDatabase: LogsDao, val workoutDatabase: CurrentWorkoutDao, application: Application) : AndroidViewModel(application){

    var logList = logDatabase.getAllLogs()
    var thisWorkout = workoutDatabase.getWorkout()

    var tempID = 0
    var currentLogs = logDatabase.getLogsByID(1)
    val tempLogs = Transformations.switchMap(logList) {
        logDatabase.getLogsByID(thisWorkout.value?.get(tempID)?.exercise?.exerciseID)
    }


    var currentIndex: Int

    var setNumber = 1
    var repCount = MutableLiveData<Int>()
    var weight = MutableLiveData<Int>()


    init {
        repCount.value = 8
        weight.value = 315

        currentIndex = 0
        thisWorkout.value?.let { println(it.size) }
    }


    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun onLogSet(exerciseID: Long){
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

    fun loadLogsByID(exerciseID: Long?) {
        uiScope.launch {
            getLogsByID(exerciseID)
        }
    }

    private suspend fun getLogsByID(exerciseID: Long?) {
        withContext(Dispatchers.IO){
            logList = logDatabase.getLogsByID(exerciseID)
        }
    }


}