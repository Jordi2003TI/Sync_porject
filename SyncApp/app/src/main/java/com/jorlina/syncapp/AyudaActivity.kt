package com.jorlina.syncapp

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.jorlina.syncapp.Firebase.StatsActivity

class AyudaActivity : AppCompatActivity() {
    private lateinit var arrowBackIv : ImageView
    val db = Firebase.firestore



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ayuda)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initComponents();
        initListeners();
        initUI()

        contarEntradaAyuda()
    }

    fun contarEntradaAyuda() {

        val ref = db.collection("stats").document("appStats")
        ref.update(
            "vecesAyuda",
            com.google.firebase.firestore.FieldValue.increment(1)
        )
    }
    private fun initComponents() {
        arrowBackIv = findViewById<ImageView>(R.id.arrowBackIv)
    }

    private fun initListeners() {
        arrowBackIv.setOnClickListener {
            finish()
        }
    }

    private fun initUI() {

    }


}