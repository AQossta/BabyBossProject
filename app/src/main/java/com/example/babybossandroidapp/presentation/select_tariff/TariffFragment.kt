package com.example.babybossandroidapp.presentation.select_tariff

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.babybossandroidapp.R
import com.example.babybossandroidapp.databinding.FragmentTariffBinding

class TariffFragment : Fragment() {

    private lateinit var binding: FragmentTariffBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTariffBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}