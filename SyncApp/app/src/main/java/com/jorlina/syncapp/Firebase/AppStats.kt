package com.jorlina.syncapp.Firebase

data class AppStats(
    val id: String = "appStats",
    val tiempoUsoTotal: Long = 0,
    val vecesAyuda: Int = 0,
    val vecesPerfil: Int = 0,
    val itemsCreados: Int = 0,
    val itemsEditados: Int = 0,
    val itemsEliminados: Int = 0,
    val vecesCrear: Int = 0
)