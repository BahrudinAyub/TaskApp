package com.example.taskapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AnggotaTeam : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tabPartisipan: TextView
    private lateinit var tabUndang: TextView
    private lateinit var btnKeluarTim: Button
    private lateinit var btnSelesai: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anggota_team)

        recyclerView = findViewById(R.id.recyclerViewAnggota)
        tabPartisipan = findViewById(R.id.tabPartisipan)
        tabUndang = findViewById(R.id.tabUndang)
        btnKeluarTim = findViewById(R.id.btnKeluarTim)
        btnSelesai = findViewById(R.id.btnSelesai)


        // Inisialisasi LayoutManager untuk RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Load the initial data as Partisipan
        loadPartisipanData()

        // Handle tab switching
        tabPartisipan.setOnClickListener {
            loadPartisipanData()
        }

        tabUndang.setOnClickListener {
            loadUndangData()
        }
    }

    private fun loadPartisipanData() {
        // Set background of the tabs
        tabPartisipan.setBackgroundResource(R.drawable.rounded_selected)
        tabUndang.setBackgroundResource(R.drawable.rounded_unselected)

        // Show "Keluar dari Tim" button and hide "Selesai" button
        btnKeluarTim.visibility = View.VISIBLE
        btnSelesai.visibility = View.GONE

        // Load data for partisipan
        val partisipanList = listOf(
            AnggotaModel("Amy Vis", "811-2334-655", R.drawable.image_profile, "admin"),
            AnggotaModel("M Daffa", "812-2444-555", R.drawable.image_profile1, "Manager"),
            AnggotaModel("Jean Gunn", "821-2314-575", R.drawable.image_profile2, "Admin"),
            AnggotaModel("Fikri Maulana", "811-2134-667", R.drawable.image_profile3, "")
        )

        // Set adapter to RecyclerView
        recyclerView.adapter = PartisipanAdapter(partisipanList)
    }

    private fun loadUndangData() {
        // Set background of the tabs
        tabPartisipan.setBackgroundResource(R.drawable.rounded_unselected)
        tabUndang.setBackgroundResource(R.drawable.rounded_selected)

        // Show "Selesai" button and hide "Keluar dari Tim" button
        btnKeluarTim.visibility = View.GONE
        btnSelesai.visibility = View.VISIBLE

        // Load data for undang
        val undangList = listOf(
            AnggotaModel("Amy Vis", "811-2334-655", R.drawable.image_profile, ""),
            AnggotaModel("M Daffa", "812-2444-555", R.drawable.image_profile1, ""),
            AnggotaModel("Jean Gunn", "821-2314-575", R.drawable.image_profile3, ""),
            AnggotaModel("Fikri Maulana", "811-2134-667", R.drawable.image_profile2, "")
        )

        // Set adapter to RecyclerView
        recyclerView.adapter = UndangAdapter(undangList)
    }




}
