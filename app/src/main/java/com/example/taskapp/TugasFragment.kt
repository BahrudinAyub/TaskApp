package com.example.taskapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout

class TugasFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var tugasAdapter: TugasAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_tugas, container, false)

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
                    0 -> tugasAdapter.updateList(getTugasList())
                    1 -> tugasAdapter.updateList(getTugasBerulangList())
                    2 -> tugasAdapter.updateList(getKebiasaanList())
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        tugasAdapter = TugasAdapter(getTugasList()) // Menampilkan tugas default
        recyclerView.adapter = tugasAdapter
    }

    private fun getTugasList(): List<Tugas> {
        return listOf(
            Tugas("Membuat Dashboard UI", "20:00", true),
            Tugas("Mengerjakan Kuis", "07:00", true),
            Tugas("Mempelajari Wireframe", "18:00", true),
            Tugas("Membuat Resume", "09:00", false)
        )
    }

    private fun getTugasBerulangList(): List<Tugas> {
        return listOf(
            Tugas("Membaca Buku", "Sel - Kam", true),
            Tugas("Menyampaikan Progres", "15, 28", true),
            Tugas("Olahraga", "Tiap Hari", false)
        )
    }

    private fun getKebiasaanList(): List<Tugas> {
        return listOf(
            Tugas("Membaca Buku", "Sel - Kam", true)
        )
    }
}
