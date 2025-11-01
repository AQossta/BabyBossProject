package com.example.babybossandroidapp.presentation.add_child

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.babybossandroidapp.databinding.FragmentAddAChildBinding

class AddAChildFragment : Fragment() {

    private lateinit var binding: FragmentAddAChildBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddAChildBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}