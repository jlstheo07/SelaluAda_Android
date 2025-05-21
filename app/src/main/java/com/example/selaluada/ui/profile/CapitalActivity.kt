package com.example.selaluada.ui.profile

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.selaluada.R

class CapitalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContentView(R.layout.activity_capital)

        val spinnerMetode: Spinner = findViewById(R.id.spinnermetodepembayaran)
        val metode = listOf("Transfer Bank", "QRIS", "Autodebet")

        val metodeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, metode)
        metodeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerMetode.adapter = metodeAdapter

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

    }
}