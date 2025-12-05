package com.jorlina.syncapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Filtros : AppCompatActivity() {

    private lateinit var spinner : Spinner

    private lateinit var arrowBackIv: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtros)
        initComponents()
        listeners()
        initUI()

    }

    private fun initUI() {
        
    }

    private fun listeners() {
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.abc_spinner,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter=adapter

        arrowBackIv.setOnClickListener {
            finish()
        }

    }

    private fun initComponents() {
        spinner=findViewById<Spinner>(R.id.SpinerAlfabetic)
        spinner.prompt="Alfabetico"

        arrowBackIv = findViewById<ImageView>(R.id.arrowBackIv)
    }
}