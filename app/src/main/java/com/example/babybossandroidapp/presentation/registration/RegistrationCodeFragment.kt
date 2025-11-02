package com.example.babybossandroidapp.presentation.registration

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.babybossandroidapp.R
import com.example.babybossandroidapp.databinding.FragmentRegistrationCodeBinding

class RegistrationCodeFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationCodeBinding
    private val correctCode = "12345"
    private lateinit var codeEditTexts: List<EditText>
    private var countDownTimer: CountDownTimer? =  null
    private var isTimerFinished = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRegistrationCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCodeEditTexts()
        setupResendCodeButton()
        applyInitialCodeDesign()
        startTimer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer?.cancel()
    }

    private fun startTimer() {
        isTimerFinished = false
        binding.btnNextCode.isEnabled = false
        binding.btnNextCode.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey_text_button))

        countDownTimer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                val timeText = String.format("Отправить код повторно (%02d:%02d)", seconds / 60, seconds % 60)
                binding.btnNextCode.text = timeText
            }

            override fun onFinish() {
                isTimerFinished = true
                binding.btnNextCode.text = "ПОЛУЧИТЬ КОД ПОВТОРНО"
                binding.btnNextCode.isEnabled = true
                binding.btnNextCode.setTextColor(ContextCompat.getColor(requireContext(), R.color.neutral_light))
                binding.btnNextCode.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.secondary_background)
            }
        }.start()
    }

    private fun setupCodeEditTexts() {
        codeEditTexts = listOf(
            binding.codeFirstNumber,
            binding.codeSecondNumber,
            binding.codeThirdNumber,
            binding.codeFourthNumber,
            binding.codeFifthNumber
        )

        codeEditTexts.forEachIndexed { index, editText ->
            editText.addTextChangedListener(createCodeTextWatcher(index))
            editText.setOnKeyListener(createKeyListener(index))
        }
    }

    private fun createCodeTextWatcher(currentIndex: Int): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val text = s.toString()
                if (text.isNotEmpty()) {

                    if (currentIndex < codeEditTexts.size - 1) {
                        codeEditTexts[currentIndex + 1].requestFocus()
                    }

                    updateCodeFieldDesign(currentIndex, true)

                    checkCompleteCode()
                } else {
                    updateCodeFieldDesign(currentIndex, false)
                }
            }
        }
    }

    private fun createKeyListener(currentIndex: Int): View.OnKeyListener {
        return View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
                if (codeEditTexts[currentIndex].text.isEmpty() && currentIndex > 0) {

                    codeEditTexts[currentIndex - 1].requestFocus()
                    codeEditTexts[currentIndex - 1].text.clear()
                }
            }
            false
        }
    }

    private fun updateCodeFieldDesign(index: Int, hasText: Boolean) {
        val editText = codeEditTexts[index]
        if (hasText) {
            editText.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_edittext_8dp_focus_code)
        } else {
            editText.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_edittext_8dp_focus_green)
        }
    }

    private fun applyInitialCodeDesign() {
        codeEditTexts.forEach { editText ->
            editText.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_edittext_8dp_focus_green)
        }
    }

    private fun checkCompleteCode() {
        val enteredCode = buildString {
            codeEditTexts.forEach { editText ->
                append(editText.text.toString())
            }
        }

        if (enteredCode.length == 5) {
            if (enteredCode == correctCode) {
                hideError()
                navigateToNextScreen()
            } else {
                showCodeError()
            }
        } else {
            hideError()
        }
    }

    private fun showCodeError() {
        binding.tvErrorTextPasswordAndServer.visibility = View.VISIBLE

        codeEditTexts.forEach { editText ->
            editText.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_edittext_8dp_error)
        }

        Handler(Looper.getMainLooper()).postDelayed({
            clearAllCodeFields()
            applyInitialCodeDesign()
            hideError()
            codeEditTexts[0].requestFocus()
        }, 1000)
    }

    private fun hideError() {
        binding.tvErrorTextPasswordAndServer.visibility = View.GONE
    }

    private fun clearAllCodeFields() {
        codeEditTexts.forEach { editText ->
            editText.text.clear()
        }
    }

    private fun navigateToNextScreen() {
         findNavController().navigate(R.id.action_registrationCodeFragment_to_registrationDataFragment)

        Toast.makeText(requireContext(), "Код верный! Регистрация успешна", Toast.LENGTH_SHORT).show()
    }

    private fun setupResendCodeButton() {
        binding.btnNextCode.setOnClickListener {
            if (isTimerFinished) {
                Toast.makeText(requireContext(), "Код отправлен повторно", Toast.LENGTH_SHORT).show()

                startTimer()

                clearAllCodeFields()
                applyInitialCodeDesign()
                hideError()
                codeEditTexts[0].requestFocus()
            }
        }
    }
}