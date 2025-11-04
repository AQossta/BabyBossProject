package com.example.babybossandroidapp.presentation.addi_child_data

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.babybossandroidapp.R
import com.example.babybossandroidapp.databinding.FragmentAddingAChildDataBinding

class AddingAChildDataFragment : Fragment() {

    private lateinit var binding: FragmentAddingAChildDataBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddingAChildDataBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigateToPhotoChild()
    }

    private fun navigateToPhotoChild() {
        binding.btnAddAChildData.setOnClickListener{
            findNavController().navigate(R.id.action_addingAChildDataFragment_to_addPhotoChildFragment)
        }
    }
}