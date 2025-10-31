package com.example.babybossandroidapp.presentation.registration

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.babybossandroidapp.R
import com.example.babybossandroidapp.databinding.FragmentRegistrationBinding
import com.google.i18n.phonenumbers.PhoneNumberUtil

class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
    private lateinit var phoneUtil: PhoneNumberUtil

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        phoneUtil = PhoneNumberUtil.getInstance()
        setupPhoneNumberInput()
        setupNextButton()
        updateNextButtonState(false)
    }

    private fun setupPhoneNumberInput() {
        binding.editTextNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val text = s.toString()
                val hasText = text.isNotEmpty()
                updateNextButtonState(hasText)

                if (hasText) {
                    binding.editTextNumber.error = null
                }
            }
        })
    }

    private fun validatePhoneNumber(phoneNumber: String) {
        val isPhoneValid = isKazakhstanPhoneNumberValid(phoneNumber)
        // проверка для навигации
    }

    private fun isKazakhstanPhoneNumberValid(phoneNumber: String): Boolean {
        return try {
            val phoneNumberProto = phoneUtil.parse(phoneNumber, "KZ")
            phoneUtil.isValidNumber(phoneNumberProto)
        } catch (e: Exception) {
            false
        }
    }

//    private fun updateNextButtonState(isEnabled: Boolean) {
//        binding.btnRegister.isEnabled = isEnabled
//
//        if (isEnabled) {
//            binding.btnRegister.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
//            binding.btnRegister.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_button_24dp_action)
//        } else {
//            binding.btnRegister.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey_text_button))
//            binding.btnRegister.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_button_24dp_action)
//        }
//    }

    private fun updateNextButtonState(isEnabled: Boolean) {
        binding.btnRegister.isEnabled = isEnabled
        binding.btnRegister.backgroundTintList = ContextCompat.getColorStateList(
            requireContext(),
            if (isEnabled) R.color.secondary_background else R.color.grey
        )
    }

    private fun setupNextButton() {
        binding.btnRegister.setOnClickListener {
            val phoneNumber = binding.editTextNumber.text.toString()

            if (isKazakhstanPhoneNumberValid(phoneNumber)) {
                navigateToCodeFragment(phoneNumber)
            } else {
                binding.editTextNumber.error = "Введите корректный номер телефона"
            }
        }
    }

    private fun navigateToCodeFragment(phoneNumber: String) {
        val action = RegistrationFragmentDirections.actionRegistrationFragmentToRegistrationCodeFragment(phoneNumber)
        findNavController().navigate(action)
    }
}