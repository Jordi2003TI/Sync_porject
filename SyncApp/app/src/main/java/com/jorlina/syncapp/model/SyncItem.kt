package com.jorlina.syncapp.model

import android.widget.ImageView

data class SyncItem(
    val id : Long,
    val idUser : Long,
    val titulo: String,
    val description: String,
    val puntuacion: Int,
    val favoritos: Boolean,
    val ImagenDoc: Int,
    val ImagenPer: Int,
    val categoria: String,
    val dateCreated: Long,
    val dateUpdated: Long
)
