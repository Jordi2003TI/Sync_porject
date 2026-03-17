package com.jorlina.syncapp.Firebase

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.jorlina.syncapp.R
import kotlin.text.get
import kotlin.text.set

var tiempoInicio: Long = 0
val db = Firebase.firestore
open class FirebaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun contarEntradaAyuda() {
        val ref = db.collection("stats").document("appStats")
        ref.update(
            "vecesAyuda",
            com.google.firebase.firestore.FieldValue.increment(1)
        )
    }

    fun contarEntradaPerfil() {

        val ref = db.collection("stats").document("appStats")

        ref.update(
            "vecesPerfil",
            com.google.firebase.firestore.FieldValue.increment(1)
        )
    }
    fun incrementarItemsEliminados() {

        db.collection("stats")
            .document("appStats")
            .update(
                "itemsEliminados",
                com.google.firebase.firestore.FieldValue.increment(1)
            )
    }

    fun incrementarItemsEditados() {

        db.collection("stats")
            .document("appStats")
            .update(
                "itemsEditados",
                com.google.firebase.firestore.FieldValue.increment(1)
            )
    }

    open fun incrementarItemsCreados() {

        db.collection("stats")
            .document("appStats")
            .update(
                "itemsCreados",
                com.google.firebase.firestore.FieldValue.increment(1)
            )
    }

    fun crearStatsIniciales() {
        val ref = db.collection("stats").document("appStats")

        ref.get()
            .addOnSuccessListener { document ->
                if (!document.exists()) {
                    val stats = AppStats()
                    ref.set(stats)
                }
            }
    }

    fun sumarTiempoUso(tiempo: Long) {

        val ref = db.collection("stats").document("appStats")

        ref.update("tiempoUsoTotal",
            com.google.firebase.firestore.FieldValue.increment(tiempo))
    }

    override fun onStart() {
        super.onStart()
        tiempoInicio = System.currentTimeMillis()
    }


    override fun onStop() {
        super.onStop()

        val tiempoFin = System.currentTimeMillis()
        val tiempoSesion = tiempoFin - tiempoInicio

        sumarTiempoUso(tiempoSesion)
    }
}