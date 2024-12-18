package com.example.taskapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.textfield.TextInputEditText

class ProjectDetailActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var tvProgressPercentage: TextView
    private lateinit var checkBoxes: List<CheckBox>
    private var totalTasks = 5
    private lateinit var addTeamIcon: ImageView
    private lateinit var tvProjectName: TextView
    private lateinit var tvProjectDescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_detail)

        // Inisialisasi Views
        tvProjectName = findViewById(R.id.tvProjectName)
        tvProjectDescription = findViewById(R.id.tvDescription)
        addTeamIcon = findViewById(R.id.addTeamIcon)
        progressBar = findViewById(R.id.progressBar)
        tvProgressPercentage = findViewById(R.id.tvProgressLabel)


        // Terima data dari Intent
        val projectName = intent.getStringExtra("project_name") ?: "Nama proyek tidak ditemukan"
        val projectDescription =
            intent.getStringExtra("project_description") ?: "Deskripsi tidak tersedia"

        // Set data ke TextView
        tvProjectName.text = projectName
        tvProjectDescription.text = projectDescription

        val btnAddTask: ImageView = findViewById(R.id.btnAddTask)
        btnAddTask.setOnClickListener {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_target, null)
            val builder = AlertDialog.Builder(this).setView(dialogView)
            val dialog = builder.create()

            // Inisialisasi Views dari dialog
            val etNamaTarget = dialogView.findViewById<TextInputEditText>(R.id.et_nama_task)
            val rbIntervalPengukuran = dialogView.findViewById<RadioButton>(R.id.rb_interval_pengukuran)
            val intervalLayout = dialogView.findViewById<LinearLayout>(R.id.intervalLayout)
            val rbSedangBerlangsung = dialogView.findViewById<RadioButton>(R.id.rb_sedang_berlangsung)
            val layoutSedangBerlangsung = dialogView.findViewById<LinearLayout>(R.id.layout_sedang_berlangsung)
            val rbMataUang = dialogView.findViewById<RadioButton>(R.id.rb_mata_uang)
            val layoutMataUang = dialogView.findViewById<LinearLayout>(R.id.layout_mata_uang)
            val tvSelesai = dialogView.findViewById<TextView>(R.id.tv_selesai)

            rbIntervalPengukuran.setOnCheckedChangeListener { _, isChecked ->
                intervalLayout.visibility = if (isChecked) LinearLayout.VISIBLE else LinearLayout.GONE
            }

            rbSedangBerlangsung.setOnCheckedChangeListener { _, isChecked ->
                layoutSedangBerlangsung.visibility = if (isChecked) LinearLayout.VISIBLE else LinearLayout.GONE
                if (isChecked) setupToggleButton(dialogView)
            }

            rbMataUang.setOnCheckedChangeListener { _, isChecked ->
                layoutMataUang.visibility = if (isChecked) LinearLayout.VISIBLE else LinearLayout.GONE
            }

            tvSelesai.setOnClickListener {
                val namaTarget = etNamaTarget.text.toString()
                Toast.makeText(this, "Target: $namaTarget disimpan", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }

            dialog.show()
        }
    }

    // Fungsi untuk mengatur warna tombol toggle
    private fun setupToggleButton(dialogView: android.view.View) {
        val toggleGroup = dialogView.findViewById<MaterialButtonToggleGroup>(R.id.layout_sedang_berlangsung)
        val btnBerlangsung = dialogView.findViewById<MaterialButton>(R.id.btn_berlangsung)
        val btnSelesai = dialogView.findViewById<MaterialButton>(R.id.btn_selesai)

        toggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.btn_berlangsung -> {
                        btnBerlangsung.setBackgroundColor(ContextCompat.getColor(this, R.color.blue))
                        btnBerlangsung.setTextColor(ContextCompat.getColor(this, R.color.white))
                        btnSelesai.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                        btnSelesai.setTextColor(ContextCompat.getColor(this, R.color.black))
                    }
                    R.id.btn_selesai -> {
                        btnSelesai.setBackgroundColor(ContextCompat.getColor(this, R.color.blue))
                        btnSelesai.setTextColor(ContextCompat.getColor(this, R.color.white))
                        btnBerlangsung.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                        btnBerlangsung.setTextColor(ContextCompat.getColor(this, R.color.black))
                    }
                }
            }
        }
    }
}
