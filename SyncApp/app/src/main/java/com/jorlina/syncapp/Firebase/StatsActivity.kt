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


    fun guardarEstadisticas(id: String, minutos: Int, posteados: Int, editados: Int, eliminados: Int, ayudas: Int, perfil: Int) {

        val nouDocument = db.collection("estadisticas").document()

        val estadistica = Estadisticas(
            id = nouDocument.id,
            minutos = minutos,
            posteados = posteados,
            editados = editados,
            eliminados = eliminados,
            ayudas = ayudas,
            perfil = perfil
        )

        nouDocument.set(estadistica)
    }
}