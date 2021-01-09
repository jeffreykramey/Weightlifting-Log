package com.example.lifttracker.exerciseSelection

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.SearchView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.lifttracker.databinding.ListItemExerciseBinding
import com.example.lifttracker.exerciseDatabase.NewExercise


class SelectExerciseAdapter (val clickListener: SelectExerciseListener): androidx.recyclerview.widget.ListAdapter <NewExercise, SelectExerciseAdapter.ViewHolder>(NewExerciseDiffCallback()) {
    var mutableList = ArrayList <NewExercise?>()
//    var data = listOf<NewExercise>()
//        //setter tells recyclerView of any changes
//        set(value) {
//            field = value
//            notifyDataSetChanged()
//        }
//
//    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val item = getItem(position!!, clickListener)
//        //make conditions here -> if reps & weight, do something
//        val res = holder.itemView.context.resources
        holder.bind(getItem(position)!!, clickListener)
        holder.binding.checkBox.setOnClickListener { view: View ->
            if(holder.binding.checkBox.isChecked){
                mutableList.add(holder.binding.exercise)
            }
            else{
                mutableList.remove(holder.binding.exercise)
            }
            Log.e("tag", "$mutableList")
        }

//        holder.itemView.setOnClickListener {view : View ->
//            holder.binding.checkBox.performClick()
//            if(!holder.binding.checkBox.isChecked){
//                mutableList.add(holder.binding.exerciseTitle.text as String)
//            }
//            else{
//                mutableList.remove(holder.binding.exerciseTitle.text as String)
//            }
//            Log.e("tag", "$mutableList")
//        }
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

