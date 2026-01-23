package com.jorlina.syncapp.CreateAccount

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jorlina.syncapp.R

class CreateAcontActivity : AppCompatActivity() {

    private val viewModel: CreateAccountViewModel by viewModels()

    private lateinit var tvErrorCreate : TextView
    private lateinit var etUsername : EditText
    private lateinit var etEmail : EditText
    private lateinit var etPassword : EditText
    private lateinit var  etRepeatPassword : EditText
    private lateinit var btCrear : Button
    private lateinit var arrowBackIv: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create_acont)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initComponents();
        initListeners();
        initUI()
    }

    private  fun initComponents(){
        arrowBackIv = findViewById<ImageView>(R.id.arrowBackIv)


        etUsername = findViewById<EditText>(R.id.etName)
        etEmail = findViewById<EditText>(R.id.etEmail)
        etPassword = findViewById<EditText>(R.id.etPasswordCreate)
        etRepeatPassword = findViewById<EditText>(R.id.etRepeatPasswordCreate)
        btCrear = findViewById<Button>(R.id.btCreateAccount)
        tvErrorCreate = findViewById<TextView>(R.id.tvErrorCreate)
    }

    private fun initListeners(){
        arrowBackIv.setOnClickListener {
            finish()
        }

        btCrear.setOnClickListener {
            viewModel.createAccount(
                etUsername.text.toString(),
                etEmail.text.toString(),
                etPassword.text.toString(),
                etRepeatPassword.text.toString()
            )
        }

        viewModel.errorMessage.observe(this) {
            tvErrorCreate.text = it
        }

        viewModel.registerResult.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "Cuenta creada correctamente", Toast.LENGTH_SHORT).show()
                finish() // Volver al login
            }
        }
    }

    private fun initUI(){

    }
}