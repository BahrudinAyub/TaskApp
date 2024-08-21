package com.example.taskapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.taskapp.databinding.FragmentBagikanBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BagikanBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBagikanBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBagikanBottomSheetBinding.inflate(inflater, container, false)

        val originalColor = resources.getColor(R.color.white)
        val selectedColor = resources.getColor(R.color.background_bagikan)

        // Set OnClickListener untuk tombol kembali
        binding.backButton.setOnClickListener {
            dismiss()  // Tutup BottomSheet
        }

        // Set OnClickListener untuk text_view_publik
        binding.textViewPublik.setOnClickListener {
            resetColors()
            binding.textViewPublik.setBackgroundColor(selectedColor)
        }

        // Set OnClickListener untuk text_view_pribadi
        binding.textViewPribadi.setOnClickListener {
            resetColors()
            binding.textViewPribadi.setBackgroundColor(selectedColor)
        }

        // Set OnClickListener untuk text_view_link
        binding.textViewLink.setOnClickListener {
            resetColors()
            binding.textViewLink.setBackgroundColor(selectedColor)
        }

        return binding.root
    }

    // Fungsi untuk mereset warna background TextView ke warna asli
    private fun resetColors() {
        binding.textViewPublik.setBackgroundColor(resources.getColor(R.color.white))
        binding.textViewPribadi.setBackgroundColor(resources.getColor(R.color.white))
        binding.textViewLink.setBackgroundColor(resources.getColor(R.color.white))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
