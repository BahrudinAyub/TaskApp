package com.example.taskapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
