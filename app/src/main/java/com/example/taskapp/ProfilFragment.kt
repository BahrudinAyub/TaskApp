package com.example.taskapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.taskapp.databinding.FragmentProfileBinding

class ProfilFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        // Set a click listener for the Edit button
        binding.btnEdit.setOnClickListener {
            val intent = Intent(activity, EditProfileActivity::class.java)
            startActivityForResult(intent, EDIT_PROFILE_REQUEST_CODE)
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == EDIT_PROFILE_REQUEST_CODE) {
            // Update the profile details
            val name = data?.getStringExtra("name")
            val phone = data?.getStringExtra("phone")
            val imageUri = data?.getStringExtra("imageUri")

            binding.profileName.text = name
            binding.profilePhone.text = phone

            imageUri?.let {
                binding.profileImage.setImageURI(Uri.parse(it))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val EDIT_PROFILE_REQUEST_CODE = 101
    }
}
