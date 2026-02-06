package com.jorlina.syncapp.model

import com.jorlina.syncapp.R

object DataSyncItem{

    val item: List<SyncItem> = listOf(
        SyncItem(1, 1001,"Ejemplo 1", "ejemplo descripcion muy muy larga para ver como se ve", 2, true, R.drawable.docs_icon, R.drawable.user_icon_image, "Biologia General", ),
        SyncItem(2, 1002,"Ejemplo 2", "ejemplo descripcion", 5, true, R.drawable.docs_icon, R.drawable.user_icon_image, "Calculo"),
        SyncItem(3, 1003,"Ejemplo 3", "ejemplo descripcion", 1, false, R.drawable.docs_icon, R.drawable.user_icon_image, "Quimica Organica"),
        SyncItem(4, 1004,"Ejemplo 4", "ejemplo descripcion", 4, true, R.drawable.docs_icon, R.drawable.user_icon_image, "Calculo"),
        SyncItem(5, 1005,"Ejemplo 5", "ejemplo descripcion", 3, false, R.drawable.docs_icon, R.drawable.user_icon_image,"Fundamentos de la programacion")
    )
} 