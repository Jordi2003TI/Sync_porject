package com.jorlina.syncapp.model.menuprincipalrecicler

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jorlina.syncapp.CRUD.ITEM.ItemApi
import com.jorlina.syncapp.CRUD.ITEM.ItemService
import com.jorlina.syncapp.model.SyncItem

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
/*Acuerdate de poner .jar en el servidor nuevo de la api y poner la url del servidor en el fichero de docker
* pongo esto aqui porque se que voy a leerlo y me voy a acordar si da problemas*/

class MenuPrincipalActivityViewModel: ViewModel() {
    private val _listaMenuPrincipal = MutableLiveData<List<SyncItem>>()
    val items: LiveData<List<SyncItem>> = _listaMenuPrincipal

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private lateinit var itemService: ItemService

    fun getAllItems(){
        // Esto es una currutina
        viewModelScope.launch {
            try{
                val response = ItemApi.API().getItem()
                if(response.isSuccessful){
                    _listaMenuPrincipal.value = response.body() ?: emptyList()
                }else{
                    _error.value = response.message()
                }
            }catch (e: Exception){
                _error.value = e.message
            }
        }
    }
}
