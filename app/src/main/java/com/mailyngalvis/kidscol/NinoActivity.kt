package com.mailyngalvis.kidscol

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast

class NinoActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private var indexPreguntaActual = 1 // Empezamos en la primera pregunta

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nino)

        // Inicializar la base de datos
        databaseHelper = DatabaseHelper(this)

        // Mostrar la primera pregunta y sus opciones
        mostrarPregunta()

        val botonSiguiente = findViewById<Button>(R.id.botonSiguiente)
        botonSiguiente.setOnClickListener {
            // Mostrar la siguiente pregunta
            indexPreguntaActual++
            if (indexPreguntaActual <= 6) {
                mostrarPregunta()
            } else {
                finish() // Termina la actividad cuando no hay más preguntas
            }
        }
    }

    private fun mostrarPregunta() {
        val textViewPregunta = findViewById<TextView>(R.id.textViewPregunta)
        val imageViewPregunta = findViewById<ImageView>(R.id.imageViewPregunta)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)

        // Obtener la pregunta y las opciones
        val pregunta = databaseHelper.obtenerPregunta(indexPreguntaActual)
        val opciones = databaseHelper.obtenerOpciones(indexPreguntaActual)

        if (pregunta == null) {
            // Mostrar un mensaje si no se encuentra la pregunta
            Toast.makeText(this, "No se encontró la pregunta.", Toast.LENGTH_SHORT).show()
            return
        }

        // Mostrar la pregunta y su imagen
        textViewPregunta.text = pregunta.texto
        imageViewPregunta.setImageResource(pregunta.imagenResId)

        // Limpiar las opciones de respuesta anteriores
        radioGroup.removeAllViews()

        // Mostrar las opciones
        if (opciones.isNotEmpty()) {
            for (opcion in opciones) {
                val radioButton = RadioButton(this)
                radioButton.text = opcion
                radioGroup.addView(radioButton)
            }
        } else {
            // Si no hay opciones, mostrar un mensaje
            Toast.makeText(this, "No se encontraron opciones para esta pregunta.", Toast.LENGTH_SHORT).show()
        }
    }
}

