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
        updateButtonState()
    }

    private fun setupTextWatchers() {
        binding.cardNumberEditText.addTextChangedListener(CardNumberTextWatcher())
        binding.validityPeriodEditText.addTextChangedListener(ValidityPeriodTextWatcher())
        binding.svvEditText.addTextChangedListener(SvvTextWatcher())

        // Общий слушатель для проверки заполненности полей
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                updateButtonState()
            }
        }

        binding.cardNumberEditText.addTextChangedListener(textWatcher)
        binding.validityPeriodEditText.addTextChangedListener(textWatcher)
        binding.svvEditText.addTextChangedListener(textWatcher)
    }

    private fun setupClickListeners() {
        binding.btnNextData.setOnClickListener {
            if (isFormValid()) {
                addCard()
            }
        }
    }

    private fun updateButtonState() {
        val isValid = isFormValid()
        binding.btnNextData.isEnabled = isValid

        if (isValid) {
            binding.btnNextData.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.secondary_background)
            binding.btnNextData.setTextColor(ContextCompat.getColor(requireContext(), R.color.neutral_light))
        } else {
            binding.btnNextData.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.grey)
            binding.btnNextData.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey_text_button))
        }
    }

    private fun isFormValid(): Boolean {
        val cardNumber = binding.cardNumberEditText.text.toString().replace(" ", "")
        val validityPeriod = binding.validityPeriodEditText.text.toString()
        val svv = binding.svvEditText.text.toString()

        return cardNumber.length == 16 &&
                validityPeriod.length == 5 &&
                svv.length == 3
    }

    private fun addCard() {
        val cardNumber = binding.cardNumberEditText.text.toString().replace(" ", "")
        val validityPeriod = binding.validityPeriodEditText.text.toString()
        val svv = binding.svvEditText.text.toString()

        // Здесь логика добавления карты
        Toast.makeText(requireContext(), "Карта успешно добавлена!", Toast.LENGTH_SHORT).show()

        // Переход назад или к следующему экрану
        findNavController().popBackStack()
    }

    // TextWatcher для номера карты (форматирование 4-4-4-4)
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
            }
            isFormatting = false
        }
    }

    // TextWatcher для срока действия (форматирование ММ/ГГ)
    private inner class ValidityPeriodTextWatcher : TextWatcher {
        private var isFormatting = false

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            if (isFormatting) return

            isFormatting = true
            val text = s.toString().replace("/", "")
            if (text.length == 1 && text.toInt() > 1) {
                s?.replace(0, s.length, "0$text/")
            } else if (text.length == 2) {
                val month = text.toIntOrNull()
                if (month != null && month in 1..12) {
                    s?.replace(0, s.length, "$text/")
                }
            } else if (text.length == 4) {
                s?.replace(0, s.length, "${text.substring(0, 2)}/${text.substring(2)}")
            }
            isFormatting = false
        }
    }

    // TextWatcher для CVV (ограничение 3 символа)
    private inner class SvvTextWatcher : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            if (s?.length ?: 0 > 3) {
                s?.delete(3, s.length)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}