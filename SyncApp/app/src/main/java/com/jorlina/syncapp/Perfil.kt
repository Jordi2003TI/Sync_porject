package com.jorlina.syncapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jorlina.syncapp.model.DataSyncItem
import com.jorlina.syncapp.model.SyncItem
import com.jorlina.syncapp.model.menuprincipalrecicler.SyncAdapter
import com.jorlina.syncapp.model.perfilrecicler.PerfilAdapter

class Perfil : AppCompatActivity() {

    private lateinit var rvPerfil: RecyclerView
    private lateinit var PerfilAdapter: PerfilAdapter
    private var listaCompleta: List<SyncItem> = listOf()


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
        rvPerfil = findViewById<RecyclerView>(R.id.rvPerfil)

    }

    private fun initListeners(){


    }

    private fun initUI(){
        rvPerfil.layoutManager = LinearLayoutManager(this)


       listaCompleta = DataSyncItem.item
        PerfilAdapter= PerfilAdapter(
            items = listaCompleta,
            onItemClick = { item ->
                Toast.makeText(
                    this,
                    "Has pulsado sobre ${item.titulo}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
        rvPerfil.adapter = PerfilAdapter

    }
}