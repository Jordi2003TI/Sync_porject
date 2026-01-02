package com.jorlina.syncapp.model

import android.widget.ImageView

data class SyncItem(
    val titulo: String,
    val description: String,
    val puntuacion: Int,
    val favoritos: Boolean,
    val ImagenDoc: Int,
    val ImagenPer: Int
)
