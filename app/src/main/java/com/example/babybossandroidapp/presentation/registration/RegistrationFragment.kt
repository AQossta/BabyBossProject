package com.example.babybossandroidapp.presentation.registration

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.Editable
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.text.set
import androidx.fragment.app.Fragment
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
        setupClickableAgreementText()
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

    private fun updateNextButtonState(isEnabled: Boolean) {
        binding.btnRegister.isEnabled = isEnabled
        binding.btnRegister.backgroundTintList = ContextCompat.getColorStateList(
            requireContext(),
            if (isEnabled) R.color.secondary_background else R.color.grey
        )
        binding.btnRegister.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                if (isEnabled) R.color.neutral_light else R.color.grey_text_button
            )
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

    private fun setupClickableAgreementText() {
        val fullText = "Нажимая кнопку «Далее», вы даете согласие на" +
                "\nСбор и обработку персональных данных, Политику конфиденциальности и соглашаетесь с Условиями пользовательского соглашения"

        val spannableString = SpannableString(fullText)

        val personalDataStart = fullText.indexOf("Сбор и обработку персональных данных")
        val personalDataEnd = personalDataStart + "Сбор и обработку персональных данных".length
        setClickableSpan(spannableString, personalDataStart, personalDataEnd, "personal_data")

        val privacyStart = fullText.indexOf("Политику конфиденциальности")
        val privacyEnd = privacyStart + "Политику конфиденциальности".length
        setClickableSpan(spannableString, privacyStart, privacyEnd, "privacy_policy")

        val termsStart = fullText.indexOf("Условиями пользовательского соглашения")
        val termsEnd = termsStart + "Условиями пользовательского соглашения".length
        setClickableSpan(spannableString, termsStart, termsEnd, "user_agreement")

        binding.txtAgreement.text = spannableString
        binding.txtAgreement.movementMethod = LinkMovementMethod.getInstance()
        binding.txtAgreement.highlightColor = android.graphics.Color.TRANSPARENT
    }

    private fun setClickableSpan(spannableString: SpannableString, start: Int, end: Int, type: String) {
        spannableString.setSpan(
            object : android.text.style.ClickableSpan() {
                override fun onClick(widget: View) {
                    navigateToAgreement(type)
                }
                override fun updateDrawState(ds: TextPaint) {
                    ds.color = ContextCompat.getColor(requireContext(), R.color.secondary_background)
                    ds.isUnderlineText = false
                }
            },
            start,
            end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    private fun navigateToAgreement(type: String) {
        val bundle = Bundle().apply {
            putString("agreement_type", type)
        }
        findNavController().navigate(R.id.action_registrationFragment_to_agreementFragment, bundle)
    }
}