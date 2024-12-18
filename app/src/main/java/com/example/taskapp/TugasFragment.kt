package com.example.taskapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout

class TugasFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var tugasAdapter: TaskAdapter
    private lateinit var sharedPreferencesManager: TugasBaruSharedPreferencesManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_tugas, container, false)

        // Setup SharedPreferencesManager
        sharedPreferencesManager = TugasBaruSharedPreferencesManager(requireContext())

        // Setup TabLayout dan RecyclerView
        tabLayout = view.findViewById(R.id.tabLayout)
        recyclerView = view.findViewById(R.id.recyclerView)

        setupTabs()
        setupRecyclerView()

        return view
    }

    private fun setupTabs() {
        tabLayout.addTab(tabLayout.newTab().setText("Tugas"))
        tabLayout.addTab(tabLayout.newTab().setText("Tugas Berulang"))
        tabLayout.addTab(tabLayout.newTab().setText("Kebiasaan"))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> tugasAdapter.updateList(sharedPreferencesManager.getTaskList()) // Menampilkan daftar tugas
                    1 -> tugasAdapter.updateList(getTugasBerulangList()) // Data Tugas Berulang
                    2 -> tugasAdapter.updateList(getKebiasaanList()) // Data Kebiasaan
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val tasks = sharedPreferencesManager.getTaskList() // Ambil data awal dari SharedPreferences
        tugasAdapter = TaskAdapter(tasks) { task ->
            // Hapus tugas ketika checkbox diaktifkan
            val updatedTasks = tasks.toMutableList().apply { remove(task) }
            sharedPreferencesManager.saveTaskList(updatedTasks) // Simpan daftar tugas yang diperbarui
            tugasAdapter.updateList(updatedTasks) // Refresh data di adapter
        }
        recyclerView.adapter = tugasAdapter
    }

    private fun getTugasBerulangList(): List<Task> {
        return listOf(
            Task("Membaca Buku", "High", "Sel - Kam", "08:00"),
            Task("Menyampaikan Progres", "Medium", "15, 28", "09:00"),
            Task("Olahraga", "Low", "Tiap Hari", "06:00")
        )
    }

    private fun getKebiasaanList(): List<Task> {
        return listOf(
            Task("Meditasi", "Medium", "Tiap Pagi", "07:00"),
            Task("Jurnal Harian", "Low", "Tiap Malam", "22:00")
        )
    }
}
