package com.example.taskapp

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import com.example.taskapp.databinding.FragmentTimePickerBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TimePickerBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentTimePickerBottomSheetBinding? = null
    private val binding get() = _binding!!

    var onTimeSelected: ((hour: Int, minute: Int) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTimePickerBottomSheetBinding.inflate(inflater, container, false)

        // Mengatur NumberPicker
        binding.hourPicker.minValue = 0
        binding.hourPicker.maxValue = 23

        binding.minutePicker.minValue = 0
        binding.minutePicker.maxValue = 59

        binding.confirmButton.setOnClickListener {
            onTimeSelected?.invoke(
                binding.hourPicker.value,
                binding.minutePicker.value
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
