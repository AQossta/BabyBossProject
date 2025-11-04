package com.example.babybossandroidapp.presentation.add_photo_child

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.babybossandroidapp.R
import com.example.babybossandroidapp.databinding.FragmentAddPhotoChildBinding

class AddPhotoChildFragment : Fragment() {

    private var _binding: FragmentAddPhotoChildBinding? = null
    private val binding get() = _binding!!
    private var selectedImageUri: Uri? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddPhotoChildBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        updatePhotoState()
    }

    private fun setupClickListeners() {
        binding.imgAddChild.setOnClickListener {
            selectImage()
        }

        binding.btnNextAddChildPhoto.setOnClickListener {
            validateAndProceed()
        }
    }

    private fun updatePhotoState() {
        if (selectedImageUri == null) {
            binding.imgAddChild.setImageResource(R.drawable.ic_photo_child)
            binding.changePhotoText.text = "ДОБАВИТЬ ФОТОГРАФИЮ"
            binding.changePhotoText.visibility = View.VISIBLE
        } else {
            binding.changePhotoText.text = "ИЗМЕНИТЬ ФОТОГРАФИЮ"
            binding.changePhotoText.visibility = View.VISIBLE
        }
    }

    private fun selectImage() {
        val options = arrayOf("Камера", "Галерея")
        AlertDialog.Builder(requireContext())
            .setTitle("Выберите источник")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> openCamera()
                    1 -> openGallery()
                }
            }
            .show()
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_CAMERA)
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CAMERA -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    binding.imgAddChild.setImageBitmap(imageBitmap)
                    selectedImageUri = saveBitmapToFile(imageBitmap)
                }
                REQUEST_GALLERY -> {
                    selectedImageUri = data?.data
                    binding.imgAddChild.setImageURI(selectedImageUri)
                }
            }
            updatePhotoState()
        }
    }

    private fun saveBitmapToFile(bitmap: Bitmap): Uri? {
        // Логика сохранения bitmap в файл и возврата URI
        return null
    }

    private fun validateAndProceed() {
        if (selectedImageUri == null) {
            Toast.makeText(requireContext(), "Добавьте фотографию", Toast.LENGTH_SHORT).show()
            return
        }

        findNavController().navigate(R.id.action_addPhotoChildFragment_to_tariffFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val REQUEST_CAMERA = 1001
        private const val REQUEST_GALLERY = 1002
    }
}