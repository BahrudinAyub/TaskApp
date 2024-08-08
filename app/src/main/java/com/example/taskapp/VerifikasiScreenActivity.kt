package com.example.taskapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import com.example.taskapp.databinding.ActivityVerifikasiBinding

class VerifikasiScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerifikasiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityVerifikasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupTextFields()
        setupButtonClickListener()
    }

    private fun setupTextFields() {
        val otpFields = listOf(
            binding.otpInputLayout1,
            binding.otpInputLayout2,
            binding.otpInputLayout3,
            binding.otpInputLayout4
        )

        for (i in otpFields.indices) {
            val currentField = otpFields[i]
            val nextField = otpFields.getOrNull(i + 1)

            currentField.editText?.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    // Mengubah border menjadi merah
                    currentField.boxStrokeColor = ContextCompat.getColor(this, R.color.red_button)

                    // Mengatur margin atas tombol verifikasi jika keyboard muncul
                    adjustButtonPosition()
                } else {
                    // Mengembalikan border ke warna semula ketika kehilangan fokus
                    currentField.boxStrokeColor = ContextCompat.getColor(this, R.color.gray_button)
                }
            }

            currentField.editText?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s?.length == 1) {
                        // Pindah fokus ke textfield berikutnya
                        nextField?.editText?.requestFocus()
                    }
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }
    }

    private fun setupButtonClickListener() {
        binding.materialButtonVerifikasi.setOnClickListener {
            // Mengarahkan ke NewPasswordScreenActivity
            val intent = Intent(this, NewPasswordScreenActivity::class.java)
            startActivity(intent)
        }
    }

    private fun adjustButtonPosition() {
        // Mendapatkan height dari keyboard
        val rootView = findViewById<View>(android.R.id.content)
        rootView.post {
            val rect = android.graphics.Rect()
            rootView.getWindowVisibleDisplayFrame(rect)
            val screenHeight = rootView.height
            val keypadHeight = screenHeight - rect.bottom

            // Menyesuaikan margin atas tombol verifikasi
            val newMarginTop = if (keypadHeight > 0) {
                200 // Margin atas yang diatur ketika keyboard muncul
            } else {
                400 // Margin atas default
            }

            binding.materialButtonVerifikasi.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                topMargin = newMarginTop
            }
        }
    }
}
