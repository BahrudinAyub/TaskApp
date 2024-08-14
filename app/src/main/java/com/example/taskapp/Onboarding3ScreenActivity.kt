package com.example.taskapp

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.taskapp.databinding.ActivityOnboarding3Binding

class Onboarding3ScreenActivity : ComponentActivity() {

    private lateinit var binding: ActivityOnboarding3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the binding
        binding = ActivityOnboarding3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set OnClickListener pada button pertama
        binding.cardviewButton1Onboarding3screen.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Set OnClickListener pada button kedua (Lewati)
        binding.textbuttonButton2Onboarding3screen.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Inisialisasi TextView dan mengubah warna kata "SiNote!"
        val textView = binding.TextViewOnboarding3screen
        val text = "Mudahnya mengelola tugas-tugasmu melalui SiNote!"
        val spannableString = SpannableString(text)

        // Mencari posisi kata "SiNote!"
        val start = text.indexOf("SiNote!")
        val end = start + "SiNote!".length

        // Mengambil warna dari resource
        val redButtonColor = ContextCompat.getColor(this, R.color.red_button)

        // Mengatur warna merah pada kata "SiNote!"
        spannableString.setSpan(ForegroundColorSpan(redButtonColor), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Mengatur teks yang diubah pada TextView
        textView.text = spannableString

        // Apply window insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
