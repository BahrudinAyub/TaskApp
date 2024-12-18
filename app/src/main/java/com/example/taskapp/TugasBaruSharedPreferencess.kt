package com.example.taskapp

import android.content.Context
import android.content.SharedPreferences

data class Task(
    val taskName: String,
    val priority: String,
    val date: String,
    val time: String
)

class TugasBaruSharedPreferencesManager(private val context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("TaskApp", Context.MODE_PRIVATE)

    // Save the list of tasks
    fun saveTaskList(taskList: List<Task>) {
        val stringBuilder = StringBuilder()
        taskList.forEach { task ->
            val taskString = "${task.taskName}||${task.priority}||${task.date}||${task.time}"
            stringBuilder.append(taskString).append(";;")
        }
        val editor = sharedPreferences.edit()
        editor.putString("taskList", stringBuilder.toString())
        editor.apply()
    }

    // Retrieve the list of tasks
    fun getTaskList(): List<Task> {
        val savedString = sharedPreferences.getString("taskList", null) ?: return emptyList()
        return savedString.split(";;").filter { it.isNotEmpty() }.map { taskString ->
            val parts = taskString.split("||")
            Task(
                taskName = parts.getOrElse(0) { "" },
                priority = parts.getOrElse(1) { "" },
                date = parts.getOrElse(2) { "" },
                time = parts.getOrElse(3) { "" }
            )
        }
    }

    // Add a single task to the list
    fun addTask(task: Task) {
        val currentList = getTaskList().toMutableList()
        currentList.add(task)
        saveTaskList(currentList)
    }

    // Clear all tasks
    fun clearTasks() {
        val editor = sharedPreferences.edit()
        editor.remove("taskList")
        editor.apply()
    }

    // Remove a specific task
    fun removeTask(task: Task) {
        val currentList = getTaskList().toMutableList()
        currentList.remove(task)
        saveTaskList(currentList)
    }
}
