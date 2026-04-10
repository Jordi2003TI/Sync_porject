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

    private lateinit var barChartGeneral: BarChart
    private lateinit var barChartDetalle: BarChart
    private lateinit var tvTiempo: TextView
    private lateinit var tvMinutos: TextView
    private lateinit var tvCO2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        // Inicializar vistas
        tvMinutos = findViewById(R.id.tvMinutos)
        tvCO2 = findViewById(R.id.tvCO2)
        tvTiempo = findViewById(R.id.tvTiempo)
        barChartGeneral = findViewById(R.id.barChartGeneral)
        barChartDetalle = findViewById(R.id.barChartDetalle)

        findViewById<Button>(R.id.btnVolver).setOnClickListener { finish() }

        // Cargar datos reales desde Firebase
        cargarStatsDesdeFirebase()
    }

    private fun cargarStatsDesdeFirebase() {
        Log.d("StatsActivity", "Intentando cargar datos de Firebase...")

        db.collection("stats").document("appStats")
            .get()
            .addOnSuccessListener { doc ->
                if (!doc.exists()) {
                    Log.d("StatsActivity", "Documento no existe. Creando uno nuevo...")
                    val nuevosStats = AppStats()
                    db.collection("stats").document("appStats").set(nuevosStats)
                        .addOnSuccessListener {
                            Log.d("StatsActivity", "Documento creado. Recargando...")
                            cargarStatsDesdeFirebase()
                        }
                        .addOnFailureListener { e ->
                            Log.e("StatsActivity", "Error al crear documento: ${e.message}", e)
                            Toast.makeText(this, "Error al crear documento: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                    return@addOnSuccessListener
                }

                // Documento existe, convertir a objeto
                val stats = doc.toObject(AppStats::class.java)
                stats?.let {
                    Log.d("StatsActivity", "Datos cargados: tiempoUsoTotal=${it.tiempoUsoTotal}, vecesAyuda=${it.vecesAyuda}, vecesPerfil=${it.vecesPerfil}")

                    // Convertir milisegundos a minutos
                    val minutos = (it.tiempoUsoTotal / 60000).toInt()
                    tvTiempo.text = "Tiempo uso: $minutos min"
                    actualizarCO2(minutos)

                    // Actualizar gráficos con datos de Firebase
                    cargarAcciones(it)
                    cargarAfluencia(it)
                } ?: run {
                    Log.e("StatsActivity", "Error: stats es null")
                    Toast.makeText(this, "Error al cargar datos de Firebase", Toast.LENGTH_LONG).show()
                }
            }
            .addOnFailureListener { exception ->
                Log.e("StatsActivity", "Error al leer Firebase", exception)
                Toast.makeText(this, "Error al leer Firebase: ${exception.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun actualizarCO2(minutos: Int) {
        tvMinutos.text = "Minutos: $minutos"
        val co2 = minutos * 0.5
        tvCO2.text = "$co2 g"
    }

    // Por cada minuto son 0,5 g de C02 consumidos

    private fun cargarAcciones(stats: AppStats) {
        val entries = listOf(
            BarEntry(0f, stats.itemsEliminados.toFloat()),
            BarEntry(1f, stats.itemsCreados.toFloat()),
            BarEntry(2f, stats.itemsEditados.toFloat()),
        )
        val dataSet = BarDataSet(entries, "Detalle")
        dataSet.color = android.graphics.Color.rgb(66, 133, 244)
        val data = BarData(dataSet)
        barChartDetalle.data = data

        val labels = listOf("Eliminados", "Creados", "Editados")
        val xAxis = barChartDetalle.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.setDrawGridLines(false)

        barChartDetalle.axisRight.isEnabled = false
        barChartDetalle.description.isEnabled = false
        barChartDetalle.setFitBars(true)
        barChartDetalle.invalidate()
    }

    private fun cargarAfluencia(stats: AppStats) {
        val entries = listOf(
            BarEntry(0f, stats.vecesAyuda.toFloat()),
            BarEntry(1f, stats.vecesPerfil.toFloat()),
            BarEntry(2f, stats.vecesCrear.toFloat())
        )
        val dataSet = BarDataSet(entries, "General")
        dataSet.color = android.graphics.Color.rgb(66, 133, 244)
        val data = BarData(dataSet)
        barChartGeneral.data = data

        val labels = listOf("Ayuda", "Perfil", "Crear")
        val xAxis = barChartGeneral.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.setDrawGridLines(false)

        barChartGeneral.axisRight.isEnabled = false
        barChartGeneral.description.isEnabled = false
        barChartGeneral.setFitBars(true)
        barChartGeneral.invalidate()
    }
}