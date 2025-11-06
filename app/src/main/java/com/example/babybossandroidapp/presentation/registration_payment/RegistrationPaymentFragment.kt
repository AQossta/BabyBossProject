package com.example.babybossandroidapp.presentation.registration_payment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.babybossandroidapp.R
import androidx.core.content.ContextCompat
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.babybossandroidapp.databinding.FragmentRegistrationPaymentBinding

class RegistrationPaymentFragment : Fragment() {

    private var _binding: FragmentRegistrationPaymentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTextWatchers()
        setupClickListeners()
        updateButtonState(false)
    }

    private fun setupTextWatchers() {
        binding.cardNumberEditText.addTextChangedListener(CardNumberTextWatcher())
        binding.validityPeriodEditText.addTextChangedListener(ValidityPeriodTextWatcher())
        binding.svvEditText.addTextChangedListener(SvvTextWatcher())

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                updateButtonState(isFormValid())
            }
        }

        binding.cardNumberEditText.addTextChangedListener(textWatcher)
        binding.validityPeriodEditText.addTextChangedListener(textWatcher)
        binding.svvEditText.addTextChangedListener(textWatcher)
    }

    private fun setupClickListeners() {
        binding.btnNextPayment.setOnClickListener {
            if (isFormValid()) {
                addCard()
            }
        }
    }

    private fun updateButtonState(isEnabled: Boolean) {
        binding.btnNextPayment.isEnabled = isEnabled
        binding.btnNextPayment.backgroundTintList = ContextCompat.getColorStateList(
            requireContext(),
            if (isEnabled) R.color.secondary_background else R.color.grey
        )
        binding.btnNextPayment.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                if (isEnabled) R.color.neutral_light else R.color.grey_text_button
            )
        )
    }

    private fun isFormValid(): Boolean {
        val cardNumber = binding.cardNumberEditText.text.toString().replace(" ", "")
        val validityPeriod = binding.validityPeriodEditText.text.toString()
        val svv = binding.svvEditText.text.toString()

        return cardNumber.length == 16 &&
                validityPeriod.length == 5 && validityPeriod.contains("/") &&
                svv.length == 3
    }

    private fun addCard() {
        val cardNumber = binding.cardNumberEditText.text.toString().replace(" ", "")
        val validityPeriod = binding.validityPeriodEditText.text.toString()
        val svv = binding.svvEditText.text.toString()

        Toast.makeText(requireContext(), "КАРТА УСПЕШНО ДОБАВЛЕНА", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_registrationPaymentFragment_to_addAChildFragment)
    }

    private inner class CardNumberTextWatcher : TextWatcher {
        private var isFormatting = false

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            if (isFormatting) return
            isFormatting = true

            val text = s.toString().replace(" ", "")
            if (text.length <= 16) {
                val formatted = StringBuilder()
                for (i in text.indices) {
                    if (i > 0 && i % 4 == 0) {
                        formatted.append(" ")
                    }
                    formatted.append(text[i])
                }
                s?.replace(0, s.length, formatted.toString())

                // Автопереход при полном вводе
                if (text.length == 16) {
                    binding.validityPeriodEditText.requestFocus()
                }
            } else {
                // Обрезаем лишние символы
                s?.delete(19, s.length)
            }

            isFormatting = false
        }
    }

    private inner class ValidityPeriodTextWatcher : TextWatcher {
        private var isFormatting = false

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            if (isFormatting) return
            isFormatting = true

            val currentText = s.toString()
            val cleanText = currentText.replace("/", "").filter { it.isDigit() }

            // Ограничиваем максимум 4 цифрами
            val limitedText = if (cleanText.length > 4) cleanText.substring(0, 4) else cleanText

            val formatted = when {
                limitedText.isEmpty() -> ""
                limitedText.length <= 2 -> limitedText
                else -> "${limitedText.substring(0, 2)}/${limitedText.substring(2)}"
            }

            if (formatted != currentText) {
                s?.replace(0, currentText.length, formatted)

                // Автопереход при полном вводе
                if (formatted.length == 5) {
                    binding.svvEditText.requestFocus()
                }
            }

            isFormatting = false
        }
    }

    private inner class SvvTextWatcher : TextWatcher {
        private var isFormatting = false

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            if (isFormatting) return
            isFormatting = true

            val digitsOnly = s.toString().filter { it.isDigit() }

            if (digitsOnly.length <= 3) {
                if (digitsOnly != s.toString()) {
                    s?.replace(0, s.length, digitsOnly)
                }
            } else {
                s?.replace(0, s.length, digitsOnly.substring(0, 3))
            }

            isFormatting = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}