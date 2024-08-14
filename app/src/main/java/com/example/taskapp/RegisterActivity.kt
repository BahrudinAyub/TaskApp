package com.example.taskapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.taskapp.databinding.ActivityRegisterBinding
import com.google.android.material.textfield.TextInputEditText
import android.text.method.PasswordTransformationMethod
import android.text.method.HideReturnsTransformationMethod

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private var isPasswordVisible: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initial state of register button
        updateRegisterButtonState()

        // Set TextWatcher to listen for text changes
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                updateRegisterButtonState()
            }
        }

        binding.nameInputEditText.addTextChangedListener(textWatcher)
        binding.emailInputEditText.addTextChangedListener(textWatcher)
        binding.passwordInputEditText.addTextChangedListener(textWatcher)

        // Set focus change listener to change button color and hide hint text when focused
        val focusChangeListener = { view: View, hasFocus: Boolean ->
            if (hasFocus) {
                binding.titleRegister.visibility = View.INVISIBLE
                binding.registerButton.setBackgroundColor(ContextCompat.getColor(this, R.color.red_button))
            } else {
                updateRegisterButtonState()
            }
        }

        binding.nameInputEditText.setOnFocusChangeListener(focusChangeListener)
        binding.emailInputEditText.setOnFocusChangeListener(focusChangeListener)
        binding.passwordInputEditText.setOnFocusChangeListener(focusChangeListener)

        // Set click listener for password visibility toggle
        binding.passwordInputEditText.setOnTouchListener { v, event ->
            val DRAWABLE_END = 2
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (binding.passwordInputEditText.right - binding.passwordInputEditText.compoundDrawables[DRAWABLE_END].bounds.width())) {
                    togglePasswordVisibility()
                    v.performClick() // Ensure performClick() is called
                    return@setOnTouchListener true
                }
            }
            false
        }

        // Set click listener for register button
        binding.registerButton.setOnClickListener {
            openLoginScreen()
        }
    }

    private fun updateRegisterButtonState() {
        val isNameFilled = binding.nameInputEditText.text.toString().isNotEmpty()
        val isEmailFilled = binding.emailInputEditText.text.toString().isNotEmpty()
        val isPasswordFilled = binding.passwordInputEditText.text.toString().isNotEmpty()

        binding.registerButton.isEnabled = isNameFilled && isEmailFilled && isPasswordFilled

        if (binding.registerButton.isEnabled) {
            binding.registerButton.setBackgroundColor(ContextCompat.getColor(this, R.color.red_button))
        } else {
            binding.registerButton.setBackgroundColor(Color.GRAY)
        }
        binding.loginLink.setOnClickListener {
            val intent = Intent(this, LoginScreenActivity::class.java)
            startActivity(intent)
        }

    }

    private fun togglePasswordVisibility() {
        if (isPasswordVisible) {
            binding.passwordInputEditText.transformationMethod = PasswordTransformationMethod.getInstance()
            binding.passwordInputEditText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock, 0, R.drawable.ic_visibility_off, 0)
        } else {
            binding.passwordInputEditText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            binding.passwordInputEditText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock, 0, R.drawable.ic_visibility, 0)
        }
        isPasswordVisible = !isPasswordVisible
        binding.passwordInputEditText.setSelection(binding.passwordInputEditText.text?.length ?: 0)
    }

    private fun openLoginScreen() {
        val intent = Intent(this, LoginScreenActivity::class.java)
        startActivity(intent)
    }


}
