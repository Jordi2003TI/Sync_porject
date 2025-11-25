package com.jorlina.syncapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var btLogin: Button

    private lateinit var  arrowBackIv: ImageView

    private lateinit var tvCreaCuenta: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponents();
        initListeners();
        initUI()

    }

    private fun initComponents(){
        btLogin = findViewById<Button>(R.id.btLogin)
        tvCreaCuenta = findViewById<TextView>(R.id.tvCreaCuenta)
        arrowBackIv = findViewById<ImageView>(R.id.arrowBackIv)
    }

    private fun initListeners(){
        btLogin.setOnClickListener {
            val intent = Intent(this, MenuPrincipalActivity::class.java)
            startActivity(intent)
        }

        tvCreaCuenta.setOnClickListener {
            val intent = Intent(this, CreateAcontActivity::class.java )
            startActivity(intent)
        }

        arrowBackIv.setOnClickListener {
            SalirApp()
        }
    }

    private fun initUI(){

    }

    private fun SalirApp(){
        AlertDialog.Builder(this)
            .setTitle("Quieres salir de la aplicacion")
            .setMessage("Seguro que quieres salir")
            .setPositiveButton("Sí"){_, _,->
                finish()
            }
            .setNegativeButton("No", null)
            .setCancelable(true)
            .show()
    }
}