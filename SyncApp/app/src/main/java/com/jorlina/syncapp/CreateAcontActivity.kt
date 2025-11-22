package com.jorlina.syncapp

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CreateAcontActivity : AppCompatActivity() {

    private lateinit var arrowBackIv: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_acont)
        initComponents();
        initListeners();
        initUI()
    }

    private  fun initComponents(){
        arrowBackIv = findViewById<ImageView>(R.id.arrowBackIv)
    }

    private fun initListeners(){
        arrowBackIv.setOnClickListener {
            finish()
        }
    }

    private fun initUI(){

    }
}