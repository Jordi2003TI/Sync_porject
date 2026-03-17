package com.jorlina.syncapp.Firebase

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.jorlina.syncapp.R

class StatsActivity : AppCompatActivity() {

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    fun guardarEstadisticas(id:  String, tiempoUsoTotal: Long, vecesAyuda: Int, vecesPerfil: Int, itemsCreados: Int, itemsEditados: Int, itemsEliminados: Int) {

        val nouDocument = db.collection("estadisticas").document()

        val estadistica = AppStats(
            id = nouDocument.id,
            tiempoUsoTotal = tiempoUsoTotal,
            vecesAyuda = vecesAyuda,
            vecesPerfil = vecesPerfil,
            itemsCreados = itemsCreados,
            itemsEditados = itemsEditados,
            itemsEliminados = itemsEliminados
        )

        nouDocument.set(estadistica)
    }

}