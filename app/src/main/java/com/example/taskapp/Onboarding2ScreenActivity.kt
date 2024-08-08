package com.example.taskapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
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

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
