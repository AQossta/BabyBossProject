package com.example.babybossandroidapp.presentation.registration_data

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ViewAnimator
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.example.babybossandroidapp.R
import com.example.babybossandroidapp.databinding.FragmentRegistrationDataBinding

class RegistrationDataFragment : Fragment() {

    private lateinit var viewAnimator: ViewAnimator
    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var tvFirstName: TextView
    private lateinit var tvLastName: TextView
    private lateinit var btnRegisterData: AppCompatButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        setupClickListeners()
        setupTextWatchers()
    }

    private fun initViews(view: View) {
        viewAnimator = view.findViewById(R.id.viewAnimator)
        btnRegisterData = view.findViewById(R.id.btnRegisterData)

        // Инициализация элементов из первого состояния (индекс 0)
        etFirstName = viewAnimator.getChildAt(0).findViewById(R.id.etFirstName)
        etLastName = viewAnimator.getChildAt(0).findViewById(R.id.etLastName)

        // Инициализация элементов из второго состояния (индекс 1)
        tvFirstName = viewAnimator.getChildAt(1).findViewById(R.id.tvFirstName)
        tvLastName = viewAnimator.getChildAt(1).findViewById(R.id.tvLastName)
    }

    private fun setupClickListeners() {
        btnRegisterData.setOnClickListener {
            if (btnRegisterData.isEnabled) {
                if (viewAnimator.displayedChild == 0) {
                    // Переход к состоянию с заполненными данными
                    showFilledState()
                } else {
                    // Возврат к редактированию
                    showEmptyState()
                }
            }
        }

        // Клик по заполненным данным для возврата к редактированию
        tvFirstName.setOnClickListener { showEmptyState() }
        tvLastName.setOnClickListener { showEmptyState() }
    }

    private fun setupTextWatchers() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                validateInputs()
            }
        }

        etFirstName.addTextChangedListener(textWatcher)
        etLastName.addTextChangedListener(textWatcher)
    }

    private fun validateInputs() {
        val firstName = etFirstName.text.toString().trim()
        val lastName = etLastName.text.toString().trim()

        val isValid = firstName.isNotEmpty() && lastName.isNotEmpty()
        btnRegisterData.isEnabled = isValid

        if (isValid) {
            btnRegisterData.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(requireContext(), R.color.secondary_background)
            )
        } else {
            btnRegisterData.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(requireContext(), R.color.grey_text_button)
            )
        }
    }

    private fun showFilledState() {
        // Сохраняем данные
        val firstName = etFirstName.text.toString().trim()
        val lastName = etLastName.text.toString().trim()

        tvFirstName.text = firstName
        tvLastName.text = lastName

        // Переключаем на второе состояние
        viewAnimator.displayedChild = 1

        // Меняем текст кнопки
        btnRegisterData.text = "ИЗМЕНИТЬ"

        // Скрываем клавиатуру
        hideKeyboard()
    }

    private fun showEmptyState() {
        // Возвращаем к первому состоянию
        viewAnimator.displayedChild = 0

        // Возвращаем текст кнопки
        btnRegisterData.text = "ДАЛЕЕ"

        // Показываем клавиатуру
        showKeyboard()
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    private fun showKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(etFirstName, InputMethodManager.SHOW_IMPLICIT)
    }
}