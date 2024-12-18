package com.example.taskapp

import ProjectData
import ProjectPreferences
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.taskapp.databinding.ActivityProyekTimBinding
import java.text.SimpleDateFormat
import java.util.*

class ProyekTimActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProyekTimBinding
    private lateinit var projectPreferences: ProjectPreferences
    private var selectedDates = mutableSetOf<Int>()  // To keep track of selected dates
    private var priorityValue = 1 // Default priority value
    private var selectedReminderTime: String = "0" // Default reminder time

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProyekTimBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Initialize ProjectPreferences for saving data
        projectPreferences = ProjectPreferences(this)

        // Set radio button click listeners to toggle the visibility of the days checkboxes
        binding.rgFrequency.setOnCheckedChangeListener { _, checkedId -> /* existing logic */ }

        // Handle Tanggal Selesai toggle switch
        binding.switchTanggalSelesai.setOnCheckedChangeListener { _, isChecked -> /* existing logic */ }

        // Handle days input for Tanggal Selesai calculation
        binding.etDaysToAdd.setOnFocusChangeListener { _, hasFocus -> /* existing logic */ }

        binding.etDaysToAdd.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Dapat dibiarkan kosong jika tidak diperlukan
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val daysToAdd = s.toString().toIntOrNull() ?: 0
                calculateEndDate(daysToAdd)
            }

            override fun afterTextChanged(s: Editable?) {
                // Dapat dibiarkan kosong jika tidak diperlukan
            }
        })


        // Set up click listener for priority linear layout
        binding.linearPrioritas.setOnClickListener { showPriorityDialog() }

        // Set up click listener for reminder linear layout
        binding.linearPengingat.setOnClickListener { showReminderDialog() }
        // Set radio button click listeners to toggle the visibility of the days checkboxes
        binding.rgFrequency.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_weekly -> {
                    binding.gridDaysOfWeek.visibility = View.VISIBLE
                    binding.gridDates.visibility = View.GONE
                    binding.tlYearlyDate.visibility = View.GONE
                    binding.linearCycle.visibility = View.GONE
                }
                R.id.rb_monthly -> {
                    binding.gridDates.visibility = View.VISIBLE
                    binding.gridDaysOfWeek.visibility = View.GONE
                    binding.tlYearlyDate.visibility = View.GONE
                    binding.linearCycle.visibility = View.GONE
                    populateDatesGrid()  // Populate the dates grid when selected
                }
                R.id.rb_yearly -> {
                    binding.tlYearlyDate.visibility = View.VISIBLE
                    binding.gridDates.visibility = View.GONE
                    binding.gridDaysOfWeek.visibility = View.GONE
                    binding.linearCycle.visibility = View.GONE
                }
                R.id.rb_cycle -> {
                    // Show the text fields for "Aktivitas" and "Istirahat"
                    binding.linearCycle.visibility = View.VISIBLE
                    binding.gridDates.visibility = View.GONE
                    binding.gridDaysOfWeek.visibility = View.GONE
                    binding.tlYearlyDate.visibility = View.GONE
                }
                else -> {
                    // Hide all fields when other radio buttons are selected
                    binding.gridDaysOfWeek.visibility = View.GONE
                    binding.gridDates.visibility = View.GONE
                    binding.tlYearlyDate.visibility = View.GONE
                    binding.linearCycle.visibility = View.GONE
                }
            }
        }

        // Handle Tanggal Selesai toggle switch
        binding.switchTanggalSelesai.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.linearTanggalSelesaiInput.visibility = View.VISIBLE
                displayCurrentDate()
            } else {
                binding.linearTanggalSelesaiInput.visibility = View.GONE
            }
        }

        // Handle days input for Tanggal Selesai calculation
        binding.etDaysToAdd.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val daysToAdd = binding.etDaysToAdd.text.toString().toIntOrNull() ?: 0
                calculateEndDate(daysToAdd)
            }
        }

        // Dynamically update date when user types in number of days
        binding.etDaysToAdd.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val daysToAdd = s.toString().toIntOrNull() ?: 0
                calculateEndDate(daysToAdd)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // Set up click listener for priority linear layout
        binding.linearPrioritas.setOnClickListener {
            showPriorityDialog()
        }

        // Set up click listener for reminder linear layout
        binding.linearPengingat.setOnClickListener {
            showReminderDialog()
        }

        binding.btnDone.setOnClickListener {
            // Step 1: Get data from form fields
            val projectName = binding.taskNameEditText.text.toString()
            val projectDescription = binding.descriptionEditText.text.toString()
            val specificDate = if (binding.rbYearly.isChecked) binding.tvCurrentDate.text.toString() else null
            val activityDays = binding.etActivityDays.text.toString().toIntOrNull()
            val restDays = binding.etRestDays.text.toString().toIntOrNull()
//            val startDate = binding.tvStartDate.text.toString()
            val endDate = binding.tvCurrentDate.text.toString()
            val reminderCount = selectedReminderTime.toIntOrNull()
            val priority = priorityValue.toString()
//            val postponeTask = binding.switchTundaTugas.isChecked

            // Step 2: Check mandatory fields (project name and description)
            if (projectName.isEmpty() || projectDescription.isEmpty()) {
                Toast.makeText(this, "Nama proyek dan deskripsi harus diisi", Toast.LENGTH_SHORT).show()
            } else {
                // Step 3: Save data to SharedPreferences
                val projectData = ProjectData(
                    projectName = projectName,
                    projectDescription = projectDescription,
                    specificDate = specificDate,
                    activityDays = activityDays,
                    restDays = restDays,
//                    startDate = startDate,
                    endDate = endDate,
                    reminderCount = reminderCount,
                    priority = priority,
//                    postponeTask = postponeTask
                )
                projectPreferences.saveProjectData(projectData)

                // Step 4: Redirect to MainActivity (or other fragment)
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("fragment", "beranda") // To specify which fragment to open
                startActivity(intent)
                finish()
            }
        }

        binding.btnAddMember.setOnClickListener {
            // Handle adding member logic
        }
    }

    // Function to display the current date in the "Tanggal Selesai" field
    private fun displayCurrentDate() {
        val today = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        binding.tvCurrentDate.text = dateFormat.format(today)
    }

    // Function to calculate and display the end date based on the number of days added
    private fun calculateEndDate(daysToAdd: Int) {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, daysToAdd)

        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        binding.tvCurrentDate.text = dateFormat.format(calendar.time)
    }

    // Populate the GridLayout with buttons for the dates 1 to 31 (for monthly selection)
    private fun populateDatesGrid() {
        binding.gridDates.removeAllViews()  // Clear any existing views

        for (i in 1..31) {
            val button = Button(this).apply {
                text = i.toString()
                textSize = 16f
                setBackgroundColor(Color.TRANSPARENT)  // Default background color
                setTextColor(Color.BLACK)

                // Handle click event to toggle the selection of dates
                setOnClickListener {
                    if (selectedDates.contains(i)) {
                        // If the date is already selected, deselect it
                        setBackgroundColor(Color.TRANSPARENT)
                        selectedDates.remove(i)
                    } else {
                        // If the date is not selected, select it
                        setBackgroundColor(Color.RED)  // Selected color
                        selectedDates.add(i)
                    }
                }
            }

            // Add the button to the GridLayout
            val params = GridLayout.LayoutParams().apply {
                width = 0
                height = GridLayout.LayoutParams.WRAP_CONTENT
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            }
            binding.gridDates.addView(button, params)
        }
    }

    // Function to show priority dialog
    private fun showPriorityDialog() {
        // Inflate the custom dialog view
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_set_priority, null)

        // Create the dialog
        val alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)
            .create()

        // Find views in the dialog
        val btnMinus = dialogView.findViewById<ImageButton>(R.id.btnMinus)
        val btnPlus = dialogView.findViewById<ImageButton>(R.id.btnPlus)
        val tvPriorityValue = dialogView.findViewById<TextView>(R.id.tvPriorityValue)
        val btnCancel = dialogView.findViewById<Button>(R.id.btnCancel)
        val btnOk = dialogView.findViewById<Button>(R.id.btnOk)

        // Set the initial priority value
        tvPriorityValue.text = priorityValue.toString()

        // Set click listeners for plus and minus buttons
        btnMinus.setOnClickListener {
            if (priorityValue > 1) {
                priorityValue--
                tvPriorityValue.text = priorityValue.toString()
            }
        }

        btnPlus.setOnClickListener {
            if (priorityValue < 10) { // Limit priority between 1 and 10
                priorityValue++
                tvPriorityValue.text = priorityValue.toString()
            }
        }

        // Handle Cancel button
        btnCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        // Handle OK button
        btnOk.setOnClickListener {
            // Set the selected priority value to the TextView in the main layout
            binding.btnPrioritas.text = priorityValue.toString()
            alertDialog.dismiss()
        }

        // Show the dialog
        alertDialog.show()
    }

    // Function to show reminder dialog
    private fun showReminderDialog() {
        // Inflate the custom dialog layout
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dilaog_reminder, null)

        // Create the AlertDialog
        val alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)
            .create()

        // Get references to dialog views
        val tvTime = dialogView.findViewById<TextView>(R.id.tvTime)
        val rgType = dialogView.findViewById<RadioGroup>(R.id.rgType)
        val rgSchedule = dialogView.findViewById<RadioGroup>(R.id.rgSchedule)
        val btnOk = dialogView.findViewById<Button>(R.id.btnOk)
        val btnCancel = dialogView.findViewById<Button>(R.id.btnCancel)

        // Set up the time picker dialog
        tvTime.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePicker = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
                val formattedTime = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute)
                tvTime.text = formattedTime
            }, hour, minute, true)

            timePicker.show()
        }

        // Handle Cancel button
        btnCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        // Handle OK button
        btnOk.setOnClickListener {
            // Get selected type and schedule
            val selectedType = dialogView.findViewById<RadioButton>(rgType.checkedRadioButtonId).text.toString()
            val selectedSchedule = dialogView.findViewById<RadioButton>(rgSchedule.checkedRadioButtonId).text.toString()

            // Update reminder text with selected time and reminder settings
            selectedReminderTime = tvTime.text.toString()
            binding.btnPengingat.text = selectedReminderTime

            // For demonstration, showing a toast message
            Toast.makeText(this, "Reminder set at: $selectedReminderTime ($selectedType, $selectedSchedule)", Toast.LENGTH_LONG).show()

            alertDialog.dismiss()
        }

        // Show the dialog
        alertDialog.show()
    }
}
