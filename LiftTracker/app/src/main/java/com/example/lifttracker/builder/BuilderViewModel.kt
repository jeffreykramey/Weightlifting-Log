package com.example.lifttracker.builder

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lifttracker.currentWorkout.CurrentWorkout
import com.example.lifttracker.currentWorkout.CurrentWorkoutDao
import com.example.lifttracker.exerciseDatabase.NewExercise

import kotlinx.coroutines.*

class BuilderViewModel (application: Application) : AndroidViewModel(application) {
    var exercises = MutableLiveData<ArrayList<NewExercise>>()

}