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
        //Test campos vacios
        if (username.isBlank()) {
            _errorMessage.value = "Usuario obligatorio"
            return
        }
        if (email.isBlank()) {
            _errorMessage.value = "Email obligatorio"
            return
        }
        if (password.isBlank()) {
            _errorMessage.value = "Contaraseña obligatoria"
            return
        }
        if (repeatPassword.isBlank()) {
            _errorMessage.value = "Debes confirmar la contraseña"
            return
        }


        //email con formato incorrecto
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _errorMessage.value = "Email no válido"
            return
        }

        if (password.length < 6) {
            _errorMessage.value = "Contraseña de al menos 6 caracteres"
            return
        }

        if (password != repeatPassword) {
            _errorMessage.value = "Las contraseñas no coinciden"
            return
        }

        if (username.length > 30) {
            _errorMessage.value = "El usuario es demasiado largo"
            return
        }

        if (nombreConNoCaracterValido(username)) {
            _errorMessage.value = "Caracter no valido en el usuario"
            return
        }

        if (username.contains("!") || username.contains("@")) {
            _errorMessage.value = "El usuario es demasiado largo"
            return
        }


        _errorMessage.value = null
        _registerResult.value = true

    }

}

    fun nombreConNoCaracterValido(name: String): Boolean {
        var lista: String = "!#$%&/()=?¿|ª€·-;,\"\'¬~-+*{}<>[] "
        for (c in name) {
            if (c in lista) {
                return true
            }
        }
        return  false
    }