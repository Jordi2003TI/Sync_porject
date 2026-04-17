package com.jorlina.syncapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.View
import android.view.ViewParent
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Switch
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.FieldPosition

import android.speech.RecognitionListener

class Filtros : AppCompatActivity() {

    private lateinit var recognizer: SpeechRecognizer

    private lateinit var spinner: Spinner

    private lateinit var SpinerCategoria: Spinner

    private lateinit var arrowBackIv: ImageView

    private lateinit var btFiltrar: Button

    private var categoriaSeleccionada: String = "Todas"

    private lateinit var voiceReceiver: BroadcastReceiver

    private lateinit var switchLike: Switch
    private var likeSeleccionado: Boolean = false

    private lateinit var micButton: Button
    private lateinit var recognizerIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_filtros)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initComponents()
        listeners()
        initUI()

        voiceReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val command = intent?.getStringExtra("command")
                handleVoiceCommand(command)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        ContextCompat.registerReceiver(
            this,
            voiceReceiver,
            IntentFilter("VOICE_RESULT"),
            ContextCompat.RECEIVER_NOT_EXPORTED
        )
    }
    override fun onPause() {
        super.onPause()
        unregisterReceiver(voiceReceiver)
    }


    private fun initUI() {
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.abc_spinner,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // Segundo Spinner
        val adapterSpinner2 = ArrayAdapter.createFromResource(
            this,
            R.array.categorias_spinner,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        SpinerCategoria.adapter = adapterSpinner2

        SpinerCategoria.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                categoriaSeleccionada = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No hacer nada
            }

        }
    }

    private fun listeners() {

        arrowBackIv.setOnClickListener {
            finish()
        }

        btFiltrar.setOnClickListener {
            likeSeleccionado = switchLike.isChecked
            val intent = Intent()
            intent.putExtra("CATEGORIA", categoriaSeleccionada)
            intent.putExtra("LIKE", likeSeleccionado)
            setResult(RESULT_OK, intent)
            finish()
        }

        micButton.setOnClickListener {
            recognizer.startListening(recognizerIntent)
        }

    }

    private fun initComponents() {
        spinner = findViewById<Spinner>(R.id.SpinerAlfabetic)
        //spinner.prompt="Alfabetico"

        arrowBackIv = findViewById<ImageView>(R.id.arrowBackIv)
        SpinerCategoria = findViewById<Spinner>(R.id.SpinerCategoria)
        switchLike = findViewById(R.id.switchLike)
        btFiltrar = findViewById<Button>(R.id.btFiltrar)


        micButton = findViewById(R.id.btMicrofono)

        recognizer = SpeechRecognizer.createSpeechRecognizer(this)

        recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES")
        }

        recognizer.setRecognitionListener(object : RecognitionListener {

            override fun onResults(results: Bundle?) {
                val spokenText = results
                    ?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    ?.firstOrNull()
                    ?.lowercase()

                handleVoiceCommand(spokenText)
            }

            override fun onError(error: Int) {}

            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {}
            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })

    }

    private fun handleVoiceCommand(command: String?) {

        val cmd = command?.lowercase()?.trim() ?: return

        when {

            cmd.contains("cálculo") ||
                    cmd.contains("filtrar calculo") -> {

                SpinerCategoria.setSelection(1)
                categoriaSeleccionada = "Calculo"
            }

            cmd.contains("programación") -> {
                SpinerCategoria.setSelection(2)
                categoriaSeleccionada = "Programacion"
            }

            cmd.contains("biología") || cmd.contains("biologia") -> {
                SpinerCategoria.setSelection(3)
                categoriaSeleccionada = "Biologia"
            }

            cmd.contains("favoritos") -> {
                switchLike.isChecked = true
                likeSeleccionado = true
            }

            cmd.contains("filtrar") -> {
                btFiltrar.performClick()
            }

            cmd.contains("atrás") || cmd.contains("atras") -> {
                finish()
            }
        }
    }

    override fun onDestroy() {
        recognizer.destroy()
        super.onDestroy()
    }


}