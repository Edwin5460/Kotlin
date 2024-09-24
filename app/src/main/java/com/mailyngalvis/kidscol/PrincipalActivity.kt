package com.mailyngalvis.kidscol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class PrincipalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        val btnJugar = findViewById<Button>(R.id.btnjugar)
        btnJugar.setOnClickListener { navegar() }
    }

    fun navegar() {
        val intent = Intent(this, NinoActivity::class.java)
        startActivity(intent)
    }
}
