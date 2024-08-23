package com.example.taskapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.taskapp.databinding.FragmentLabelBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LabelBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentLabelBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLabelBottomSheetBinding.inflate(inflater, container, false)

        // Mengatur klik listener untuk tombol close
        binding.closeButton.setOnClickListener {
            dismiss() // Menutup BottomSheet
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
