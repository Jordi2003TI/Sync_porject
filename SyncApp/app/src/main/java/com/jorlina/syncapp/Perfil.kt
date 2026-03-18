package com.jorlina.syncapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.jorlina.syncapp.CRUD.ITEM.ItemApi
import com.jorlina.syncapp.CRUD.ITEM.ItemService
import com.jorlina.syncapp.Firebase.FirebaseActivity
import com.jorlina.syncapp.model.DataSyncItem
import com.jorlina.syncapp.model.SyncItem
import com.jorlina.syncapp.model.menuprincipalrecicler.SyncAdapter
import com.jorlina.syncapp.model.perfilrecicler.PerfilAdapter
import kotlinx.coroutines.launch

class Perfil : FirebaseActivity() {

    private lateinit var rvPerfil: RecyclerView
    private lateinit var PerfilAdapter: PerfilAdapter
    private var listaCompleta: List<SyncItem> = listOf()

    private val editItemLauncher = // Esto es una variable globla que hace que cada vez que entremos ejecute esto.
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result -> // esta siempre es igual (recoge el resulSet)

            if (result.resultCode == RESULT_OK) { // Comprueba el resultSet es Okay

                val updatedItem = result.data?.getParcelableExtra<SyncItem>("UPDATED_ITEM") // le da a la variable updateItem el valor de data

                updatedItem?.let { item -> // Comprobamos que no sea nulo

                    val index = listaCompleta.indexOfFirst { it.id == item.id } // sacamos en la posicion que se ecunetra el item mediante la id

                    if (index != -1) {
                        val mutable = listaCompleta.toMutableList() // copiamos la lista normal pero mutable
                        mutable[index] = item // buscamos la psocicon del anterior item para remplazarlo por el nuevo item
                        listaCompleta = mutable // ya cambiamos la lista por la nueva

                        PerfilAdapter.updateList(listaCompleta) // actualizamos la lista
                    }
                }
            }
        }


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

        contarEntradaPerfil()
        onStart()

    }


    private fun initComponents(){
        rvPerfil = findViewById<RecyclerView>(R.id.rvPerfil)

    }

    private fun initListeners(){


    }


    private fun initUI(){
        rvPerfil.layoutManager = LinearLayoutManager(this)// Es para hacer que nuestro recycle vew sepa como mostrar los elementos

        PerfilAdapter = PerfilAdapter(
            items = mutableListOf(),// Estamos creando una mutableList para que podamos actualizar el recycle al momento

            onItemClick = { item ->
                Toast.makeText(
                    this,
                    "Has pulsado sobre ${item.titulo}",
                    Toast.LENGTH_SHORT
                ).show()
            },
            // Aqui le damos a accion al boton de papelera/basura
            onDeleteClick = { item, position ->
                AlertDialog.Builder(this)
                    .setTitle("Eliminar")
                    .setMessage("¿Seguro que quieres eliminar este item?")
                    .setPositiveButton("Sí") { _, _ ->
                        lifecycleScope.launch { //  Corrutina - sincronizar con la api
                            try {
                                val response = ItemApi.API().deleteItemById(item.id) // aqui nos devuleve información de la api es decir nos devuelve un 200 -> ok

                                if (response.isSuccessful) { // Comprobamos que to ha ido okay que nos devuelve ok
                                    incrementarItemsEliminados()//Funcion de estadisticas
                                    listaCompleta = listaCompleta.filter { it.id != item.id } // recogemos la lista pero en memoria aun no la hemos aplicado
                                    PerfilAdapter.removeItem(position)

                                    Toast.makeText(
                                        this@Perfil,
                                        "${item.titulo} eliminado correctamente",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                } else {
                                    Toast.makeText(
                                        this@Perfil,
                                        "Error al eliminar: ${response.code()}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            } catch (e: Exception) {
                                e.printStackTrace()
                                Toast.makeText(
                                    this@Perfil,
                                    "Error de conexión al eliminar",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                    .setNegativeButton("No", null)
                    .show()
            },

            onEditClick = { item, position ->
                val intent = Intent(this, PerfilPatchActivity::class.java)
                intent.putExtra("SYNC_ITEM", item)

                editItemLauncher.launch(intent)
            }
        )

        rvPerfil.adapter = PerfilAdapter

        val userIdString = intent.getStringExtra("RecueprarIdUser")
        val userId: Long? = userIdString?.toLongOrNull()

        if (userId != null) {
            fetchItemsByUserId(userId)
        } else {
            Toast.makeText(this, "ID de usuario inválido", Toast.LENGTH_SHORT).show()
        }

    }


    private fun fetchItemsByUserId(userId: Long) {
        lifecycleScope.launch {
            try {
                val response = ItemApi.API().getItemsByUserId(userId)
                if (response.isSuccessful) {
                    val itemsDTO = response.body() ?: listOf()

                    val items: List<SyncItem> = itemsDTO.map { dto ->
                        SyncItem(
                            id = dto.id,
                            idUser = dto.idUser,
                            titulo = dto.titulo,
                            description = dto.description,
                            puntuacion = 0,
                            favoritos = false,
                            imagen_doc = dto.imagen_doc,
                            imagen_per = 0,
                            categoria = "",
                            onCreated = dto.onCreated,
                            onUpdate = dto.onUpdate
                        )
                    }

                    listaCompleta = items
                    PerfilAdapter.updateList(listaCompleta)

                } else {
                    Toast.makeText(
                        this@Perfil,
                        "Error al cargar items: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@Perfil, "Error al cargar items", Toast.LENGTH_SHORT).show()
            }
        }
    }
}