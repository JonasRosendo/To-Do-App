package com.jonasrosendo.to_doapp.fragments.tasks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jonasrosendo.to_doapp.data.model.Task
import com.jonasrosendo.to_doapp.databinding.ItemTaskAdapterBinding

class TaskAdapter(var tasks: List<Task>) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val bindingView = ItemTaskAdapterBinding.inflate(inflater, parent, false)
        return ViewHolder(bindingView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount(): Int = tasks.size

    fun update(newTasks: List<Task>) {
        val taskDiffUtil = TaskDiffUtil(tasks, newTasks)
        val diffResult = DiffUtil.calculateDiff(taskDiffUtil)
        tasks = newTasks
        diffResult.dispatchUpdatesTo(this)
    }


    class ViewHolder(private val binding: ItemTaskAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.task = task
            binding.executePendingBindings()
        }
    }
}
