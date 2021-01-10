package com.example.lifttracker.addExercise

import android.app.Application
import android.util.Log
import android.widget.RadioButton
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lifttracker.databinding.FragmentAddExerciseBinding
import com.example.lifttracker.exerciseDatabase.NewExercise
import com.example.lifttracker.exerciseDatabase.NewExerciseDao
import kotlinx.coroutines.*
import java.lang.Exception

class AdderViewModel (val database: NewExerciseDao, application: Application) : AndroidViewModel(application) {
    var exerciseTitle = MutableLiveData <String>()
//    val exerciseTitle :LiveData<String>
//        get() = _exerciseTitle

    var equipment = MutableLiveData <String>()
//    val equipment : LiveData <String>
//        get() = _equipment

    var metric = MutableLiveData <String>()
//    val metric : LiveData <String>
//        get() = _metric

    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var thisExercise1 = MutableLiveData<NewExercise>()


    init {
        exerciseTitle.value = "empty"
        equipment.value = "empty"
        metric.value = "empty"
//        initExercise()
    }

//    private fun initExercise(){
//        uiScope.launch {
//         thisExercise1.value = NewExercise(exerciseTitle.value.toString(), equipment.value.toString(), metric.value.toString())
//        }
//    }


    fun onSaveExercise(){
        uiScope.launch {
            val thisExercise = NewExercise(exerciseTitle = exerciseTitle.value.toString(), equipment =  equipment.value.toString(), metric =  metric.value.toString())
            insert(thisExercise)
        }
    }

    private suspend fun insert(newExercise: NewExercise){
        withContext(Dispatchers.IO){
            database.insert(newExercise)
        }
    }



//    binding.saveButton.setOnClickListener { view: View ->
//        //Get title
//        exerciseTitle = binding.exerciseTitle.text.toString()
//
//        //get equipment from one of the radio button groups
//        if (binding.equipmentRadioGroup1.checkedRadioButtonId != -1) {
//            equipment =
//                getRadioButtonText(binding.equipmentRadioGroup1.checkedRadioButtonId, binding)
//        } else if (binding.equipmentRadioGroup2.checkedRadioButtonId != -1) {
//            equipment =
//                getRadioButtonText(binding.equipmentRadioGroup2.checkedRadioButtonId, binding)
//        }
//
//        //get metric
//        if (binding.metricRadioGroup.checkedRadioButtonId != -1) {
//            metric = binding.metricLabel.text.toString()
//        }

    fun getRadioButtonText(
        radioButtonId: Int,
        binding: FragmentAddExerciseBinding): String {
        val rb: RadioButton = binding.root.findViewById(radioButtonId)
        return rb.text.toString()
    }
}