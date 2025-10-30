package com.example.babybossandroidapp.presentation.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {

    private val _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String> = _phoneNumber

    private val _generatedCode = MutableLiveData<String>()
    val generatedCode: LiveData<String> = _generatedCode

    fun setPhoneNumber(phone: String) {
        _phoneNumber.value = phone
    }

    fun generateAndSendCode() {
        // Генерируем случайный 5-значный код
        val code = (10000..99999).random().toString()
        _generatedCode.value = code

        // Для демо выводим в лог (в реальном приложении здесь будет отправка по SMS/WhatsApp)
        println("Generated code: $code")
    }
}