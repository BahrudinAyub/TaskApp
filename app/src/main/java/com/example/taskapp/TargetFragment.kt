package com.example.taskapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class TargetFragment : Fragment() {

    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_target, container, false)

        // Initialize SharedPreferencesManager
        sharedPreferencesManager = SharedPreferencesManager(requireContext())

        // Fetch the saved data
        val target = sharedPreferencesManager.getTargetData()

        // Update UI with saved data
        view.findViewById<TextView>(R.id.nama_target_text).text = target.namaTarget
        view.findViewById<TextView>(R.id.deskripsi_text).text = target.deskripsi
        view.findViewById<TextView>(R.id.tanggal_selesai_text).text = "Berakhir: ${target.tanggalSelesai}"

        return view
    }
}
