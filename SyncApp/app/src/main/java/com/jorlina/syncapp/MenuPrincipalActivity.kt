package com.jorlina.syncapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MenuPrincipalActivity : AppCompatActivity() {
    private lateinit var arrowBackIv: ImageView
    private lateinit var filterButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)
        initComponents();
        initListeners();
        initUI()
    }

    private fun initComponents(){
        arrowBackIv = findViewById<ImageView>(R.id.arrowBackIv)
        filterButton = findViewById<Button>(R.id.btFiltrosUser)
    }

    private fun initListeners(){
        arrowBackIv.setOnClickListener {
            finish()
        }

        filterButton.setOnClickListener {
            val intent = Intent(this, Filtros::class.java)
            startActivity(intent)
        }
    }
    private fun initUI(){

    }
}