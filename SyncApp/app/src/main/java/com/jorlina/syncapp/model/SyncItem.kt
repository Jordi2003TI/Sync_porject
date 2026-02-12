package com.jorlina.syncapp.model

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
)
