package com.jorlina.syncapp

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer

class VoiceService : Service() {

    private lateinit var recognizer: SpeechRecognizer

    private lateinit var recognizerIntent: Intent

    override fun onCreate() {
        super.onCreate()

        recognizer = SpeechRecognizer.createSpeechRecognizer(this)
        recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ca-ES")
        }

        recognizer.setRecognitionListener(object : RecognitionListener {
            override fun onResults(results: Bundle?) {
                val spokenText = results
                    ?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    ?.get(0)
                    ?.lowercase()

                val intent = Intent("VOICE_RESULT")
                intent.putExtra("command", spokenText)
                sendBroadcast(intent)
            }


            override fun onError(error: Int) {}
            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {
                recognizer.startListening(recognizerIntent)
            }
            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })

        recognizer.startListening(recognizerIntent)


    }

    override fun onDestroy() {
        super.onDestroy()
        recognizer.destroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}