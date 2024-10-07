package com.example.taskapp

import CalendarTugasAdapter
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class TugasBaruActivity : AppCompatActivity() {

    private var isDescriptionVisible = true
    private var currentMonth = 4 // Misalkan April
    private var currentYear = 2024
    val calendar = Calendar.getInstance()
    val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
    

    private lateinit var calendarAdapter: CalendarTugasAdapter
    private lateinit var selectedTimeTextView: TextView
    private lateinit var timePicker: TimePicker
    private lateinit var priorityValue: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tugas_baru)

        // Setup Edge-to-Edge UI
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Setup Calendar RecyclerView
        val calendarRecyclerView: RecyclerView = findViewById(R.id.calenderRecyclerView)
        calendarRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Setup month navigation
        val prevMonth: ImageView = findViewById(R.id.prevMonth)
        val nextMonth: ImageView = findViewById(R.id.nextMonth)
        val monthTextView: TextView = findViewById(R.id.monthTextView)

        // Komponen kalender
        val linearMonth: View = findViewById(R.id.linearMonth)
        calendarRecyclerView.visibility = View.GONE // Awalnya kalender disembunyikan

        updateCalendar()

        prevMonth.setOnClickListener {
            changeMonth(-1)
        }

        nextMonth.setOnClickListener {
            changeMonth(1)
        }

        // Update the Calendar with actual dates
        updateCalendarAdapter()

        // Setup TimePicker
        selectedTimeTextView = findViewById(R.id.selectedTimeTextView)
        timePicker = findViewById(R.id.timePicker)
        timePicker.setIs24HourView(true)

        // Setup initial time (set ke 20:00 untuk contoh)
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 20)
        calendar.set(Calendar.MINUTE, 0)

        updateSelectedTime(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE))

        // Listener untuk update waktu yang dipilih
        timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            updateSelectedTime(hourOfDay, minute)
        }

        // Setup Buttons
        val backButton: ImageView = findViewById(R.id.backButton)
        val doneButton: TextView = findViewById(R.id.doneButton)
        val addMemberButton: Button = findViewById(R.id.addMemberButton)

        // Komponen deskripsi dan lainnya
        val descriptionLayout: View = findViewById(R.id.descriptionInputLayout)
        val addSubtaskButton: View = findViewById(R.id.addSubtaskButton)
        val reminderLabel: View = findViewById(R.id.reminderLabel)
        val reminderValue: View = findViewById(R.id.reminderValue)
        val priorityLabel: View = findViewById(R.id.priorityLabel)
        priorityValue = findViewById(R.id.priorityValue) // Referensi ke TextView Priority
        val postponeTaskButton: View = findViewById(R.id.postponeTaskButton)
        val toggleButton: ImageView = findViewById(R.id.toggleVisibilityButton)

        // Menyembunyikan komponen saat kalender terlihat
        descriptionLayout.visibility = View.VISIBLE
        linearMonth.visibility = View.GONE // Kalender disembunyikan saat deskripsi terlihat

        toggleButton.setOnClickListener {
            if (isDescriptionVisible) {
                descriptionLayout.visibility = View.GONE
                addSubtaskButton.visibility = View.GONE
                reminderLabel.visibility = View.GONE
                reminderValue.visibility = View.GONE
                priorityLabel.visibility = View.GONE
                priorityValue.visibility = View.GONE
                postponeTaskButton.visibility = View.GONE

                linearMonth.visibility = View.VISIBLE
                calendarRecyclerView.visibility = View.VISIBLE // Tampilkan kalender
                toggleButton.setImageResource(R.drawable.arrow_down) // Ubah ikon
            } else {
                descriptionLayout.visibility = View.VISIBLE
                addSubtaskButton.visibility = View.VISIBLE
                reminderLabel.visibility = View.VISIBLE
                reminderValue.visibility = View.VISIBLE
                priorityLabel.visibility = View.VISIBLE
                priorityValue.visibility = View.VISIBLE
                postponeTaskButton.visibility = View.VISIBLE

                linearMonth.visibility = View.GONE
                calendarRecyclerView.visibility = View.GONE // Sembunyikan kalender
                toggleButton.setImageResource(R.drawable.arrow_up) // Ubah ikon
            }

            isDescriptionVisible = !isDescriptionVisible
        }

        // Setup for priorityLabel to show dialog
        priorityLabel.setOnClickListener {
            showPriorityDialog()
        }
    }

    private fun showPriorityDialog() {
        // Inflate layout dialog
        val dialogView = layoutInflater.inflate(R.layout.dialog_set_priority, null)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        val btnMinus: ImageButton = dialogView.findViewById(R.id.btnMinus)
        val btnPlus: ImageButton = dialogView.findViewById(R.id.btnPlus)
        val tvPriorityValue: TextView = dialogView.findViewById(R.id.tvPriorityValue)
        val btnCancel: Button = dialogView.findViewById(R.id.btnCancel)
        val btnOk: Button = dialogView.findViewById(R.id.btnOk)
        val doneButton: TextView = findViewById(R.id.doneButton)


        var priorityValueDialog = 1 // Nilai prioritas dialog

        doneButton.setOnClickListener {
            val taskName = findViewById<TextView>(R.id.taskNameEditText).text.toString()
            val priority = priorityValue.text.toString()
            val date = "$currentDay/$currentMonth/$currentYear" // Assuming you're storing date this way
            val time = selectedTimeTextView.text.toString()

            // Save the task data
            saveTaskData(taskName, priority, date, time)

            // Close the activity
            finish()
        }



        btnMinus.setOnClickListener {
            if (priorityValueDialog > 1) {
                priorityValueDialog--
                tvPriorityValue.text = priorityValueDialog.toString()
            }
        }

        btnPlus.setOnClickListener {
            priorityValueDialog++
            tvPriorityValue.text = priorityValueDialog.toString()
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnOk.setOnClickListener {
            priorityValue.text = priorityValueDialog.toString()
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun changeMonth(offset: Int) {
        currentMonth += offset
        if (currentMonth < 1) {
            currentMonth = 12
            currentYear -= 1
        } else if (currentMonth > 12) {
            currentMonth = 1
            currentYear += 1
        }
        updateCalendar()
        updateCalendarAdapter() // Memperbarui adapter saat bulan berubah
    }

    private fun updateCalendar() {
        val monthTextView: TextView = findViewById(R.id.monthTextView)
        val monthName = SimpleDateFormat("MMMM", Locale("id")).format(Calendar.getInstance().apply {
            set(Calendar.MONTH, currentMonth - 1)
        }.time)

        monthTextView.text = "$monthName $currentYear"
    }

    private fun updateCalendarAdapter() {
        val calendarRecyclerView: RecyclerView = findViewById(R.id.calenderRecyclerView)

        // Get list of Date objects for the current month
        val datesInMonth = getDatesForMonth(currentMonth, currentYear)

        // Update the adapter with the Date objects
        calendarAdapter = CalendarTugasAdapter(datesInMonth) { selectedDate ->
            Toast.makeText(this, "Tanggal dipilih: ${SimpleDateFormat("d MMMM yyyy", Locale.getDefault()).format(selectedDate)}", Toast.LENGTH_SHORT).show()
        }
        calendarRecyclerView.adapter = calendarAdapter
    }


    private fun getDatesForMonth(month: Int, year: Int): List<Date?> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month - 1)
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val dates = mutableListOf<Date?>()

        // Add null values for days before the 1st of the month to align the start of the week
        val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1
        for (i in 0 until firstDayOfWeek) {
            dates.add(null)
        }

        // Add actual dates for the current month
        for (day in 1..daysInMonth) {
            calendar.set(Calendar.DAY_OF_MONTH, day)
            dates.add(calendar.time)
        }

        return dates
    }
    private fun saveTaskData(taskName: String, priority: String, date: String, time: String) {
        val sharedPref = getSharedPreferences("TaskApp", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        // Get current tasks from SharedPreferences
        val taskNames = sharedPref.getStringSet("task_names", mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        val priorityLevels = sharedPref.getStringSet("priority_levels", mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        val taskDates = sharedPref.getStringSet("task_dates", mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        val taskTimes = sharedPref.getStringSet("task_times", mutableSetOf())?.toMutableSet() ?: mutableSetOf()

        // Add new task data
        taskNames.add(taskName)
        priorityLevels.add(priority)
        taskDates.add(date)
        taskTimes.add(time)

        // Save updated tasks to SharedPreferences
        editor.putStringSet("task_names", taskNames)
        editor.putStringSet("priority_levels", priorityLevels)
        editor.putStringSet("task_dates", taskDates)
        editor.putStringSet("task_times", taskTimes)
        editor.apply()
    }


    private fun updateSelectedTime(hour: Int, minute: Int) {
        val formattedTime = String.format("%02d:%02d", hour, minute)
        selectedTimeTextView.text = formattedTime
    }
}
