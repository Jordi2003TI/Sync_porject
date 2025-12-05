package com.jorlina.syncapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Perfil : AppCompatActivity() {

    private lateinit var arrowBackIv : ImageView
    private lateinit var ivInfo : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfil)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initComponents();
        initListeners();
        initUI()

    }

    private fun initComponents(){
        arrowBackIv = findViewById<ImageView>(R.id.arrowBackIv)
        ivInfo = findViewById<ImageView>(R.id.ivInfo)

    }

    private fun initListeners(){
        arrowBackIv.setOnClickListener {
            finish()
        }
        ivInfo.setOnClickListener {
            val intent = Intent(this, AyudaActivity::class.java)
            startActivity(intent)
        }

    }

    private fun initUI(){

    }
}