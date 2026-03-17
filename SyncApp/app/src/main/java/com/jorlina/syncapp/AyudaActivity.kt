package com.jorlina.syncapp

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.jorlina.syncapp.Firebase.FirebaseActivity
import com.jorlina.syncapp.Firebase.StatsActivity

class AyudaActivity : FirebaseActivity() {
    private lateinit var arrowBackIv : ImageView




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

        onStart()
        contarEntradaAyuda()

    }
    private fun initComponents() {
        arrowBackIv = findViewById<ImageView>(R.id.arrowBackIv)
    }

    private fun initListeners() {
        arrowBackIv.setOnClickListener {
            onStop()
            finish()
        }
    }

    private fun initUI() {

    }


}