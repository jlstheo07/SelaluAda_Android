package com.example.selaluada.ui.profile

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.selaluada.R

class IdentityActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_identity)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val spinnerGender: Spinner = findViewById(R.id.spinnerGender)
        val genders = listOf("Pria", "Wanita")

        val spinnerProvinsi: Spinner = findViewById(R.id.spinnerProvinsi)
        val provinsi = listOf(
            "Aceh",
            "Bali",
            "Bangka Belitung",
            "Banten",
            "Bengkulu",
            "DI Yogyakarta",
            "DKI Jakarta",
            "Gorontalo",
            "Jambi",
            "Jawa Barat",
            "Jawa Tengah",
            "Jawa Timur",
            "Kalimantan Barat",
            "Kalimantan Selatan",
            "Kalimantan Tengah",
            "Kalimantan Timur",
            "Kalimantan Utara",
            "Kepulauan Riau",
            "Lampung",
            "Maluku",
            "Maluku Utara",
            "Nusa Tenggara Barat",
            "Nusa Tenggara Timur",
            "Papua",
            "Papua Barat",
            "Papua Barat Daya",
            "Papua Pegunungan",
            "Papua Selatan",
            "Papua Tengah",
            "Riau",
            "Sulawesi Barat",
            "Sulawesi Selatan",
            "Sulawesi Tengah",
            "Sulawesi Tenggara",
            "Sulawesi Utara",
            "Sumatera Barat",
            "Sumatera Selatan",
            "Sumatera Utara"
        )



        val genderAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genders)
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGender.adapter = genderAdapter

        val provinsiAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, provinsi)
        provinsiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerProvinsi.adapter = provinsiAdapter

        // Jika ingin mengisi data dummy seperti gambar
        findViewById<EditText>(R.id.CusNamaLengkap).setText("Mira Setiawan")
        findViewById<EditText>(R.id.CusEmail).setText("mirasetiawan@company.com")
        spinnerGender.setSelection(1) // "Wanita"
        findViewById<EditText>(R.id.CusAlamat).setText("Jl. Pasti Cepat A7/66")
        findViewById<EditText>(R.id.CusKota).setText("Jakarta Barat")
        spinnerProvinsi.setSelection(0) // DKI Jakarta
        findViewById<EditText>(R.id.CusTelepon).setText("(128) 666 070")
    }
}