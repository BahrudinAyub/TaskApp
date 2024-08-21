package com.example.taskapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.taskapp.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomSheetBinding.inflate(inflater, container, false)

        // Mengatur klik listener untuk linear_bagikan
        binding.linearBagikan.setOnClickListener {
            val bagikanBottomSheet = BagikanBottomSheetFragment()
            bagikanBottomSheet.show(parentFragmentManager, bagikanBottomSheet.tag)
        }

        // Mengatur klik listener untuk linear_atur_pengingat
        binding.linearAturPengingat.setOnClickListener {
            val pengingatBottomSheet = PengingatBottomSheetFragment()
            pengingatBottomSheet.show(parentFragmentManager, pengingatBottomSheet.tag)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
