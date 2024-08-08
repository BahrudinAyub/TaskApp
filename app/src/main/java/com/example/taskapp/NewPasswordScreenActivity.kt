package com.example.taskapp

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import com.example.taskapp.databinding.ActivityNewPasswordBinding
import com.example.taskapp.databinding.ActivityPopupNewPasswordBinding

class NewPasswordScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set initial position of the checkbox to be on the left
        positionCheckBoxToLeft()

        binding.passwordInputEditTextNew1.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                hideImageView()
                showCheckBox()
            }
        }

        binding.passwordInputEditTextNew2.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                hideImageView()
                showCheckBox()
            }
        }

        // Ensure checkbox is always positioned to the left
        binding.checkboxRememberMe.setOnCheckedChangeListener { _, _ ->
            positionCheckBoxToLeft()
        }

        // Set button click listener to show popup
        binding.materialButtonNewPassword.setOnClickListener {
            showPopup()
        }
    }

    private fun hideImageView() {
        binding.imageViewNewPassword.visibility = View.GONE
    }

    private fun showCheckBox() {
        binding.checkboxRememberMe.visibility = View.VISIBLE
    }

    private fun positionCheckBoxToLeft() {
        val constraintSet = ConstraintSet()
        val constraintLayout = binding.root.findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.constraintLayout)
        constraintSet.clone(constraintLayout)

        constraintSet.clear(R.id.checkbox_remember_me, ConstraintSet.END)
        constraintSet.connect(R.id.checkbox_remember_me, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 40)
        constraintSet.connect(R.id.checkbox_remember_me, ConstraintSet.TOP, R.id.text_input_password_new2, ConstraintSet.BOTTOM, 15)

        constraintSet.applyTo(constraintLayout)
    }

    private fun showPopup() {
        // Inflate the custom layout/view for the dialog using ViewBinding
        val dialogBinding = ActivityPopupNewPasswordBinding.inflate(LayoutInflater.from(this))
        val dialogView = dialogBinding.root

        // Initialize the dialog builder
        val builder = AlertDialog.Builder(this)
            .setView(dialogView)

        // Create and show the dialog
        val alertDialog = builder.create()
        alertDialog.show()

        // Access and modify views within the dialog if needed
        val imageView = dialogBinding.popupImage
        val textView1 = dialogBinding.popupText1
        val textView2 = dialogBinding.popupText2

        // Set up any additional properties or listeners here
    }



}
