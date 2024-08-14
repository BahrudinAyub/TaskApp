package com.example.taskapp

import android.content.Intent

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.taskapp.databinding.ActivityOnboarding1Binding

class Onboarding1ScreanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboarding1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityOnboarding1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi tombol pertama
        val button1 = binding.cardviewButton1Onboarding1screen

        // Set OnClickListener pada button pertama
        button1.setOnClickListener {
            val intent = Intent(this, Onboarding2ScreenActivity::class.java)
            startActivity(intent)
        }

        // Inisialisasi tombol kedua
        val button2 = binding.textbuttonButton2Onboarding1screen

        // Set OnClickListener pada button kedua
        button2.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Inisialisasi TextView dan mengubah warna kata "Atur"
        val textView = binding.TextViewOnboading1screen
        val text = "Atur tugas dan catatanmu dengan mudah disini"
        val spannableString = SpannableString(text)

        // Mencari posisi kata "Atur"
        val start = text.indexOf("Atur")
        val end = start + "Atur".length

        // Mengambil warna dari resource
        val redButtonColor = ContextCompat.getColor(this, R.color.red_button)

        // Mengatur warna merah pada kata "Atur"
        spannableString.setSpan(ForegroundColorSpan(redButtonColor), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Mengatur teks yang diubah pada TextView
        textView.text = spannableString

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
