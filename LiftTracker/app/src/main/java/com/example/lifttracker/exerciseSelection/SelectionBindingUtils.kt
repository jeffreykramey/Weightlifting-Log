package com.example.lifttracker.exerciseSelection

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.lifttracker.R
import com.example.lifttracker.exerciseDatabase.NewExercise
import org.w3c.dom.Text

@BindingAdapter("setExerciseTitle")
fun TextView.setExerciseTitle(item: NewExercise?){
    item?.let {
        text = item.exerciseTitle
    }
}

@BindingAdapter("setExerciseMetric")
    fun TextView.setExerciseMetric(item: NewExercise?){
        item?.let{
            text = item.metric
        }
    }

@BindingAdapter("equipmentImage")
fun ImageView.setEquipmentImage(item: NewExercise?){
    item?.let {
        setImageResource(when (item.equipment) {
            "Barbell" -> R.drawable.ic_launcher_foreground
            "Dumbbell" -> R.drawable.ic_launcher_foreground
            "EZ-Bar" -> R.drawable.ic_launcher_foreground
            "Band" -> R.drawable.ic_launcher_foreground
            "Kettlebell" -> R.drawable.ic_launcher_foreground
            "Weight Plate" -> R.drawable.ic_launcher_foreground
            "Other" -> R.drawable.ic_launcher_foreground
            else -> R.drawable.ic_launcher_foreground
        })
    }
}