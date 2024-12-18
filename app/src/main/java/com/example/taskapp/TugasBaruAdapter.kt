package com.example.taskapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private var tasks: List<Task>,
    private val onTaskCompleted: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    // ViewHolder seperti biasa
    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskName: TextView = itemView.findViewById(R.id.tvTaskName)
        val taskTime: TextView = itemView.findViewById(R.id.tvTaskTime)
        val taskPriority: TextView = itemView.findViewById(R.id.tvTaskPriority)
        val taskCompleted: CheckBox = itemView.findViewById(R.id.cbTaskCompleted)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.taskName.text = task.taskName
        holder.taskTime.text = "\uD83D\uDD52 ${task.time}"
        holder.taskPriority.text = "\uD83D\uDCCB ${task.priority}"
        holder.taskCompleted.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                onTaskCompleted(task)
            }
        }
    }

    override fun getItemCount(): Int = tasks.size

    // Tambahkan metode ini untuk memperbarui data
    fun updateList(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged() // Memberitahu adapter untuk memperbarui tampilan
    }
}


