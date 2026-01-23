package com.jorlina.syncapp.MainLogin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel(){
    private val _loginResult = MutableLiveData<Boolean>()
    var loginResult: LiveData<Boolean> = _loginResult

    private val _errorMessage = MutableLiveData<String>()
    var errorMessage: LiveData<String> = _errorMessage

    fun login(username: String, password: String){

        if(username.isBlank()){
            _errorMessage.value = "El usuario no puede vacio"
            return
        }

        if(password.length <= 5){
            _errorMessage.value = "La contraseña debe tener mas de 5 caracteres"
            return
        }

        _errorMessage.value = null
        _loginResult.value = true
    }
}