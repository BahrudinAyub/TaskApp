package com.example.taskapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.example.taskapp.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var selectedImageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle back button click
        binding.backButton.setOnClickListener {
            onBackPressed() // Go back to the previous activity
        }

        // Handle "Selesai" button click
        binding.btnSelesai.setOnClickListener {
            // Pass data back to ProfilFragment
            val resultIntent = Intent().apply {
                putExtra("name", binding.editName.text.toString())
                putExtra("phone", binding.editPhone.text.toString())
                putExtra("imageUri", selectedImageUri.toString())
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish() // Close the activity and return
        }

        // Handle profile icon click to open gallery
        binding.editProfileIcon.setOnClickListener {
            openGallery()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == GALLERY_REQUEST_CODE) {
            data?.data?.let { uri ->
                selectedImageUri = uri
                binding.profileImageEdit.setImageURI(uri)
            }
        }
    }

    companion object {
        const val GALLERY_REQUEST_CODE = 100
    }
}
