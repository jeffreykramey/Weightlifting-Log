package com.example.lifttracker.exerciseSelection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lifttracker.currentWorkout.CurrentWorkout
import com.example.lifttracker.databinding.ListItemExerciseBinding
import com.example.lifttracker.exerciseDatabase.NewExercise


class SelectExerciseAdapter (val clickListener: SelectExerciseListener): ListAdapter<NewExercise, SelectExerciseAdapter.ViewHolder>(NewExerciseDiffCallback()) {
    var workoutComposition = mutableListOf <CurrentWorkout>()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(getItem(position)!!, clickListener)

        holder.binding.checkBox.setOnClickListener { view: View ->
            var temp = CurrentWorkout(exercise = holder.binding.exercise!!)
            if(holder.binding.checkBox.isChecked){
                workoutComposition.add(temp)
            }
            else{
                workoutComposition.remove(temp)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListItemExerciseBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(item: NewExercise, clickListener: SelectExerciseListener) {
            binding.exercise = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemExerciseBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class NewExerciseDiffCallback: DiffUtil.ItemCallback<NewExercise>() {

    override fun areItemsTheSame(oldItem: NewExercise, newItem: NewExercise): Boolean {
        return oldItem.exerciseTitle == newItem.exerciseTitle
    }

    override fun areContentsTheSame(oldItem: NewExercise, newItem: NewExercise): Boolean {
        return oldItem == newItem
    }

}

class SelectExerciseListener (val clickListener: (exerciseTitle: String) -> Unit){
    fun onClick(exercise: NewExercise) = clickListener(exercise.exerciseTitle)
}

