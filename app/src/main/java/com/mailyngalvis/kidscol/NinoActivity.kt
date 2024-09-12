package com.mailyngalvis.kidscol

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView

class  NinoActivity : AppCompatActivity() {

    private val preguntas = listOf(
        "¿Cuantas manzanas hay?",
        "¿Ubicar las siguientes unidades en el abaco?",
        "¿Cuantos lapices hay?",
        "¿Cual es el resultado de esta resta?",
        "¿Cual es el número anterior a:?",
        "¿Cual es el número anterior a:?",
        // Agrega más preguntas aquí según sea necesario
    )

    private val opciones = listOf(
        listOf("4", "3", "5"),
        listOf("435", "565", "875"),
        listOf("10", "7", "8"),
        listOf("3", "2", "1"),
        listOf("2", "0", "4"),
        listOf("3", "2", "4"),
        // Agrega más listas de opciones aquí según sea necesario
    )

    private val imagenes = listOf(
        R.drawable.manzanas,
        R.drawable.abaco,
        R.drawable.lapices,
        R.drawable.resta,
        R.drawable.uno,
        R.drawable.cinco,
        // Agrega más imágenes aquí según sea necesario
    )

    private var indexPreguntaActual = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nino)

        val textViewPregunta = findViewById<TextView>(R.id.textViewPregunta)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val botonSiguiente = findViewById<Button>(R.id.botonSiguiente)

        // Mostrar la primera pregunta y sus opciones de respuesta
        mostrarPregunta()

        botonSiguiente.setOnClickListener {
            // Guardar la respuesta seleccionada
            val radioButtonSeleccionadoId = radioGroup.checkedRadioButtonId
            val radioButtonSeleccionado = findViewById<RadioButton>(radioButtonSeleccionadoId)
            val respuestaSeleccionada = radioButtonSeleccionado.text.toString()
            // Aquí puedes guardar la respuesta en algún lugar o hacer lo que quieras con ella

            // Mostrar la siguiente pregunta
            indexPreguntaActual++
            if (indexPreguntaActual < preguntas.size) {
                mostrarPregunta()
            } else {
                // Se han respondido todas las preguntas, puedes realizar alguna acción final aquí
                // Por ejemplo, mostrar un mensaje de agradecimiento o cerrar la actividad
                finish()
            }
        }
    }

    private fun mostrarPregunta() {
        val textViewPregunta = findViewById<TextView>(R.id.textViewPregunta)
        val imageViewPregunta = findViewById<ImageView>(R.id.imageViewPregunta)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)

        // Mostrar la pregunta actual
        textViewPregunta.text = preguntas[indexPreguntaActual]

        imageViewPregunta.setImageResource(imagenes[indexPreguntaActual])

        // Limpiar las opciones de respuesta anteriores
        radioGroup.removeAllViews()

        // Agregar las opciones de respuesta para la pregunta actual
        for (opcion in opciones[indexPreguntaActual]) {
            val radioButton = RadioButton(this)
            radioButton.text = opcion
            radioGroup.addView(radioButton)
        }
    }
}