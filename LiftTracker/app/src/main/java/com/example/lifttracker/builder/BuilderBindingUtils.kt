package com.example.lifttracker.builder

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.lifttracker.currentWorkout.CurrentWorkout
import com.example.lifttracker.exerciseDatabase.NewExercise

@BindingAdapter("setExerciseTitle")
fun TextView.setExerciseTitle(item: CurrentWorkout?){
    item?.let {
        text = item.exercise.exerciseTitle
    }
}

@BindingAdapter("setExerciseMetric")
fun TextView.setExerciseMetric(item: CurrentWorkout?){
    item?.let{
        text = item.exercise.metric
    }
}