package com.example.taskapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.taskapp.databinding.FragmentPengingatBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PengingatBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentPengingatBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPengingatBottomSheetBinding.inflate(inflater, container, false)

        binding.linearTanggal.setOnClickListener {
            val datePickerBottomSheet = DatePickerBottomSheetFragment()
            datePickerBottomSheet.onDateSelected = { day, month, year ->
                val selectedDate = "$day ${getMonthName(month)} $year"
                binding.statusTanggal.text = selectedDate
            }
            datePickerBottomSheet.show(parentFragmentManager, datePickerBottomSheet.tag)
        }

        binding.linearWaktu.setOnClickListener {
            val timePickerBottomSheet = TimePickerBottomSheetFragment()
            timePickerBottomSheet.onTimeSelected = { hour, minute ->
                val selectedTime = String.format("%02d:%02d", hour, minute)
                binding.statusWaktu.text = selectedTime
            }
            timePickerBottomSheet.show(parentFragmentManager, timePickerBottomSheet.tag)
        }

        return binding.root
    }

    private fun getMonthName(month: Int): String {
        val months = arrayOf("Jan", "Feb", "Mar", "Apr", "Mei", "Jun", "Jul", "Agu", "Sep", "Okt", "Nov", "Des")
        return months[month]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
