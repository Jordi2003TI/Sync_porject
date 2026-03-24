package com.jorlina.syncapp.Firebase

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jorlina.syncapp.R

class StatsActivity : AppCompatActivity() {

    private val db = Firebase.firestore

    private lateinit var barChart: BarChart
    private lateinit var tvTiempo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        // 🔹 Inicializar vistas
        barChart = findViewById(R.id.barChart)
        tvTiempo = findViewById(R.id.tvTiempo)

        Log.d("STATS", "barChart initialized: $barChart")

        // 🔹 Botón volver
        findViewById<Button>(R.id.btnVolver).setOnClickListener {
            finish()
        }

        // 🔹 Cargar datos de prueba
        cargarStatsDePrueba()
    }

    private fun cargarStatsDePrueba() {
        // Datos de prueba
        val tiempoUsoTotal = 120 // ejemplo
        val vecesAyuda = 5
        val vecesPerfil = 3
        val itemsCreados = 7
        val itemsEditados = 2
        val itemsEliminados = 6

        tvTiempo.text = "Tiempo uso: $tiempoUsoTotal"

        // Crear entradas para el gráfico
        val entries = listOf(
            BarEntry(0f, vecesAyuda.toFloat()),
            BarEntry(1f, vecesPerfil.toFloat()),
            BarEntry(2f, itemsCreados.toFloat()),
            BarEntry(3f, itemsEditados.toFloat()),
            BarEntry(4f, itemsEliminados.toFloat())
        )

        val dataSet = BarDataSet(entries, "Estadísticas")
        val data = BarData(dataSet)
        barChart.data = data

        // Etiquetas del eje X
        val labels = listOf("Ayuda", "Perfil", "Creados", "Editados", "Eliminados")
        val xAxis = barChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.setDrawGridLines(false)

        // Ajustes gráficos
        barChart.axisRight.isEnabled = false
        barChart.description.isEnabled = false
        barChart.setFitBars(true)

        // Refrescar gráfico
        barChart.invalidate()
    }
}