package com.example.taskapp

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(private val context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("TargetData", Context.MODE_PRIVATE)

    // Method to save the target data
    fun saveTargetData(namaTarget: String, deskripsi: String, tanggalSelesai: String) {
        val editor = sharedPreferences.edit()
        editor.putString("namaTarget", namaTarget)
        editor.putString("deskripsi", deskripsi)
        editor.putString("tanggalSelesai", tanggalSelesai)
        editor.apply()
    }

    // Method to retrieve the target data
    fun getTargetData(): Target {
        val namaTarget = sharedPreferences.getString("namaTarget", "Pergi Liburan") ?: "Pergi Liburan"
        val deskripsi = sharedPreferences.getString("deskripsi", "Rencana liburan jangka panjang") ?: "Rencana liburan jangka panjang"
        val tanggalSelesai = sharedPreferences.getString("tanggalSelesai", "15 Okt 2024") ?: "15 Okt 2024"
        return Target(namaTarget, deskripsi, tanggalSelesai)
    }
}
