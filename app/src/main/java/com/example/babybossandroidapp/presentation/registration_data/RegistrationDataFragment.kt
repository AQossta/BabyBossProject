package com.example.babybossandroidapp.presentation.registration_data

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.babybossandroidapp.databinding.FragmentRegistrationDataBinding

class RegistrationDataFragment : Fragment() {

    private var _binding: FragmentRegistrationDataBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnNextData.setOnClickListener {
            validateAndProceed()
        }
    }

    private fun validateAndProceed() {
        val name = binding.nameEditText.text.toString().trim()
        val surname = binding.surnameEditText.text.toString().trim()

        if (name.isEmpty()) {
            binding.nameInputLayout.error = "Введите имя"
            return
        } else {
            binding.nameInputLayout.error = null
        }

        if (surname.isEmpty()) {
            binding.surnameInputLayout.error = "Введите фамилию"
            return
        } else {
            binding.surnameInputLayout.error = null
        }

        proceedToNextStep(name, surname)
    }

    private fun proceedToNextStep(name: String, surname: String) {
        Toast.makeText(requireContext(), "Имя: $name, Фамилия: $surname", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}