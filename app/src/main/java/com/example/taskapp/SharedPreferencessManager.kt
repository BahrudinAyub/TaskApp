package com.example.taskapp

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(private val context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("TargetData", Context.MODE_PRIVATE)

    // Method to save the list of target data
    fun saveTargetList(targetList: List<Target>) {
        val stringBuilder = StringBuilder()
        targetList.forEach { target ->
            val targetString = "${target.namaTarget}||${target.deskripsi}||${target.tanggalSelesai}"
            stringBuilder.append(targetString).append(";;")
        }
        val editor = sharedPreferences.edit()
        editor.putString("targetList", stringBuilder.toString())
        editor.apply()
    }

    // Method to retrieve the list of target data
    fun getTargetList(): List<Target> {
        val savedString = sharedPreferences.getString("targetList", null) ?: return emptyList()
        return savedString.split(";;").filter { it.isNotEmpty() }.map { targetString ->
            val parts = targetString.split("||")
            Target(
                namaTarget = parts.getOrElse(0) { "" },
                deskripsi = parts.getOrElse(1) { "" },
                tanggalSelesai = parts.getOrElse(2) { "" }
            )
        }
    }

    // Method to add a single target to the list
    fun addTarget(target: Target) {
        val currentList = getTargetList().toMutableList()
        currentList.add(target)
        saveTargetList(currentList)
    }
}
