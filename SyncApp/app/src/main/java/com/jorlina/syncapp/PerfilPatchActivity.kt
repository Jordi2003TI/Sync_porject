    package com.jorlina.syncapp

    import android.content.Intent
    import android.os.Build
    import android.os.Bundle
    import android.widget.ArrayAdapter
    import android.widget.Button
    import android.widget.EditText
    import android.widget.ImageView
    import android.widget.Spinner
    import android.widget.Toast
    import androidx.activity.enableEdgeToEdge
    import androidx.annotation.RequiresApi
    import androidx.appcompat.app.AppCompatActivity
    import androidx.core.view.ViewCompat
    import androidx.core.view.WindowInsetsCompat
    import androidx.lifecycle.lifecycleScope
    import com.google.firebase.Firebase
    import com.google.firebase.firestore.firestore
    import com.jorlina.syncapp.CRUD.ITEM.ItemApi
    import com.jorlina.syncapp.Firebase.FirebaseActivity
    import com.jorlina.syncapp.model.SyncItem
    import kotlinx.coroutines.launch


    class PerfilPatchActivity : FirebaseActivity() {

        private lateinit var etTitleCreateP: EditText

        private lateinit var etIdUserP: EditText

        private lateinit var ivAddImageP: ImageView

        private lateinit var etDescriptionP : EditText

        private lateinit var SpinerCategoriaFiltrosP: Spinner

        private lateinit var btUpdatePost: Button

        private var currentItem: SyncItem? = null // Aqui la declaramos y en el onCreate le damos valor (es opcional)

        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContentView(R.layout.activity_perfil_patch)
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
            initComponents();
            initListeners();
            initUI()
            onStart()

            currentItem = intent.getParcelableExtra("SYNC_ITEM", SyncItem::class.java) // Recogemos el item que nos lllega de onEditClick en perfil

            currentItem?.let { // Comprobamos que el item exista y rellenamos los campos del xml del item si existe
                rellenarCampos(it) // El it es propio currentItem
            }

        }

        private fun initUI() {
            val adapterSpinner = ArrayAdapter.createFromResource(
                this,
                R.array.categorias_spinner,
                android.R.layout.simple_spinner_item
            )

            adapterSpinner.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
            )

            SpinerCategoriaFiltrosP.adapter = adapterSpinner

        }

        private fun initListeners() {
            btUpdatePost.setOnClickListener {
                onStop()
                updateItem()

            }
        }

        private fun initComponents() {
            etTitleCreateP = findViewById<EditText>(R.id.etTitleCreateP)
            etIdUserP = findViewById<EditText>(R.id.etIdUserP)
            ivAddImageP = findViewById<ImageView>(R.id.ivAddImageP)
            etDescriptionP = findViewById<EditText>(R.id.etDescriptionP)
            SpinerCategoriaFiltrosP = findViewById<Spinner>(R.id.SpinerCategoriaFiltrosP)
            btUpdatePost = findViewById<Button>(R.id.btUpdatePost)



    }

        private fun updateItem() {

            val original = currentItem ?: return // esto solo comprueba que el item tenga valores

            val nuevoTitulo = etTitleCreateP.text.toString()
            val nuevaDescripcion = etDescriptionP.text.toString()

            lifecycleScope.launch {
                try {

                    var huboCambios = false

                    // 🔹 ACTUALIZAR TÍTULO
                    if (nuevoTitulo != original.titulo) {
                        val responseTitulo =
                            ItemApi.API().updateItemTitulo(original.id, nuevoTitulo)

                        if (!responseTitulo.isSuccessful) {
                            Toast.makeText(
                                this@PerfilPatchActivity,
                                "Error actualizando título",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@launch
                        }

                        huboCambios = true
                    }

                    // 🔹 ACTUALIZAR DESCRIPCIÓN
                    if (nuevaDescripcion != original.description) {
                        val responseDescripcion =
                            ItemApi.API().updateItemDescripcion(original.id, nuevaDescripcion)

                        if (!responseDescripcion.isSuccessful) {
                            Toast.makeText(
                                this@PerfilPatchActivity,
                                "Error actualizando descripción",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@launch // Es importante ponerlo
                        }

                        huboCambios = true
                    }

                    if (huboCambios) {
                        incrementarItemsEditados() //Funcion de estadisticas
                        Toast.makeText(
                            this@PerfilPatchActivity,
                            "Item actualizado correctamente",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@PerfilPatchActivity,
                            "No hubo cambios",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    val updatedItem = original.copy( // hacemos una copia del original pero cambiadonle tipo y descripcion. Solo lo hacemos para refrecar el recycle local
                        titulo = nuevoTitulo,
                        description = nuevaDescripcion
                    )

                    val intent = Intent()
                    intent.putExtra("UPDATED_ITEM", updatedItem) // creamos un intent del item que acabamss de crear
                    setResult(RESULT_OK, intent)

                    finish()


                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(
                        this@PerfilPatchActivity,
                        "Error de conexión",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        private fun rellenarCampos(item: SyncItem) {
            etTitleCreateP.setText(item.titulo)
            etIdUserP.setText(item.idUser.toString())
            etDescriptionP.setText(item.description)

            // Si usas categorías en spinner
            val position = (SpinerCategoriaFiltrosP.adapter as ArrayAdapter<String>).getPosition(item.categoria)
            SpinerCategoriaFiltrosP.setSelection(position)
        }
    }
