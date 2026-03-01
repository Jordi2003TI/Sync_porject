package com.jorlina.syncapp

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.jorlina.syncapp.CRUD.ITEM.ItemApi
import com.jorlina.syncapp.model.SyncItemRequest
import com.jorlina.syncapp.model.menuprincipalrecicler.SyncAdapter
import com.jorlina.syncapp.model.perfilrecicler.PerfilAdapter
import kotlinx.coroutines.launch

class CreateActivity : AppCompatActivity() {

    private lateinit var etTitleCreate: EditText

    private lateinit var etIdUser: EditText

    private lateinit var ivAddImage: ImageView

    private lateinit var etDescription : EditText

    private lateinit var SpinerCategoriaFiltros: Spinner

    private lateinit var btCreatePost: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create)
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
        etTitleCreate = findViewById<EditText>(R.id.etTitleCreate)
        etIdUser = findViewById<EditText>(R.id.etIdUser)
        ivAddImage = findViewById<ImageView>(R.id.ivAddImage)
        etDescription = findViewById<EditText>(R.id.etDescription)
        SpinerCategoriaFiltros = findViewById<Spinner>(R.id.SpinerCategoriaFiltros)
        btCreatePost = findViewById<Button>(R.id.btCreatePost)
    }

    private fun initListeners(){
        btCreatePost.setOnClickListener {
            createItem()

        }
    }
    private fun initUI(){

        val adapterSpinner = ArrayAdapter.createFromResource(
            this,
            R.array.categorias_spinner,
            android.R.layout.simple_spinner_item
        )

        adapterSpinner.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        SpinerCategoriaFiltros.adapter = adapterSpinner
    }

    private fun createItem() {
        val titulo = etTitleCreate.text.toString()
        val descripcion = etDescription.text.toString()
        val idUser = etIdUser.text.toString().toLongOrNull() ?: 1L
        val categoria = SpinerCategoriaFiltros.selectedItem.toString()


        if (titulo.isEmpty() || descripcion.isEmpty()) {
            Toast.makeText(this,
                "Completa todos los campos obligatorios",
                Toast.LENGTH_LONG).show()
            return
        }

        val newItem = SyncItemRequest(
            idUser = idUser,
            titulo = titulo,
            description = descripcion,
            categoria = categoria
        )

        
        lifecycleScope.launch {
            try {
                val response = ItemApi.API().addItem(newItem)

                if (response.isSuccessful) {
                    Toast.makeText(this@CreateActivity,
                        "Item creado correctamente",
                        Toast.LENGTH_LONG).show()
                        setResult(RESULT_OK)
                        finish()

                } else {
                    Toast.makeText(this@CreateActivity,
                        "Error: ${response.code()}",
                        Toast.LENGTH_LONG).show()
                }

            } catch (e: Exception) {
                Toast.makeText(this@CreateActivity,
                    "Error conexión: ${e.message}",
                    Toast.LENGTH_LONG).show()
            }
        }
    }
}

