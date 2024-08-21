package com.example.taskapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.example.taskapp.databinding.ActivityCatatanBinding
import androidx.fragment.app.DialogFragment

class CatatanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCatatanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatatanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set onClickListener untuk tombol kembali
        binding.btnBackCatatan.setOnClickListener {
            finish()  // Kembali ke activity sebelumnya
        }

        // Listener untuk perubahan teks di EditText
        binding.titleEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Tidak digunakan
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Tidak digunakan
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    // Jika EditText kosong, tidak menampilkan imageView
                    binding.iconTitle.visibility = android.view.View.GONE
                } else {
                    // Jika EditText tidak kosong, menampilklan Imageview
                    binding.iconTitle.visibility = android.view.View.VISIBLE
                }
            }
        })

        // Set onClickListener untuk CheckBox
        binding.checkBoxItem.setOnClickListener {
            binding.checkBoxItem.visibility = android.view.View.GONE
            binding.editTextReplacement.visibility = android.view.View.VISIBLE
            binding.editTextReplacement.requestFocus()  // Fokus pada EditText baru
        }
        binding.btnMoreOptions.setOnClickListener {
            val bottomSheetFragment = BottomSheetFragment()
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }
    }
}