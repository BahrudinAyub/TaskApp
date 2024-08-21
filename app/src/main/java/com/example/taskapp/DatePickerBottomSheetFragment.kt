package com.example.taskapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.taskapp.databinding.FragmentDatePickerBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DatePickerBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentDatePickerBottomSheetBinding? = null
    private val binding get() = _binding!!

    var onDateSelected: ((day: Int, month: Int, year: Int) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDatePickerBottomSheetBinding.inflate(inflater, container, false)

        // Atur nilai untuk day_picker
        binding.dayPicker.minValue = 1
        binding.dayPicker.maxValue = 31

        // Atur nilai untuk month_picker
        val months = arrayOf("Jan", "Feb", "Mar", "Apr", "Mei", "Jun", "Jul", "Agu", "Sep", "Okt", "Nov", "Des")
        binding.monthPicker.minValue = 0
        binding.monthPicker.maxValue = months.size - 1
        binding.monthPicker.displayedValues = months

        // Atur nilai untuk year_picker
        binding.yearPicker.minValue = 2000
        binding.yearPicker.maxValue = 2100

        binding.root.findViewById<View>(R.id.confirm_button).setOnClickListener {
            onDateSelected?.invoke(
                binding.dayPicker.value,
                binding.monthPicker.value,
                binding.yearPicker.value
            )
            dismiss()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
