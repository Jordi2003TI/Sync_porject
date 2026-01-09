package com.jorlina.syncapp.model

import com.jorlina.syncapp.R

object DataSyncItem{

    val item: List<SyncItem> = listOf(
        SyncItem("Ejemplo 1", "ejemplo descripcion", 2, true, R.drawable.favorite_icon, R.drawable.user_icon_image, "Biologia General"),
        SyncItem("Ejemplo 2", "ejemplo descripcion", 5, true, R.drawable.favorite_icon, R.drawable.user_icon_image, "Calculo"),
        SyncItem("Ejemplo 3", "ejemplo descripcion", 1, false, R.drawable.favorite_icon, R.drawable.user_icon_image, "Quimica Organica"),
        SyncItem("Ejemplo 4", "ejemplo descripcion", 4, true, R.drawable.favorite_icon, R.drawable.user_icon_image, "Calculo"),
        SyncItem("Ejemplo 5", "ejemplo descripcion", 3, false, R.drawable.favorite_icon, R.drawable.user_icon_image,"Fundamentos de la programacion")
    )
} 