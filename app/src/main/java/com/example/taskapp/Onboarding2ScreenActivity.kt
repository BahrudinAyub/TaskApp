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
import com.example.taskapp.databinding.ActivityOnboarding2Binding

class Onboarding2ScreenActivity : ComponentActivity() {
    private lateinit var binding: ActivityOnboarding2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnboarding2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set OnClickListener pada button pertama
        binding.cardviewButton1Onboarding2screen.setOnClickListener {
            val intent = Intent(this, Onboarding3ScreenActivity::class.java)
            startActivity(intent)
        }

        // Set OnClickListener pada button kedua (Lewati)
        binding.textbuttonButton2Onboarding2screen.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Inisialisasi TextView dan mengubah warna kata "Optimalkan"
        val textView = binding.TextViewOnboarding2screen
        val text = "Optimalkan produktivitasmu dengan pengolaan tugas yang efektif!"
        val spannableString = SpannableString(text)

        // Mencari posisi kata "Optimalkan"
        val start = text.indexOf("Optimalkan")
        val end = start + "Optimalkan".length

        // Mengambil warna dari resource
        val redButtonColor = ContextCompat.getColor(this, R.color.red_button)

        // Mengatur warna merah pada kata "Optimalkan"
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
