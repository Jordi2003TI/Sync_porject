package com.jorlina.syncapp.CreateAccount

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateAccountViewModel : ViewModel() {

    private val _registerResult = MutableLiveData<Boolean>()
    val registerResult: LiveData<Boolean> = _registerResult

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun createAccount(
        username: String,
        email: String,
        password: String,
        repeatPassword: String
    ) {

        if (username.isBlank()) {
            _errorMessage.value = "Usuario obligatorio"
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _errorMessage.value = "Email no válido"
            return
        }

        if (password.length < 6) {
            _errorMessage.value = "Contraseña demasiado corta"
            return
        }

        if (password != repeatPassword) {
            _errorMessage.value = "Las contraseñas no coinciden"
            return
        }

        _errorMessage.value = null
        _registerResult.value = true

    }

}