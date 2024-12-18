package com.example.taskapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup

class TargetActivity : AppCompatActivity() {

    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_target)

        try {
            // Ambil data dari Intent dengan nilai default jika null
            val namaTarget = intent.getStringExtra("NAMA_TARGET") ?: "Tidak Ada Nama Target"
            val deskripsi = intent.getStringExtra("DESKRIPSI") ?: "Tidak Ada Deskripsi"
            val tanggalSelesai = intent.getStringExtra("TANGGAL_SELESAI") ?: "Tidak Ada Tanggal"

            // Update UI dengan data
            findViewById<TextView>(R.id.nama_target_text).text = namaTarget
            findViewById<TextView>(R.id.deskripsi_text).text = deskripsi
            findViewById<TextView>(R.id.tanggal_selesai_text).text = "Berakhir: $tanggalSelesai"
        } catch (e: Exception) {
            e.printStackTrace() // Log error untuk debugging
        }
    }



    private fun showAddTargetDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_target, null)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)

        val dialog = dialogBuilder.create()
        dialog.show()

        // Referensi ke elemen-elemen UI
        val rbIntervalPengukuran = dialogView.findViewById<RadioButton>(R.id.rb_interval_pengukuran)
        val rbSedangBerlangsung = dialogView.findViewById<RadioButton>(R.id.rb_sedang_berlangsung)
        val rbMataUang = dialogView.findViewById<RadioButton>(R.id.rb_mata_uang)
        val intervalLayout = dialogView.findViewById<LinearLayout>(R.id.intervalLayout)
        val layoutSedangBerlangsung = dialogView.findViewById<LinearLayout>(R.id.layout_sedang_berlangsung)
        val layoutMataUang = dialogView.findViewById<LinearLayout>(R.id.layout_mata_uang)

        val toggleGroup = dialogView.findViewById<MaterialButtonToggleGroup>(R.id.layout_sedang_berlangsung)
        val btnBerlangsung = dialogView.findViewById<MaterialButton>(R.id.btn_berlangsung)
        val btnSelesai = dialogView.findViewById<MaterialButton>(R.id.btn_selesai)

        // Tambahkan referensi ke EditText
        val etMulai = dialogView.findViewById<EditText>(R.id.et_mulai_uang)
        val etTarget = dialogView.findViewById<EditText>(R.id.et_target_uang)

        toggleGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
            when (checkedId) {
                R.id.btn_berlangsung -> {
                    if (isChecked) {
                        btnBerlangsung.setBackgroundColor(ContextCompat.getColor(this, R.color.blue))
                        btnBerlangsung.setTextColor(ContextCompat.getColor(this, R.color.white))
                        btnSelesai.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                        btnSelesai.setTextColor(ContextCompat.getColor(this, R.color.black))
                    }
                }
                R.id.btn_selesai -> {
                    if (isChecked) {
                        btnSelesai.setBackgroundColor(ContextCompat.getColor(this, R.color.blue))
                        btnSelesai.setTextColor(ContextCompat.getColor(this, R.color.white))
                        btnBerlangsung.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                        btnBerlangsung.setTextColor(ContextCompat.getColor(this, R.color.black))
                    }
                }
            }
        }

        // Listener untuk Interval Pengukuran
        rbIntervalPengukuran.setOnClickListener {
            Log.d("DEBUG", "RadioButton Interval Pengukuran clicked")
            intervalLayout.visibility = View.VISIBLE // Tampilkan intervalLayout
            layoutSedangBerlangsung.visibility = View.GONE // Sembunyikan layout sedang berlangsung
            layoutMataUang.visibility = View.GONE // Sembunyikan layout mata uang
        }

        // Listener untuk Sedang Berlangsung/Selesai
        rbSedangBerlangsung.setOnClickListener {
            Log.d("DEBUG", "RadioButton Sedang Berlangsung clicked")
            intervalLayout.visibility = View.GONE // Sembunyikan intervalLayout
            layoutSedangBerlangsung.visibility = View.VISIBLE // Tampilkan layout sedang berlangsung
            layoutMataUang.visibility = View.GONE // Sembunyikan layout mata uang
        }

        // Listener untuk Mata Uang
        rbMataUang.setOnClickListener {
            Log.d("DEBUG", "RadioButton Mata Uang clicked")
            intervalLayout.visibility = View.GONE // Sembunyikan intervalLayout
            layoutSedangBerlangsung.visibility = View.GONE // Sembunyikan layout sedang berlangsung
            layoutMataUang.visibility = View.VISIBLE // Tampilkan layout mata uang
        }

        // Listener untuk tombol selesai
        dialogView.findViewById<TextView>(R.id.tv_selesai).setOnClickListener {
            val mulaiNominal = etMulai.text.toString()
            val targetNominal = etTarget.text.toString()

            Log.d("DEBUG", "Nominal Mulai: $mulaiNominal")
            Log.d("DEBUG", "Nominal Target: $targetNominal")

            // Dismiss dialog setelah nilai diambil
            dialog.dismiss()
        }
    }
}
