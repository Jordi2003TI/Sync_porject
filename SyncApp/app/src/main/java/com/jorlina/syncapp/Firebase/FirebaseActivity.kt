package com.jorlina.syncapp.Firebase

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore

var tiempoInicio: Long = 0

object FirebaseClient {
    val db by lazy { Firebase.firestore }
}

open class FirebaseActivity : AppCompatActivity() {
    private val TAG = "FirebaseActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Verificar conexión
        verificarConexion()
    }

    private fun verificarConexion() {
        FirebaseClient.db.collection("stats").document("appStats")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "✅ Firebase conectado correctamente")
                } else {
                    Log.e(TAG, "❌ Error de conexión Firebase: ${task.exception?.message}")
                    Toast.makeText(this, "Error de conexión con Firebase", Toast.LENGTH_LONG).show()
                }
            }
    }

    fun contarEntradaAyuda() {
        actualizarContador("vecesAyuda")
    }

    fun contarEntradaPerfil() {
        actualizarContador("vecesPerfil")
    }

    private fun actualizarContador(campo: String) {
        val ref = FirebaseClient.db.collection("stats").document("appStats")
        ref.update(campo, FieldValue.increment(1))
            .addOnFailureListener { e ->
                Log.e(TAG, "Error al actualizar $campo: ${e.message}")
                crearDocumentoSiNoExiste()
            }
    }

    fun incrementarItemsEliminados() {
        actualizarContador("itemsEliminados")
    }

    fun incrementarItemsEditados() {
        actualizarContador("itemsEditados")
    }

    open fun incrementarItemsCreados() {
        actualizarContador("itemsCreados")
    }

    fun crearStatsIniciales() {
        val ref = FirebaseClient.db.collection("stats").document("appStats")

        ref.get()
            .addOnCompleteListener { task ->
                Log.d(TAG, "Task completada: ${task.isSuccessful}, exception: ${task.exception}")
            }
            .addOnSuccessListener { document ->
                if (!document.exists()) {
                    val stats = AppStats(
                        id = "appStats",
                        tiempoUsoTotal = 0,
                        vecesAyuda = 0,
                        vecesPerfil = 0,
                        itemsCreados = 0,
                        itemsEditados = 0,
                        itemsEliminados = 0
                    )
                    ref.set(stats)
                        .addOnSuccessListener {
                            Log.d(TAG, "Documento stats creado exitosamente")
                        }
                        .addOnFailureListener { e ->
                            Log.e(TAG, "Error al crear documento: ${e.message}")
                        }
                }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error al verificar documento: ${e.message}")
            }
    }

    private fun crearDocumentoSiNoExiste() {
        val ref = FirebaseClient.db.collection("stats").document("appStats")
        ref.get().addOnSuccessListener { doc ->
            if (!doc.exists()) {
                val stats = AppStats()
                ref.set(stats)
            }
        }
    }

    fun sumarTiempoUso(tiempo: Long) {
        val ref = FirebaseClient.db.collection("stats").document("appStats")
        ref.update("tiempoUsoTotal", FieldValue.increment(tiempo))
            .addOnFailureListener { e ->
                Log.e(TAG, "Error al sumar tiempo: ${e.message}")
                crearDocumentoSiNoExiste()
            }
    }

    override fun onStart() {
        super.onStart()
        tiempoInicio = System.currentTimeMillis()
        Log.d(TAG, "onStart - tiempo inicio: $tiempoInicio")
    }

    override fun onStop() {
        super.onStop()
        val tiempoFin = System.currentTimeMillis()
        val tiempoSesion = tiempoFin - tiempoInicio
        Log.d(TAG, "onStop - tiempo sesión: $tiempoSesion ms")
        sumarTiempoUso(tiempoSesion)
    }
}