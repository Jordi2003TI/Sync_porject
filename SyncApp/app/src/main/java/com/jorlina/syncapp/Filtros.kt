package com.jorlina.syncapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewParent
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.FieldPosition

class Filtros : AppCompatActivity() {

    private lateinit var spinner: Spinner

    private lateinit var SpinerCategoria: Spinner

    private lateinit var arrowBackIv: ImageView

    private lateinit var btFiltrar: Button

    private var categoriaSeleccionada: String = "Todas"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_filtros)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initComponents()
        listeners()
        initUI()

    }

    private fun initUI() {
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.abc_spinner,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // Segundo Spinner
        val adapterSpinner2 = ArrayAdapter.createFromResource(
            this,
            R.array.categorias_spinner,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        SpinerCategoria.adapter = adapterSpinner2

        SpinerCategoria.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                categoriaSeleccionada = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No hacer nada
            }

        }
    }

    private fun listeners() {

        arrowBackIv.setOnClickListener {
            finish()
        }

        btFiltrar.setOnClickListener {
            val intent = Intent()
            intent.putExtra("CATEGORIA", categoriaSeleccionada)
            setResult(RESULT_OK, intent)
            finish()
        }

    }

    private fun initComponents() {
        spinner = findViewById<Spinner>(R.id.SpinerAlfabetic)
        //spinner.prompt="Alfabetico"

        arrowBackIv = findViewById<ImageView>(R.id.arrowBackIv)
        SpinerCategoria = findViewById<Spinner>(R.id.SpinerCategoria)
        btFiltrar = findViewById<Button>(R.id.btFiltrar)
    }
}