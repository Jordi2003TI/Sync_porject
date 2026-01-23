package com.jorlina.syncapp.MainLogin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jorlina.syncapp.CreateAccount.CreateAcontActivity
import com.jorlina.syncapp.MenuPrincipalActivity
import com.jorlina.syncapp.R

class MainActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()

    private lateinit var etName: EditText

    private lateinit var etPasswordLogin: EditText

    private lateinit var tvError: TextView
    private lateinit var btLogin: Button

    private lateinit var  arrowBackIv: ImageView

    private lateinit var tvCreaCuenta: TextView

    private lateinit var btInvitado: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
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
        btLogin = findViewById<Button>(R.id.btLogin)
        tvCreaCuenta = findViewById<TextView>(R.id.tvCreaCuenta)
        arrowBackIv = findViewById<ImageView>(R.id.arrowBackIv)
        btInvitado = findViewById<Button>(R.id.btInvitado)
        etName = findViewById<EditText>(R.id.etName)
        etPasswordLogin = findViewById<EditText>(R.id.etPasswordLogin)
        tvError = findViewById<TextView>(R.id.tvError)

    }

    private fun initListeners(){


        btLogin.setOnClickListener {
            viewModel.login(
                etName.text.toString(),
                etPasswordLogin.text.toString()
            )
        }

        viewModel.errorMessage.observe(this){error ->
            tvError.text = error
        }

        viewModel.loginResult.observe(this){ success ->
            if (success){
                val intent = Intent(this, MenuPrincipalActivity::class.java)
                startActivity(intent)
            }
        }

        tvCreaCuenta.setOnClickListener {
            val intent = Intent(this, CreateAcontActivity::class.java)
            startActivity(intent)
        }

        arrowBackIv.setOnClickListener {
            SalirApp()
        }
    }

    private fun initUI(){

    }

    private fun SalirApp(){
        AlertDialog.Builder(this)
            .setTitle("Quieres salir de la aplicacion")
            .setMessage("Seguro que quieres salir")
            .setPositiveButton("Sí"){_, _,->
                finish()
            }
            .setNegativeButton("No", null)
            .setCancelable(true)
            .show()
    }
}

private fun Any.observe(activity: MainActivity, function: Any) {}
