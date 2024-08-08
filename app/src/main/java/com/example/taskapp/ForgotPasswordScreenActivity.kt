package com.example.taskapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ForgotPasswordScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forgot_password)

        val emailInputEditText = findViewById<TextInputEditText>(R.id.emailInputEditText)
        val materialButton = findViewById<MaterialButton>(R.id.material_button)

        // Set the initial color of the button
        materialButton.setBackgroundColor(getColor(R.color.gray_button))

        emailInputEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // Change button color when the TextInputEditText is focused
                materialButton.setBackgroundColor(getColor(R.color.red_button))
            } else {
                // Optionally reset button color when focus is lost
                materialButton.setBackgroundColor(getColor(R.color.gray_button))
            }
        }

        // Set click listener for the button
        materialButton.setOnClickListener {
            // Create an intent to start VerifikasiScreenActivity
            val intent = Intent(this, VerifikasiScreenActivity::class.java)
            startActivity(intent)
        }
    }
}
