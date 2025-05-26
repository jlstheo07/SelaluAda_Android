package com.example.selaluada.ui.main

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.selaluada.R
import com.example.selaluada.data.model.PlafondLevel
import com.example.selaluada.ui.adapter.PlafondAdapter

class PlafondActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PlafondAdapter

    private val plafondList = listOf(
        PlafondLevel("Topaz", "Rp 6.000.000", "12 Bulan", "10%"),
        PlafondLevel("Emerald", "Rp 15.000.000", "12 Bulan", "8%"),
        PlafondLevel("Sapphire", "Rp 30.000.000", "12 Bulan", "6%"),
        PlafondLevel("Ruby", "Rp 45.000.000", "12 Bulan", "4%"),
        PlafondLevel("Diamond", "Rp 60.000.000", "12 Bulan", "2%")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plafond)

        recyclerView = findViewById(R.id.recyclerViewPlafond)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PlafondAdapter(plafondList)
        recyclerView.adapter = adapter

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}
