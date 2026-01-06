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
import com.jorlina.syncapp.model.SyncAdapter
import com.jorlina.syncapp.model.SyncItem
import javax.sql.DataSource

class MenuPrincipalActivity : AppCompatActivity() {
    //private lateinit var arrowBackIv: ImageView
    private lateinit var filterButton: Button
    //private lateinit var add_iv : ImageView

    //private lateinit var ivPerfil : ImageView

    //private lateinit var settings_iv : ImageView

    private lateinit var bnvNavegation: BottomNavigationView

    private lateinit var rvRecientes: RecyclerView
    private lateinit var SyncAdapter: SyncAdapter

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
        //arrowBackIv = findViewById<ImageView>(R.id.arrowBackIv)
        filterButton = findViewById<Button>(R.id.btFiltrosUser)
        //add_iv = findViewById<ImageView>(R.id.add_iv)
        //ivPerfil = findViewById<ImageView>(R.id.ivPerfil)
        //settings_iv = findViewById<ImageView>(R.id.settings_iv)
        bnvNavegation = findViewById<BottomNavigationView>(R.id.bnvNavegation)
        rvRecientes = findViewById<RecyclerView>(R.id.rvRecientes)
        

    }

    private fun initListeners(){
        /*
        arrowBackIv.setOnClickListener {
            finish()
        }*/

        filterButton.setOnClickListener {
            val intent = Intent(this, Filtros::class.java)
            startActivity(intent)
        }
        /*
        add_iv.setOnClickListener {
            val intent = Intent(this, CreateActivity::class.java)
            startActivity(intent)
        }
        */
        /*ivPerfil.setOnClickListener {
            val intent = Intent(this, Perfil::class.java)
            startActivity(intent)
        }*/
        /*
        settings_iv.setOnClickListener {
            val intent = Intent(this, PreferenciasActivity::class.java)
            startActivity(intent)
        }
        */

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
        val items = DataSyncItem.item
        SyncAdapter = SyncAdapter(
            items = items,
            onItemClick = { item ->
                Toast.makeText(
                    this,
                    "Has pulsado sobre ${item.titulo}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
        rvRecientes.adapter = SyncAdapter
    }
}