package com.example.taskapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup

class TargetFragment : Fragment() {

    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_target, container, false)
        val addTargetButton = view.findViewById<FrameLayout>(R.id.fl_add_target)
        addTargetButton.setOnClickListener {
            showAddTargetDialog() // Fungsi untuk menampilkan dialog
        }

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

    private fun showAddTargetDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_target, null)
        val dialogBuilder = AlertDialog.Builder(requireContext())
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
                        btnBerlangsung.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))
                        btnBerlangsung.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                        btnSelesai.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                        btnSelesai.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                    }
                }
                R.id.btn_selesai -> {
                    if (isChecked) {
                        btnSelesai.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))
                        btnSelesai.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                        btnBerlangsung.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                        btnBerlangsung.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
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
