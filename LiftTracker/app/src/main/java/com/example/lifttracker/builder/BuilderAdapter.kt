package com.example.lifttracker.builder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lifttracker.currentWorkout.CurrentWorkout
import com.example.lifttracker.databinding.ListItemWorkoutBinding


class BuilderAdapter(val clickListener: BuilderListener ): ListAdapter<CurrentWorkout, BuilderAdapter.ViewHolder>(BuilderDiffCallBack()){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val item = getItem(position)
//        holder.bind(item)

        holder.bind(getItem(position)!!, clickListener)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListItemWorkoutBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(item: CurrentWorkout, clickListener: BuilderListener) {
            binding.exercise = item
            binding.exerciseTitle.text = item.exercise.exerciseTitle
            binding.exerciseMetric.text = item.exercise.metric
            binding.clickListener = clickListener
            binding.executePendingBindings()

        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemWorkoutBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class BuilderDiffCallBack : DiffUtil.ItemCallback<CurrentWorkout>() {
    override fun areItemsTheSame(oldItem: CurrentWorkout, newItem: CurrentWorkout): Boolean {
        return oldItem.exerciseID == newItem.exerciseID
    }

    override fun areContentsTheSame(oldItem: CurrentWorkout, newItem: CurrentWorkout): Boolean {
        return oldItem == newItem
    }

}

class BuilderListener(val clickListener: (currentWorkout: CurrentWorkout) -> Unit){
//    fun onClick() = clickListener("test")
    fun onClick(exercise: CurrentWorkout) = clickListener(exercise)
}