package com.jorlina.syncapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jorlina.syncapp.model.DataSyncItem
import com.jorlina.syncapp.model.menuprincipalrecicler.SyncAdapter
import com.jorlina.syncapp.model.SyncItem
import javax.sql.DataSource

class MenuPrincipalActivity : AppCompatActivity() {
    private lateinit var filterButton: Button
    private lateinit var bnvNavegation: BottomNavigationView
    private lateinit var rvRecientes: RecyclerView
    private lateinit var syncAdapter: SyncAdapter
    private var listaCompleta: List<SyncItem> = listOf()
    private var REQUEST_CODE_FILTROS = 100


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_principal)
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
        filterButton = findViewById<Button>(R.id.btFiltrosUser)
        bnvNavegation = findViewById<BottomNavigationView>(R.id.bnvNavegation)
        rvRecientes = findViewById<RecyclerView>(R.id.rvRecientes)
        

    }

    private fun initListeners(){

        filterButton.setOnClickListener {
            val intent = Intent(this, Filtros::class.java)
            startActivityForResult(intent, REQUEST_CODE_FILTROS)
        }

        bnvNavegation.setOnItemSelectedListener { item ->
            when (item.itemId){
                R.id.nav_favorites -> {
                    startActivity(Intent(this, CreateActivity::class.java))
                    true
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, PreferenciasActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
    private fun initUI(){
        rvRecientes.layoutManager = LinearLayoutManager(this)

        listaCompleta = DataSyncItem.item

        syncAdapter= SyncAdapter(
            items = listaCompleta,
            onItemClick = { item ->
                Toast.makeText(
                    this,
                    "Has pulsado sobre ${item.titulo}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
        rvRecientes.adapter = syncAdapter
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_FILTROS && resultCode == RESULT_OK) {

            val categoriaSeleccionada = data?.getStringExtra("CATEGORIA") ?: "Todas"


            aplicarFiltro(categoriaSeleccionada)
        }
    }


    private fun aplicarFiltro(categoria: String) {
        val listaFiltrada = if (categoria == "Todas") {
            listaCompleta // Mostrar todos los items
        } else {
            listaCompleta.filter { it.categoria == categoria }
        }

        syncAdapter.updateList(listaFiltrada)

        Toast.makeText(this, "Filtrado por: $categoria", Toast.LENGTH_SHORT).show()
    }

}
