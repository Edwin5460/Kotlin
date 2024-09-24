package com.mailyngalvis.kidscol

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "preguntas.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_PREGUNTAS = "preguntas"
        const val COLUMN_ID = "id"
        const val COLUMN_PREGUNTA = "pregunta"
        const val COLUMN_IMAGEN = "imagen"

        const val TABLE_OPCIONES = "opciones"
        const val COLUMN_PREGUNTA_ID = "pregunta_id"
        const val COLUMN_OPCION = "opcion"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTablePreguntas = ("CREATE TABLE $TABLE_PREGUNTAS (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_PREGUNTA TEXT, " +
                "$COLUMN_IMAGEN INTEGER)")
        db?.execSQL(createTablePreguntas)

        val createTableOpciones = ("CREATE TABLE $TABLE_OPCIONES (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_PREGUNTA_ID INTEGER, " +
                "$COLUMN_OPCION TEXT, " +
                "FOREIGN KEY($COLUMN_PREGUNTA_ID) REFERENCES $TABLE_PREGUNTAS($COLUMN_ID))")
        db?.execSQL(createTableOpciones)

        insertarPreguntasYRespuestas(db)
    }

    private fun insertarPreguntasYRespuestas(db: SQLiteDatabase?) {
        // Insertar preguntas
        db?.execSQL("INSERT INTO $TABLE_PREGUNTAS ($COLUMN_PREGUNTA, $COLUMN_IMAGEN) VALUES ('¿Cuántas manzanas hay?', ${R.drawable.manzanas})")
        db?.execSQL("INSERT INTO $TABLE_PREGUNTAS ($COLUMN_PREGUNTA, $COLUMN_IMAGEN) VALUES ('¿Ubicar las siguientes unidades en el ábaco?', ${R.drawable.abaco})")
        db?.execSQL("INSERT INTO $TABLE_PREGUNTAS ($COLUMN_PREGUNTA, $COLUMN_IMAGEN) VALUES ('¿Cuántos lápices hay?', ${R.drawable.lapices})")
        db?.execSQL("INSERT INTO $TABLE_PREGUNTAS ($COLUMN_PREGUNTA, $COLUMN_IMAGEN) VALUES ('¿Cuál es el resultado de esta resta?', ${R.drawable.resta})")
        db?.execSQL("INSERT INTO $TABLE_PREGUNTAS ($COLUMN_PREGUNTA, $COLUMN_IMAGEN) VALUES ('¿Cuál es el número anterior a?', ${R.drawable.uno})")
        db?.execSQL("INSERT INTO $TABLE_PREGUNTAS ($COLUMN_PREGUNTA, $COLUMN_IMAGEN) VALUES ('¿Cuál es el número anterior a?', ${R.drawable.cinco})")

        // Insertar opciones para las preguntas
        db?.execSQL("INSERT INTO $TABLE_OPCIONES ($COLUMN_PREGUNTA_ID, $COLUMN_OPCION) VALUES (1, '4')")
        db?.execSQL("INSERT INTO $TABLE_OPCIONES ($COLUMN_PREGUNTA_ID, $COLUMN_OPCION) VALUES (1, '3')")
        db?.execSQL("INSERT INTO $TABLE_OPCIONES ($COLUMN_PREGUNTA_ID, $COLUMN_OPCION) VALUES (1, '5')")

        db?.execSQL("INSERT INTO $TABLE_OPCIONES ($COLUMN_PREGUNTA_ID, $COLUMN_OPCION) VALUES (2, '435')")
        db?.execSQL("INSERT INTO $TABLE_OPCIONES ($COLUMN_PREGUNTA_ID, $COLUMN_OPCION) VALUES (2, '565')")
        db?.execSQL("INSERT INTO $TABLE_OPCIONES ($COLUMN_PREGUNTA_ID, $COLUMN_OPCION) VALUES (2, '875')")

        db?.execSQL("INSERT INTO $TABLE_OPCIONES ($COLUMN_PREGUNTA_ID, $COLUMN_OPCION) VALUES (3, '10')")
        db?.execSQL("INSERT INTO $TABLE_OPCIONES ($COLUMN_PREGUNTA_ID, $COLUMN_OPCION) VALUES (3, '7')")
        db?.execSQL("INSERT INTO $TABLE_OPCIONES ($COLUMN_PREGUNTA_ID, $COLUMN_OPCION) VALUES (3, '8')")

        db?.execSQL("INSERT INTO $TABLE_OPCIONES ($COLUMN_PREGUNTA_ID, $COLUMN_OPCION) VALUES (4, '3')")
        db?.execSQL("INSERT INTO $TABLE_OPCIONES ($COLUMN_PREGUNTA_ID, $COLUMN_OPCION) VALUES (4, '2')")
        db?.execSQL("INSERT INTO $TABLE_OPCIONES ($COLUMN_PREGUNTA_ID, $COLUMN_OPCION) VALUES (4, '1')")

        db?.execSQL("INSERT INTO $TABLE_OPCIONES ($COLUMN_PREGUNTA_ID, $COLUMN_OPCION) VALUES (5, '2')")
        db?.execSQL("INSERT INTO $TABLE_OPCIONES ($COLUMN_PREGUNTA_ID, $COLUMN_OPCION) VALUES (5, '0')")
        db?.execSQL("INSERT INTO $TABLE_OPCIONES ($COLUMN_PREGUNTA_ID, $COLUMN_OPCION) VALUES (5, '4')")

        db?.execSQL("INSERT INTO $TABLE_OPCIONES ($COLUMN_PREGUNTA_ID, $COLUMN_OPCION) VALUES (6, '3')")
        db?.execSQL("INSERT INTO $TABLE_OPCIONES ($COLUMN_PREGUNTA_ID, $COLUMN_OPCION) VALUES (6, '2')")
        db?.execSQL("INSERT INTO $TABLE_OPCIONES ($COLUMN_PREGUNTA_ID, $COLUMN_OPCION) VALUES (6, '4')")
    }

    // Obtener opciones desde la base de datos
    fun obtenerOpciones(preguntaId: Int): List<String> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $COLUMN_OPCION FROM $TABLE_OPCIONES WHERE $COLUMN_PREGUNTA_ID = ?", arrayOf(preguntaId.toString()))
        val opciones = mutableListOf<String>()

        if (cursor.moveToFirst()) {
            do {
                val columnIndex = cursor.getColumnIndex(COLUMN_OPCION)
                if (columnIndex != -1) {
                    val opcion = cursor.getString(columnIndex)
                    opciones.add(opcion)
                } else {
                    Log.e("DatabaseHelper", "La columna $COLUMN_OPCION no existe en la tabla.")
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
        return opciones
    }

    // Obtener una pregunta desde la base de datos
    fun obtenerPregunta(preguntaId: Int): Pregunta? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $COLUMN_PREGUNTA, $COLUMN_IMAGEN FROM $TABLE_PREGUNTAS WHERE $COLUMN_ID = ?", arrayOf(preguntaId.toString()))

        var pregunta: Pregunta? = null
        if (cursor.moveToFirst()) {
            val textoPregunta = cursor.getString(cursor.getColumnIndex(COLUMN_PREGUNTA))
            val imagenResId = cursor.getInt(cursor.getColumnIndex(COLUMN_IMAGEN))
            pregunta = Pregunta(textoPregunta, imagenResId)
        }
        cursor.close()
        return pregunta
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Eliminar las tablas antiguas si existen
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PREGUNTAS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_OPCIONES")

        // Crear las tablas nuevamente
        onCreate(db)
    }
}

data class Pregunta(val texto: String, val imagenResId: Int)
