package com.jorlina.syncapp

import android.speech.RecognitionListener
import android.app.Service
import android.os.IBinder
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.jorlina.syncapp.CRUD.ITEM.ItemApi
import com.jorlina.syncapp.Firebase.AppStats
import com.jorlina.syncapp.Firebase.FirebaseActivity
import com.jorlina.syncapp.Firebase.StatsActivity
import com.jorlina.syncapp.model.DataSyncItem
import com.jorlina.syncapp.model.SyncItem
import com.jorlina.syncapp.model.menuprincipalrecicler.SyncAdapter
import kotlinx.coroutines.launch

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MenuPrincipalActivity : FirebaseActivity() {
    private lateinit var svBusquedaUser: SearchView
    private lateinit var filterButton: Button
    private lateinit var bnvNavegation: BottomNavigationView
    private lateinit var rvRecientes: RecyclerView
    private lateinit var syncAdapter: SyncAdapter
    private var listaCompleta: List<SyncItem> = listOf()
    private var REQUEST_CODE_FILTROS = 100

    private var likeBool = false
    private lateinit var recognizer: SpeechRecognizer
    private lateinit var recognizerIntent: Intent
    private lateinit var micButton: Button



    private val createItemLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                cargarDatosDesdeAPI()
            }
        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_principal) //Linea para enlazar la Activity con su layout !!
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        crearStatsIniciales()
        onStart()
        initComponents();
        initListeners();
        initUI()
        pedirPermisosMic()
    }

    private fun pedirPermisosMic(){
        // Pedir permiso en tiempo de ejecución
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                101
            )
        }
    }

    private fun initComponents() {
        svBusquedaUser = findViewById(R.id.svBusquedaUser)
        filterButton = findViewById(R.id.btFiltrosUser)
        bnvNavegation = findViewById(R.id.bnvNavegation)
        rvRecientes = findViewById(R.id.rvRecientes)
        micButton = findViewById(R.id.btMicrofono)

        // Inicializar recognizer e intent
        recognizer = SpeechRecognizer.createSpeechRecognizer(this)
        recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES")
        }

        recognizer.setRecognitionListener(object : RecognitionListener {
            override fun onResults(results: Bundle?) {
                val spokenText = results
                    ?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    ?.get(0)
                    ?.lowercase()

                Log.d("VOICE", "Texto reconocido: $spokenText")

                handleVoiceCommand(spokenText)
            }
            override fun onError(error: Int) {
                Log.e("VOICE", "Error reconocimiento: $error")
                Toast.makeText(this@MenuPrincipalActivity,
                    "Error en reconocimiento ($error)",
                    Toast.LENGTH_SHORT).show()
            }
            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {}
            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })
    }

    private fun initListeners() {

        micButton.setOnClickListener {
            recognizer.startListening(recognizerIntent)
        }

        svBusquedaUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                filtrarPorTitulo(query.orEmpty())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filtrarPorTitulo(newText.orEmpty())
                return true
            }
        })

        filterButton.setOnClickListener {
            onStop()
            val intent = Intent(this, Filtros::class.java)
            startActivityForResult(intent, REQUEST_CODE_FILTROS)
        }

        bnvNavegation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_crud -> {
                    onStop()
                    val intent = Intent(this, CreateActivity::class.java)
                    createItemLauncher.launch(intent)
                    true
                }

                R.id.nav_settings -> {
                    onStop()
                    startActivity(Intent(this, PreferenciasActivity::class.java))
                    true
                }
//                R.id.like_bool -> {
//                    filtrarPorLike()
//                    true
//                }
                else -> false
            }
        }
    }

    private fun initUI() {
        rvRecientes.layoutManager = LinearLayoutManager(this)

        listaCompleta = DataSyncItem.item

        syncAdapter = SyncAdapter(
            items = listOf(), // Inicialmente vacío
            onItemClick = { item ->
                Toast.makeText(
                    this,
                    "Has pulsado sobre ${item.titulo}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
        rvRecientes.adapter = syncAdapter

        rvRecientes.layoutManager = LinearLayoutManager(this)


        rvRecientes.adapter = syncAdapter

        // Llamamos al API
        cargarDatosDesdeAPI()

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_FILTROS && resultCode == RESULT_OK) {

            val categoriaSeleccionada = data?.getStringExtra("CATEGORIA") ?: "Todas"
            val likeSeleccionado = data?.getBooleanExtra("LIKE", false) ?: false

            aplicarFiltro(categoriaSeleccionada, likeSeleccionado)


        }
    }

    private fun filtrarPorTitulo(texto: String) {
        val listaFiltrada = listaCompleta.filter {
            it.titulo.lowercase().contains(texto.lowercase())
        }

        syncAdapter.updateList(listaFiltrada)
    }

//    private fun filtrarPorLike(likeBool : Boolean) {
//        likeBool = !likeBool
//
//        val listaFiltrada = if (likeBool) {
//            // mostrar solo favoritos
//            listaCompleta.filter { it.favoritos }
//        } else {
//            // mostrar todos
//            listaCompleta
//        }
//
//        syncAdapter.updateList(listaFiltrada)
//    }

    private fun aplicarFiltro(categoria: String, like: Boolean) {
        val listaFiltrada = listaCompleta.filter { item ->
            val categoriaOk = categoria == "Todas" || item.categoria == categoria
            val likeOk =
                !like || item.favoritos // si like=false, mostrar todos; si true, solo favoritos
            categoriaOk && likeOk
        }

        syncAdapter.updateList(listaFiltrada)
        Toast.makeText(this, "Filtrado por: $categoria, favoritos: $like", Toast.LENGTH_SHORT)
            .show()
    }

    private fun cargarDatosDesdeAPI() {
        lifecycleScope.launch { //corutina
            try {
                val response = ItemApi.API().getItem() //Carga los items de la base de datos
                if (response.isSuccessful) { //Comprobacion de que la lista se haya cargado
                    val listaAPI = response.body() ?: emptyList()
                    listaCompleta = listaAPI
                    // Actualizamos el RecyclerView
                    syncAdapter.updateList(listaCompleta)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(
                    this@MenuPrincipalActivity,
                    "Error de conexión: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
                Log.i("Error", e.message.toString())
            }
        }

    }

    private fun handleVoiceCommand(command: String?) {

        Toast.makeText(this@MenuPrincipalActivity,
            "Reconocimiento de voz activado",
            Toast.LENGTH_LONG).show()
        val cmd = command?.lowercase()?.trim() ?: return

        when {
            cmd.contains("buscar") -> {
                val query = cmd.substringAfter("buscar").trim()
                svBusquedaUser.setQuery(query, false)
                filtrarPorTitulo(query)
            }

            cmd.contains("limpiar") -> {
                svBusquedaUser.setQuery("", false)
                filtrarPorTitulo("")
            }

            cmd.contains("atras") || cmd.contains("atrás") || cmd.contains("enrere") -> {
                onBackPressedDispatcher.onBackPressed()
            }

            cmd.contains("nuevo") -> {
                val intent = Intent(this, CreateActivity::class.java)
                createItemLauncher.launch(intent)
            }

            cmd.contains("ajustes") -> {
                startActivity(Intent(this, PreferenciasActivity::class.java))
            }

            cmd.contains("perfil") -> {
                startActivity(Intent(this, Perfil::class.java))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        recognizer.destroy()
    }
}
