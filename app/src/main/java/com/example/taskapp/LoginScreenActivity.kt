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
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginScreenActivity : AppCompatActivity() {

    private lateinit var emailInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var loginButton: MaterialButton
    private lateinit var titleLogin: TextView
    private lateinit var passwordLayout: TextInputLayout
    private lateinit var forgotPasswordTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // Initialize views
        emailInput = findViewById(R.id.emailInputEditText)
        passwordInput = findViewById(R.id.passwordInputEditText)
        loginButton = findViewById(R.id.login_button)
        titleLogin = findViewById(R.id.title_login)
        passwordLayout = findViewById(R.id.textInput_password_login)
        forgotPasswordTextView = findViewById(R.id.textView_forgot_password)

        // Set focus change listeners
        emailInput.setOnFocusChangeListener { _, hasFocus ->
            handleFieldFocusChange(hasFocus)
        }
        passwordInput.setOnFocusChangeListener { _, hasFocus ->
            handleFieldFocusChange(hasFocus)
        }

        // Set text change listeners
        emailInput.addTextChangedListener(textWatcher)
        passwordInput.addTextChangedListener(textWatcher)

        // Set login button click listener to show popup
        loginButton.setOnClickListener {
            showPopup()
        }

        // Set forgot password text view click listener
        forgotPasswordTextView.setOnClickListener {
            val intent = Intent(this, ForgotPasswordScreenActivity::class.java)
            startActivity(intent)
        }
    }

    private fun handleFieldFocusChange(hasFocus: Boolean) {
        if (hasFocus) {
            // Change button color to red and hide title when any field is focused
            loginButton.setBackgroundColor(ContextCompat.getColor(this, R.color.red_button))
            titleLogin.visibility = View.GONE
        } else {
            // Reset button color and show title if no field is focused
            loginButton.setBackgroundColor(ContextCompat.getColor(this, R.color.gray_button))
            if (emailInput.text.isNullOrEmpty() && passwordInput.text.isNullOrEmpty()) {
                titleLogin.visibility = View.VISIBLE
            }
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // Ensure the button color is updated if text is present
            if (emailInput.text!!.isNotEmpty() || passwordInput.text!!.isNotEmpty()) {
                loginButton.setBackgroundColor(ContextCompat.getColor(this@LoginScreenActivity, R.color.red_button))
            } else {
                loginButton.setBackgroundColor(ContextCompat.getColor(this@LoginScreenActivity, R.color.gray_button))
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
            // Handle Cancel button click
            alertDialog.dismiss()
        }
    }
}
