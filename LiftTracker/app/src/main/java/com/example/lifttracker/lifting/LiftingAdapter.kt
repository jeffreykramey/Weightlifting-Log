package com.example.lifttracker.lifting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lifttracker.databinding.ListItemLogsBinding
import com.example.lifttracker.logDatabase.Logs

class LiftingAdapter: ListAdapter<Logs, LiftingAdapter.ViewHolder>(LogsDiffCallBack()){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListItemLogsBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(item: Logs) {
            binding.log = item
            binding.setNumber.text = item.setNumber.toString()
            binding.repCount.text = item.repCount.toString()
            binding.weight.text = item.weight.toString()
            binding.executePendingBindings()

        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemLogsBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class LogsDiffCallBack : DiffUtil.ItemCallback<Logs>() {
    override fun areItemsTheSame(oldItem: Logs, newItem: Logs): Boolean {
        return oldItem.logID == newItem.logID
    }

    override fun areContentsTheSame(oldItem: Logs, newItem: Logs): Boolean {
        return oldItem == newItem
    }

}