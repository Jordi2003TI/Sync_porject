package com.jorlina.syncapp.model.perfilrecicler

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jorlina.syncapp.R
import com.jorlina.syncapp.model.SyncItem
import com.jorlina.syncapp.model.toDateString

class PerfilHolder  (
    itemView: View,
    private val onItemClick: (SyncItem) -> Unit
): RecyclerView.ViewHolder(itemView){

    private val tvData : TextView = itemView.findViewById(R.id.tvData)
    private val tvTituloElementoUser: TextView = itemView.findViewById(R.id.tvTituloElementoUser)
    private val tvDescripcionDeUser: TextView = itemView.findViewById(R.id.tvDescripcionDeUser)
    private val ivImagenElementoUser: ImageView = itemView.findViewById(R.id.ivImagenElementoUser)
    private val ivStar1: ImageView = itemView.findViewById(R.id.ivStar1)
    private val ivStar2: ImageView = itemView.findViewById(R.id.ivStar2)
    private val ivStar3: ImageView = itemView.findViewById(R.id.ivStar3)
    private val ivStar4: ImageView = itemView.findViewById(R.id.ivStar4)
    private val ivStar5: ImageView = itemView.findViewById(R.id.ivStar5)
    private val ivEditElementoUser : ImageView = itemView.findViewById(R.id.ivEditElementoUser)
    private val ivFotoDeleteItemUser : ImageView = itemView.findViewById(R.id.ivFotoDeleteItemUser)


    fun bind(item: SyncItem){
        tvTituloElementoUser.text = item.titulo
        tvDescripcionDeUser.text = item.description
        ivImagenElementoUser.setImageResource(item.ImagenDoc)
        ivEditElementoUser.setImageResource(R.drawable.edit_icon)
        ivFotoDeleteItemUser.setImageResource(R.drawable.delete_icon)

        tvData.text = if (item.dateUpdated != null) {
            "Editado: ${item.dateUpdated.toDateString()}"
        } else {
            "Creado: ${item.dateCreated.toDateString()}"
        }

        val estrellas = listOf(ivStar1, ivStar2, ivStar3, ivStar4, ivStar5)
        for (i in estrellas.indices) {
            if (i < item.puntuacion) {
                estrellas[i].setImageResource(R.drawable.full_star_icon) // Imagen de estrella llena
            } else {
                estrellas[i].setImageResource(R.drawable.empty_star_icon) // Imagen de estrella vacía
            }
        }

        itemView.setOnClickListener {
            onItemClick(item)
        }
    }


}