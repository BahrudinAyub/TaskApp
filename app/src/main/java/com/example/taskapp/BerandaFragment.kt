package com.example.taskapp

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.taskapp.databinding.FragmentCatatanBinding
import java.text.SimpleDateFormat
import java.util.*

class BerandaFragment : Fragment() {

    private var _binding: FragmentCatatanBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCatatanBinding.inflate(inflater, container, false)
        val view = binding.root

        // Set onClickListener for FloatingActionButton (existing functionality)
        setupFab()

        // Set current date
        setCurrentDate()

        // Retrieve and display the habit task names
        displayHabitTasks()

        return view
    }

    private fun setCurrentDate() {
        // Get the current date
        val currentDate = Calendar.getInstance().time

        // Format the date to display as "EEEE, dd MMMM yyyy" (e.g., "Selasa, 21 April 2024")
        val dateFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale("id"))

        // Set the formatted date to the TextView
        binding.tvDate.text = dateFormat.format(currentDate)
    }

    private fun displayHabitTasks() {
        val sharedPref = requireContext().getSharedPreferences("TaskApp", Context.MODE_PRIVATE)

        // Existing habit task data
        val habitTaskNames = sharedPref.getStringSet("habit_task_names", setOf())?.toMutableList() ?: mutableListOf()
        val habitTimes = sharedPref.getStringSet("habit_reminder_times", setOf())?.toList() ?: listOf()
        val habitActivityCounts = sharedPref.getStringSet("habit_activity_counts", setOf())?.toList() ?: listOf()

        // New TugasBerulang task data
        val taskNames = sharedPref.getStringSet("task_names", setOf())?.toMutableList() ?: mutableListOf()
        val reminderTimes = sharedPref.getStringSet("reminder_times", setOf())?.toList() ?: listOf()
        val priorities = sharedPref.getStringSet("priorities", setOf())?.toList() ?: listOf()

        // Clear the container before adding new views
        binding.dailyTasksContainer.removeAllViews()

        // Existing Habit Tasks
        for ((index, taskName) in habitTaskNames.withIndex()) {
            val taskLayout = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.HORIZONTAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            }

            val taskTextLayout = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.VERTICAL
                layoutParams = LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
                )
            }

            val habitTaskLabel = TextView(requireContext()).apply {
                text = "Kebiasaan ${index + 1} — $taskName"
                textSize = 16f
                setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            }

            val iconLayout = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.HORIZONTAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            }

            val timeIcon = TextView(requireContext()).apply {
                text = "\uD83D\uDD52"
                textSize = 16f
            }

            val timeTextView = TextView(requireContext()).apply {
                text = habitTimes.getOrNull(index) ?: "09:00"
                textSize = 14f
                setTextColor(ContextCompat.getColor(requireContext(), R.color.red_button))
            }

            val activityIcon = TextView(requireContext()).apply {
                text = "\uD83D\uDCCB"
                textSize = 16f
            }

            val activityTextView = TextView(requireContext()).apply {
                text = "1/${habitActivityCounts.getOrNull(index) ?: "1"}"
                textSize = 14f
                setTextColor(ContextCompat.getColor(requireContext(), R.color.red_button))
            }

            iconLayout.addView(timeIcon)
            iconLayout.addView(timeTextView)
            iconLayout.addView(activityIcon)
            iconLayout.addView(activityTextView)

            taskTextLayout.addView(habitTaskLabel)
            taskTextLayout.addView(iconLayout)

            val habitTaskCheckbox = CheckBox(requireContext()).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                setOnClickListener {
                    // Remove the task immediately from the list
                    habitTaskNames.removeAt(index)

                    // Save the updated task list to SharedPreferences
                    saveUpdatedTasks(habitTaskNames.toSet())

                    // Refresh the list by redisplaying the tasks
                    displayHabitTasks()
                }
            }

            taskLayout.addView(taskTextLayout)
            taskLayout.addView(habitTaskCheckbox)

            binding.dailyTasksContainer.addView(taskLayout)
        }

        // New TugasBerulang Tasks
        for ((index, taskName) in taskNames.withIndex()) {
            val taskLayout = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.HORIZONTAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            }

            val taskTextLayout = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.VERTICAL
                layoutParams = LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
                )
            }

            val taskLabel = TextView(requireContext()).apply {
                text = "TugasBerulang ${index + 1} — $taskName"
                textSize = 16f
                setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            }

            val iconLayout = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.HORIZONTAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            }

            val reminderIcon = TextView(requireContext()).apply {
                text = "\uD83D\uDD52"
                textSize = 16f
            }

            val reminderTimeTextView = TextView(requireContext()).apply {
                text = reminderTimes.getOrNull(index) ?: "No reminder"
                textSize = 14f
                setTextColor(ContextCompat.getColor(requireContext(), R.color.red_button))
            }

            val priorityIcon = TextView(requireContext()).apply {
                text = "\uD83D\uDCCB"
                textSize = 16f
            }

            val priorityTextView = TextView(requireContext()).apply {
                text = "Prioritas: ${priorities.getOrNull(index) ?: "Default"}"
                textSize = 14f
                setTextColor(ContextCompat.getColor(requireContext(), R.color.red_button))
            }

            iconLayout.addView(reminderIcon)
            iconLayout.addView(reminderTimeTextView)
            iconLayout.addView(priorityIcon)
            iconLayout.addView(priorityTextView)

            taskTextLayout.addView(taskLabel)
            taskTextLayout.addView(iconLayout)

            val taskCheckbox = CheckBox(requireContext()).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                setOnClickListener {
                    // Remove the task immediately from the list
                    taskNames.removeAt(index)

                    // Save the updated task list to SharedPreferences
                    saveUpdatedTugas(taskNames.toSet())

                    // Refresh the list by redisplaying the tasks
                    displayHabitTasks()
                }
            }

            taskLayout.addView(taskTextLayout)
            taskLayout.addView(taskCheckbox)

            binding.dailyTasksContainer.addView(taskLayout)
        }
    }

    // Function to save updated habit tasks
    private fun saveUpdatedTasks(updatedTasks: Set<String>) {
        val sharedPref = requireContext().getSharedPreferences("TaskApp", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putStringSet("habit_task_names", updatedTasks)
            apply()
        }
    }

    // Function to save updated TugasBerulang tasks
    private fun saveUpdatedTugas(updatedTasks: Set<String>) {
        val sharedPref = requireContext().getSharedPreferences("TaskApp", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putStringSet("task_names", updatedTasks)
            apply()
        }
    }

    // Function to remove the new task (TugasBerulang) from SharedPreferences
    private fun removeTugasBerulang(index: Int) {
        val sharedPref = requireContext().getSharedPreferences("TaskApp", Context.MODE_PRIVATE)
        val taskNames = sharedPref.getStringSet("task_names", setOf())?.toMutableSet() ?: mutableSetOf()
        val reminderTimes = sharedPref.getStringSet("reminder_times", setOf())?.toMutableSet() ?: mutableSetOf()
        val priorities = sharedPref.getStringSet("priorities", setOf())?.toMutableSet() ?: mutableSetOf()

        if (index < taskNames.size) {
            taskNames.remove(taskNames.elementAt(index))
            reminderTimes.remove(reminderTimes.elementAt(index))
            priorities.remove(priorities.elementAt(index))

            with(sharedPref.edit()) {
                putStringSet("task_names", taskNames)
                putStringSet("reminder_times", reminderTimes)
                putStringSet("priorities", priorities)
                apply()
            }
        }
    }

    private fun setupFab() {
        binding.fabAddTask.setOnClickListener {
            val dialog = Dialog(requireContext())
            dialog.setContentView(R.layout.dialog_task_option)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            val optionTugas = dialog.findViewById<LinearLayout>(R.id.option_tugas)
            val optionTugasBerulang = dialog.findViewById<LinearLayout>(R.id.option_tugas_berulang)
            val optionKebiasaan = dialog.findViewById<LinearLayout>(R.id.option_kebiasaan)

            optionTugas.setOnClickListener {
                val intent = Intent(activity, TugasBaruActivity::class.java)
                startActivity(intent)
                dialog.dismiss()
            }

            optionTugasBerulang.setOnClickListener {
                val intent = Intent(activity, TugasBerulangActivity::class.java)
                startActivity(intent)
                dialog.dismiss()
            }

            optionKebiasaan.setOnClickListener {
                val intent = Intent(activity, KebiasaanBaruActivity::class.java)
                startActivity(intent)
                dialog.dismiss()
            }

            dialog.show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
