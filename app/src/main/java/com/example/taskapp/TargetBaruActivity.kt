package com.example.taskapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class TargetBaruActivity : AppCompatActivity() {

    private lateinit var switchTanggalSelesai: Switch
    private lateinit var textTanggalSelesai: TextView
    private lateinit var inputJumlahHari: EditText
    private lateinit var namaTargetInput: TextInputEditText
    private lateinit var deskripsiInput: TextInputEditText
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_target_baru)

        // Initialize views
        switchTanggalSelesai = findViewById(R.id.switch_tanggal_selesai)
        textTanggalSelesai = findViewById(R.id.text_tanggal_selesai)
        inputJumlahHari = findViewById(R.id.input_jumlah_hari)
        namaTargetInput = findViewById(R.id.nama_target)
        deskripsiInput = findViewById(R.id.deskripsi_opsional)

        // Initialize SharedPreferencesManager
        sharedPreferencesManager = SharedPreferencesManager(this)

        // Set default visibility of Tanggal Selesai and Jumlah Hari
        textTanggalSelesai.visibility = View.GONE
        inputJumlahHari.visibility = View.GONE

        // Listener for Switch Tanggal Selesai
        switchTanggalSelesai.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                textTanggalSelesai.visibility = View.VISIBLE
                inputJumlahHari.visibility = View.VISIBLE
                updateEndDate(45)
            } else {
                textTanggalSelesai.visibility = View.GONE
                inputJumlahHari.visibility = View.GONE
            }
        }

        // Listener for changes to Input Jumlah Hari
        inputJumlahHari.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val days = s?.toString()?.toIntOrNull() ?: 0
                updateEndDate(days)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Save data when "Selesai" button is pressed
        findViewById<TextView>(R.id.button_selesai).setOnClickListener {
            saveTargetData()
            navigateToTargetFragment()
        }
    }

    private fun updateEndDate(days: Int) {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val currentDate = Calendar.getInstance()
        currentDate.add(Calendar.DAY_OF_MONTH, days)
        val endDate = currentDate.time
        textTanggalSelesai.text = sdf.format(endDate)
    }

    private fun saveTargetData() {
        val namaTarget = namaTargetInput.text.toString()
        val deskripsi = deskripsiInput.text.toString()
        val tanggalSelesai = textTanggalSelesai.text.toString()

        sharedPreferencesManager.saveTargetData(namaTarget, deskripsi, tanggalSelesai)
    }

    private fun navigateToTargetFragment() {
        // Navigate to the fragment where the saved data is displayed
        val intent = Intent(this, TargetFragment::class.java)
        startActivity(intent)
    }
}
