package com.jorlina.syncapp

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.jorlina.syncapp.CRUD.ITEM.ItemApi
import com.jorlina.syncapp.model.SyncItemRequest
import kotlinx.coroutines.launch

class CreateActivity : AppCompatActivity() {

    private lateinit var etTitleCreate: EditText

    private lateinit var etIdUser: EditText

    private lateinit var FileCard: ImageView

    private lateinit var etDescription : EditText

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

    }

    private fun initListeners(){

    }
    private fun initUI(){

    }

    private fun createItem() {

        val newItem = SyncItemRequest(
            idUser = 1L,
            titulo = "Nuevo item",
            description = "Descripción ejemplo",
            categoria = "General"
        )

        lifecycleScope.launch {
            try {
                val response = ItemApi.API().addItem(newItem)

                if (response.isSuccessful) {
                    Toast.makeText(this@CreateActivity,
                        "Item creado correctamente",
                        Toast.LENGTH_LONG).show()
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