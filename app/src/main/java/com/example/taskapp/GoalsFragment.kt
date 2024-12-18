package com.example.taskapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GoalsFragment : Fragment() {

    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GoalsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_goals, container, false)

        // Inisialisasi RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Inisialisasi SharedPreferencesManager
        sharedPreferencesManager = SharedPreferencesManager(requireContext())

        // Ambil semua data dari SharedPreferences
        val goalsList = sharedPreferencesManager.getTargetList()

        // Tambahkan log untuk debugging
        Log.d("GoalsFragment", "Goals List: $goalsList")

        // Set adapter dengan data
        adapter = GoalsAdapter(goalsList) { selectedGoal ->
            // Panggil fungsi saat item diklik
            onRecyclerViewItemClicked(selectedGoal)
        }
        recyclerView.adapter = adapter

        return view
    }


    private fun onRecyclerViewItemClicked(goal: Target) {
        try {
            val intent = Intent(requireContext(), TargetActivity::class.java).apply {
                putExtra("NAMA_TARGET", goal.namaTarget)
                putExtra("DESKRIPSI", goal.deskripsi)
                putExtra("TANGGAL_SELESAI", goal.tanggalSelesai)
            }
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace() // Log error untuk debugging
        }
    }


}

