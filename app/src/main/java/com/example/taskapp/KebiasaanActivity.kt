package com.example.taskapp

import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.taskapp.databinding.ActivityKebiasaanBinding
import java.util.*

class KebiasaanBaruActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKebiasaanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout for this activity using ViewBinding
        binding = ActivityKebiasaanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up a click listener for the "Selesai" button to save the task
        binding.selesaiButton.setOnClickListener {
            // Get the task name from the input field
            val taskName = binding.taskNameEditText.text.toString()

            // Get the numeric value from the jumlah aktivitas field if visible
            val jumlahAktivitas = if (binding.jumlahAktivitasInputLayout.visibility == View.VISIBLE) {
                binding.jumlahAktivitasEditText.text.toString()
            } else {
                ""
            }

            // Get the reminder time (if available)
            val reminderTime = binding.textPengingat.text.toString()

            // Validate if the task name is not empty
            if (taskName.isNotEmpty()) {
                // Save the task name, jumlah aktivitas, and reminder time using the saveHabitTask function
                saveHabitTask(taskName, jumlahAktivitas, reminderTime)
                Toast.makeText(this, "Task saved successfully", Toast.LENGTH_SHORT).show()

                // Clear the input field for a new task
                binding.taskNameEditText.text?.clear()

                // Optionally: Close the activity or navigate back
                finish()
            } else {
                Toast.makeText(this, "Please enter a task name", Toast.LENGTH_SHORT).show()
            }
        }

        // Load saved reminder time if available
        loadReminderTime()

        // Set up the reminder click listener
        setupReminderClickListener()

        // Handle the visibility of the "Jumlah aktivitas dalam sehari" field
        binding.radioNilaiNumerik.setOnClickListener {
            binding.jumlahAktivitasInputLayout.visibility = View.VISIBLE
            binding.checkboxDaysContainer.visibility = View.GONE
        }

        binding.radioYaAtauTidak.setOnClickListener {
            binding.jumlahAktivitasInputLayout.visibility = View.GONE
            binding.checkboxDaysContainer.visibility = View.GONE
        }

        binding.radioHariTertentu.setOnClickListener {
            binding.jumlahAktivitasInputLayout.visibility = View.GONE
            binding.checkboxDaysContainer.visibility = View.VISIBLE
        }

        // Listeners for Tiap Hari and Siklus
        binding.radioTiapHari.setOnClickListener {
            binding.jumlahAktivitasInputLayout.visibility = View.GONE
            binding.checkboxDaysContainer.visibility = View.GONE
        }

        binding.radioSiklus.setOnClickListener {
            binding.jumlahAktivitasInputLayout.visibility = View.GONE
            binding.checkboxDaysContainer.visibility = View.GONE
        }

        // Set up a click listener for the priority LinearLayout
        binding.layoutPrioritas.setOnClickListener {
            showPriorityDialog()
        }
    }

    // Function to show the custom priority dialog
    private fun showPriorityDialog() {
        // Inflate the custom dialog layout
        val dialogView = layoutInflater.inflate(R.layout.dialog_set_priority, null)

        // Find views in the custom dialog layout
        val tvPriorityValue = dialogView.findViewById<TextView>(R.id.tvPriorityValue)
        val btnPlus = dialogView.findViewById<ImageButton>(R.id.btnPlus)
        val btnMinus = dialogView.findViewById<ImageButton>(R.id.btnMinus)
        val btnCancel = dialogView.findViewById<Button>(R.id.btnCancel)
        val btnOk = dialogView.findViewById<Button>(R.id.btnOk)

        // Variable to track the current priority
        var priority = tvPriorityValue.text.toString().toInt() // Start with the default 1

        // Increase priority when plus button is clicked
        btnPlus.setOnClickListener {
            if (priority < 10) { // Set the maximum limit to 10
                priority++
                tvPriorityValue.text = priority.toString()
            }
        }

        // Decrease priority when minus button is clicked
        btnMinus.setOnClickListener {
            if (priority > 1) { // Set the minimum limit to 1
                priority--
                tvPriorityValue.text = priority.toString()
            }
        }

        // Build and show the dialog
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        // Handle Cancel button click
        btnCancel.setOnClickListener {
            dialog.dismiss() // Close the dialog
        }

        // Handle OK button click
        btnOk.setOnClickListener {
            // Update the "Prioritas" button text with the selected priority
            binding.buttonDefaultPrioritas.text = priority.toString()
            dialog.dismiss() // Close the dialog
        }

        // Show the dialog
        dialog.show()
    }

    // Function to save the task name and jumlah aktivitas to SharedPreferences
    // In saveHabitTask() method
    private fun saveHabitTask(taskName: String, jumlahAktivitas: String, reminderTime: String) {
        val sharedPref = getSharedPreferences("TaskApp", Context.MODE_PRIVATE)
        val taskSet = sharedPref.getStringSet("habit_task_names", mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        val habitTimes = sharedPref.getStringSet("habit_reminder_times", mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        val habitActivityCounts = sharedPref.getStringSet("habit_activity_counts", mutableSetOf())?.toMutableSet() ?: mutableSetOf()

        // Add the new task, time, and activity count
        taskSet.add(taskName)
        habitTimes.add(reminderTime)
        habitActivityCounts.add(jumlahAktivitas)

        with(sharedPref.edit()) {
            putStringSet("habit_task_names", taskSet)
            putStringSet("habit_reminder_times", habitTimes)
            putStringSet("habit_activity_counts", habitActivityCounts)
            apply()
        }
    }


    // Function to set up the reminder click listener
    private fun setupReminderClickListener() {
        // Reference to the reminder text view
        val reminderTextView = binding.textPengingat

        // Set a click listener on the reminder layout
        binding.reminderLayout.setOnClickListener {
            // Get the current time
            val calendar = Calendar.getInstance()
            val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
            val currentMinute = calendar.get(Calendar.MINUTE)

            // Show the TimePickerDialog
            val timePickerDialog = TimePickerDialog(
                this,
                { _, hourOfDay, minute ->
                    // Update the reminder text with the selected time
                    val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
                    reminderTextView.text = selectedTime

                    // Save the selected time to SharedPreferences
                    saveReminderTime(selectedTime)
                },
                currentHour, currentMinute, true
            )
            timePickerDialog.show()
        }
    }

    // Function to save the selected reminder time to SharedPreferences
    private fun saveReminderTime(time: String) {
        val sharedPref = getSharedPreferences("TaskApp", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("habit_reminder_time", time)
            apply()
        }
    }

    // Function to load the saved reminder time from SharedPreferences
    private fun loadReminderTime() {
        val sharedPref = getSharedPreferences("TaskApp", Context.MODE_PRIVATE)
        val savedTime = sharedPref.getString("habit_reminder_time", "0")

        // Update the reminder TextView with the saved time, or "0" if not set
        binding.textPengingat.text = savedTime
    }
}
