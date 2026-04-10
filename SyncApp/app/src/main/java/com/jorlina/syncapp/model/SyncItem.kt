package com.jorlina.syncapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


//Necesita plugins en el graddle (id("kotlin-parcelize"))
@Parcelize
data class SyncItem(
    val id : Long,
    val idUser : Long,
    val titulo: String,
    val description: String,
    val puntuacion: Int,
    val favoritos: Boolean,
    val imagen_doc: Int,
    val imagen_per: Int,
    val categoria: String,
    val onCreated: Long,
    val onUpdate: Long
) : Parcelable
