package com.example.babybossandroidapp.presentation.registration_data

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.babybossandroidapp.R
import com.example.babybossandroidapp.databinding.FragmentRegistrationDataBinding

class RegistrationDataFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationDataBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationDataBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}