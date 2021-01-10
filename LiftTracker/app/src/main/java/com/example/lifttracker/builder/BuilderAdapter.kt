package com.example.lifttracker.builder

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.lifttracker.R
import com.example.lifttracker.Util
import com.example.lifttracker.currentWorkout.CurrentWorkout
import com.example.lifttracker.databinding.ListItemExerciseBinding
import com.example.lifttracker.databinding.ListItemWorkoutBinding
import com.example.lifttracker.exerciseDatabase.NewExercise
import com.example.lifttracker.exerciseSelection.SelectExerciseAdapter
import com.example.lifttracker.exerciseSelection.SelectExerciseListener


class BuilderAdapter: RecyclerView.Adapter<Util.TextItemViewHolder>(){
    var data = listOf<CurrentWorkout>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Util.TextItemViewHolder, position: Int) {
        val item = data[position]
        if(item.exercise.exerciseTitle == "Bench"){
            holder.textView.setTextColor(Color.RED)
        }
        else{
            holder.textView.setTextColor(Color.BLACK)
        }
        holder.textView.text = item.exercise.exerciseTitle
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Util.TextItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.text_item_view, parent, false) as TextView
        return Util.TextItemViewHolder(view)
    }




}