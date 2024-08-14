package com.example.taskapp

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.taskapp.databinding.ActivityLoginBinding

class LoginScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set focus change listeners
        binding.emailInputEditText.setOnFocusChangeListener { _, hasFocus ->
            handleFieldFocusChange(hasFocus)
        }
        binding.passwordInputEditText.setOnFocusChangeListener { _, hasFocus ->
            handleFieldFocusChange(hasFocus)
        }

        // Set text change listeners
        binding.emailInputEditText.addTextChangedListener(textWatcher)
        binding.passwordInputEditText.addTextChangedListener(textWatcher)

        // Set login button click listener to show popup
        binding.loginButton.setOnClickListener {
            showPopup()
        }

        // Set forgot password text view click listener
        binding.textViewForgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordScreenActivity::class.java)
            startActivity(intent)
        }
    }

    private fun handleFieldFocusChange(hasFocus: Boolean) {
        if (hasFocus) {
            // Change button color to red and hide title when any field is focused
            binding.loginButton.setBackgroundColor(ContextCompat.getColor(this, R.color.red_button))
            binding.titleLogin.visibility = View.GONE
        } else {
            // Reset button color and show title if no field is focused
            binding.loginButton.setBackgroundColor(ContextCompat.getColor(this, R.color.gray_button))
            if (binding.emailInputEditText.text.isNullOrEmpty() && binding.passwordInputEditText.text.isNullOrEmpty()) {
                binding.titleLogin.visibility = View.VISIBLE
            }
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // Ensure the button color is updated if text is present
            if (binding.emailInputEditText.text!!.isNotEmpty() || binding.passwordInputEditText.text!!.isNotEmpty()) {
                binding.loginButton.setBackgroundColor(ContextCompat.getColor(this@LoginScreenActivity, R.color.red_button))
            } else {
                binding.loginButton.setBackgroundColor(ContextCompat.getColor(this@LoginScreenActivity, R.color.gray_button))
            }
        }

        override fun afterTextChanged(s: Editable?) {
            // `s` is not used, so this method can be left empty or removed
        }
    }

    private fun showPopup() {
        // Inflate the custom layout/view for the dialog
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_popup, null)

        // Initialize the dialog builder
        val builder = AlertDialog.Builder(this)
            .setView(dialogView)

        // Create and show the dialog
        val alertDialog = builder.create()
        alertDialog.show()

        // Access and modify views within the dialog if needed
        val imageView: ImageView = dialogView.findViewById(R.id.popup_image)
        val textView1: TextView = dialogView.findViewById(R.id.popup_text1)
        val textView2: TextView = dialogView.findViewById(R.id.popup_text2)
        val btnPositive: Button = dialogView.findViewById(R.id.btn_positive)
        val btnNegative: Button = dialogView.findViewById(R.id.btn_negative)

        // Set up button listeners
        btnPositive.setOnClickListener {
            // Handle OK button click
            alertDialog.dismiss()
        }

        btnNegative.setOnClickListener {
            onSkipButtonClicked()
        }
    }

    private fun onSkipButtonClicked() {
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish()
    }

}
