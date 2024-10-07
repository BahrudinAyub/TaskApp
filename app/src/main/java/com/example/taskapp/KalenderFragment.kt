package com.example.taskapp

import CalendarTugasAdapter
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class KalenderFragment : Fragment() {

    private lateinit var calendarGrid: GridLayout
    private lateinit var currentMonth: Calendar
    private val tasksByDate: Map<Date, List<String>> = mapOf() // Map tugas berdasarkan tanggal

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tugas, container, false)
        calendarGrid = view.findViewById(R.id.calendarGrid)
        val previousMonthButton = view.findViewById<ImageButton>(R.id.previousMonthButton)
        val nextMonthButton = view.findViewById<ImageButton>(R.id.nextMonthButton)
        val monthYearText = view.findViewById<TextView>(R.id.monthYearText)

        // Initialize Calendar
        currentMonth = Calendar.getInstance()
        updateCalendar(monthYearText)

        previousMonthButton.setOnClickListener {
            currentMonth.add(Calendar.MONTH, -1)
            updateCalendar(monthYearText)
        }

        nextMonthButton.setOnClickListener {
            currentMonth.add(Calendar.MONTH, 1)
            updateCalendar(monthYearText)
        }

        return view
    }

    private fun updateCalendar(monthYearText: TextView) {
        val sdf = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        monthYearText.text = sdf.format(currentMonth.time)

        val days = getDaysOfMonth(currentMonth)
        calendarGrid.removeAllViews() // Bersihkan sebelum menambahkan elemen baru

        // Tambahkan item ke GridLayout secara manual
        days.forEach { date ->
            val dayTextView = TextView(requireContext()).apply {
                layoutParams = GridLayout.LayoutParams().apply {
                    width = 0
                    height = ViewGroup.LayoutParams.WRAP_CONTENT
                    columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                }
                gravity = Gravity.CENTER
                setPadding(8, 16, 8, 16)
                text = if (date != null) SimpleDateFormat("d", Locale.getDefault()).format(date) else ""
            }

            dayTextView.setOnClickListener {
                date?.let { onDateSelected(it) }
            }

            calendarGrid.addView(dayTextView)
        }

        // Update hari di header sesuai bulan yang dipilih
        setDaysOfWeekHeader(currentMonth)
    }

    private fun getDaysOfMonth(calendar: Calendar): List<Date?> {
        val days = mutableListOf<Date?>()
        val tempCalendar = calendar.clone() as Calendar

        tempCalendar.set(Calendar.DAY_OF_MONTH, 1)
        val firstDayOfWeek = tempCalendar.get(Calendar.DAY_OF_WEEK) - 1

        for (i in 0 until firstDayOfWeek) {
            days.add(null)
        }

        while (tempCalendar.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)) {
            days.add(tempCalendar.time)
            tempCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        val remainingDaysInWeek = 7 - (days.size % 7)
        if (remainingDaysInWeek < 7) {
            for (i in 0 until remainingDaysInWeek) {
                days.add(null)
            }
        }

        return days
    }

    private fun onDateSelected(date: Date) {
        Toast.makeText(requireContext(), "Tanggal dipilih: ${date}", Toast.LENGTH_SHORT).show()
    }

    private fun setDaysOfWeekHeader(calendar: Calendar) {
        val daysOfWeek = arrayOf("Min", "Sen", "Sel", "Rab", "Kam", "Jum", "Sab")
        val firstDayOfWeek = calendar.firstDayOfWeek
        val adjustedDaysOfWeek = daysOfWeek.slice(firstDayOfWeek - 1 until daysOfWeek.size) +
                daysOfWeek.slice(0 until firstDayOfWeek - 1)

        val daysOfWeekHeader = view?.findViewById<GridLayout>(R.id.daysOfWeekHeader)
        daysOfWeekHeader?.removeAllViews()

        adjustedDaysOfWeek.forEach { day ->
            val dayTextView = TextView(requireContext()).apply {
                layoutParams = GridLayout.LayoutParams().apply {
                    width = 0
                    height = ViewGroup.LayoutParams.WRAP_CONTENT
                    columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                }
                gravity = Gravity.CENTER
                setPadding(8, 16, 8, 16)
                text = day
            }
            daysOfWeekHeader?.addView(dayTextView)
        }
    }
}