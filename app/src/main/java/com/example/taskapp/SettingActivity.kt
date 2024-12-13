package com.example.taskapp

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_setting)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Menangani klik pada Pengaturan Tugas
    fun openTaskSettings(view: android.view.View) {
        val intent = Intent(this, TaskSettingActivity::class.java)
        startActivity(intent)
    }
    fun openNotificationSettings(view: android.view.View) {
        // Intent untuk membuka halaman pengaturan notifikasi
        val intent = Intent(this, NotificationSettingActivity::class.java)
        startActivity(intent)
    }

    fun openLanguageDialog(view: android.view.View) {
        // Pilihan bahasa
        val languages = arrayOf(
            "Bahasa Indonesia", "English", "Français", "Deutsch",
            "Italiano", "Español", "中文", "日本語"
        )

        // Buat dialog kustom
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_language_selection)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        // Atur judul
        val title = dialog.findViewById<TextView>(R.id.tv_title)
        title.text = "Bahasa"

        // Atur ListView
        val listView = dialog.findViewById<ListView>(R.id.lv_languages)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, languages)
        listView.adapter = adapter

        // Tangani klik pada item bahasa
        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedLanguage = languages[position]
            Toast.makeText(this, "Bahasa dipilih: $selectedLanguage", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        // Tangani klik tombol Cancel
        val cancel = dialog.findViewById<TextView>(R.id.tv_cancel)
        cancel.setOnClickListener { dialog.dismiss() }

        // Tampilkan dialog
        dialog.show()
    }

}
